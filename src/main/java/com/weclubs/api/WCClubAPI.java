package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCRequestModel;
import com.weclubs.model.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 社团相关接口
 *
 * Created by fangzanpan on 2017/3/11.
 */
@RestController
@RequestMapping(value = "/club")
public class WCClubAPI {

    private Logger log = Logger.getLogger(WCClubAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIClubService mClubService;

    @RequestMapping(value = "/get_club_detail")
    public WCResultData getClubDetail(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus security = mSecurityService.checkRequestParams(requestModel);
        if (security != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(security, null);
        }

        try {
            HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

            if (requestData == null || requestData.size() == 0) {
                log.error("getClubDetail：请求参数data为空");
                return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
            }

            long clubId = Long.parseLong((String) requestData.get("club_id"));

            WCClubBean clubBean = mClubService.getClubInfoById(clubId);
            if (clubBean == null) {
                WCHttpStatus clubNullError = WCHttpStatus.FAIL_CUSTOM_DAILOG_AND_CLOSE;
                clubNullError.msg = "该社团不存在";
                log.error("getClubDetail：找不到 id = " + clubId + " 的社团");
                return WCResultData.getHttpStatusData(clubNullError, null);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result = getClubBaseInfo(clubBean, result);

            result.put("club_level", clubBean.getLevel());
            result.put("slogan", clubBean.getSlogan());
            result.put("attribution", clubBean.getIntroduction());

            result.put("member_count", "10");
            result.put("member", null);

            result.put("activity_count", "9");
            result.put("activity", null);

            List<WCClubHonorBean> honors = mClubService.getClubHonorByClubId(clubId);
            result.put("club_honor", getClubHonorList(honors));

            result.put("cache_test", mClubService.getCacheTest());

            return WCResultData.getSuccessData(result);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }
    }

    @RequestMapping(value = "/get_clubs_by_suggest")
    public WCResultData getClubsBySuggest(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getClubsBySuggest：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        List<WCClubBean> suggestClubs = mClubService.getClubsBySchoolId(1);
        List<HashMap<String, Object>> resultSuggest = new ArrayList<HashMap<String, Object>>();
        for (WCClubBean suggestClub : suggestClubs) {
            HashMap<String, Object> club = new HashMap<String, Object>();
            club = getClubBaseInfo(suggestClub, club);
            resultSuggest.add(club);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("club", resultSuggest);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_clubs_by_owner")
    public WCResultData getClubsByOwner(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getClubsByOwner：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestParams == null || requestParams.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = Long.parseLong((String) requestParams.get("student_id"));

        List<WCClubBean> clubs = mClubService.getClubsByStudentId(studentId);
        ArrayList<HashMap<String, Object>> ownerClubs = new ArrayList<HashMap<String, Object>>();
        for (WCClubBean club : clubs) {
            HashMap<String, Object> clubHash = new HashMap<String, Object>();
            clubHash = getClubBaseInfo(club, clubHash);
            clubHash.put("member_count", "10");
            clubHash.put("club_level", club.getLevel());
            clubHash.put("todo_count", "10");
            clubHash.put("activity_count", "2");
            ownerClubs.add(clubHash);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club", ownerClubs);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_club_students", method = RequestMethod.POST)
    public WCResultData getStudentsOfClubByCurrentGraduate(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getStudentsOfClubByCurrentGraduate：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = WCRequestParamsUtil.getRequestId(requestModel);
        long clubId = Long.parseLong((String) requestData.get("club_id"));

        List<WCStudentBean> students = mClubService.getStudentsByCurrentGraduate(clubId);
        ArrayList<HashMap<String, Object>> studentsMap = new ArrayList<HashMap<String, Object>>();
        if (students != null) {
            for (WCStudentBean student : students) {
                studentsMap.add(getCommonStudent(student));
            }
        }


        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("student", studentsMap);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getClubBaseInfo(WCClubBean clubBean, HashMap<String, Object> result) {
        result.put("club_id", clubBean.getClubId());
        result.put("club_name", clubBean.getName());
        result.put("avatar_url", clubBean.getAvatarUrl());
        return result;
    }

    private ArrayList<HashMap<String, Object>> getClubHonorList(List<WCClubHonorBean> honors) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for (WCClubHonorBean honor : honors) {
            HashMap<String, Object> honorHash = new HashMap<String, Object>();
            honorHash.put("get_date", honor.getGetDate());
            honorHash.put("name", honor.getContent());
            result.add(honorHash);
        }
        return result;
    }

    private HashMap<String, Object> getCommonStudent(WCStudentBean studentBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("student_id", studentBean.getStudentId());
        result.put("name", !StringUtils.isEmpty(studentBean.getRealName()) ? studentBean.getRealName() : studentBean.getNickName());
        result.put("department", "技术部");
        result.put("job", "部长");
        result.put("mobile", "12345098760");
        result.put("avatar_url", studentBean.getAvatarUrl());
        result.put("major", "12级-多媒体应用");
        return result;
    }
}
