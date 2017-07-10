package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.club.WCClubServiceImpl;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.model.WCMyClubModel;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
class WCClubAPI {

    private Logger log = Logger.getLogger(WCClubAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubService mClubService;
    private WCISchoolService mSchoolService;
    private WCIUserService mUserService;

    @Autowired
    public WCClubAPI(WCIClubService mClubService, WCISecurityService mSecurityService,
                     WCISchoolService schoolService, WCIUserService userService) {
        this.mClubService = mClubService;
        this.mSecurityService = mSecurityService;
        this.mSchoolService = schoolService;
        this.mUserService = userService;
    }

    @RequestMapping(value = "/get_club_detail", method = RequestMethod.POST)
    public WCResultData getClubDetail(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus security = mSecurityService.checkRequestParams(requestModel);
        if (security != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(security, null);
        }

        security = mSecurityService.checkTokenAvailable(requestModel);
        if (security != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(security, null);
        }

        try {
            HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

            if (requestData == null || requestData.size() == 0) {
                log.error("getClubDetail：请求参数data为空");
                return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
            }

            long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
            long userId = WCCommonUtil.getLongData(requestData.get("user_id"));

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

            List<WCClubStudentBean> members = mClubService.getCurrentGraduateStudentsByClubId(clubId);
            result.put("member_count", members != null ? members.size() : 0);
            if (members == null) {
                result.put("member", null);
            } else {
                result.put("member", getClubMember(members));
            }

            boolean isJoined = mClubService.checkStudentExitCurrentGraduate(userId, clubId);
            result.put("is_joined", isJoined ? 1 : 0);

            List<WCClubHonorBean> honors = mClubService.getClubHonorByClubId(clubId);
            result.put("club_honor", getClubHonorList(honors));

            return WCResultData.getSuccessData(result);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }
    }

    @RequestMapping(value = "/get_clubs_by_suggest", method = RequestMethod.POST)
    public WCResultData getClubsBySuggest(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getClubsBySuggest：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);
        long schoolId = WCCommonUtil.getLongData(requestData.get("school_id"));

        PageHelper.startPage(pageNo, pageSize);
        List<WCClubBean> suggestClubs = mClubService.getClubsBySchoolId(schoolId);
        PageInfo<WCClubBean> pageInfo = new PageInfo<WCClubBean>(suggestClubs);

        List<HashMap<String, Object>> resultSuggest = new ArrayList<HashMap<String, Object>>();
        for (WCClubBean suggestClub : pageInfo.getList()) {
            HashMap<String, Object> club = new HashMap<String, Object>();
            club = getClubBaseInfo(suggestClub, club);
            resultSuggest.add(club);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("club", resultSuggest);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);

        return WCResultData.getSuccessData(result);
    }

//    @RequestMapping(value = "/get_clubs_by_owner", method = RequestMethod.POST)
    public WCResultData getClubsByOwner(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getClubsByOwner：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestParams == null || requestParams.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        long studentId = WCRequestParamsUtil.getUserId(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCClubBean> clubs = mClubService.getClubsByStudentId(studentId);
        PageInfo<WCClubBean> pageInfo = new PageInfo<WCClubBean>(clubs);

        ArrayList<HashMap<String, Object>> ownerClubs = new ArrayList<HashMap<String, Object>>();
        for (WCClubBean club : pageInfo.getList()) {
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
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_club_students", method = RequestMethod.POST)
    public WCResultData getStudentsOfClubByCurrentGraduate(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getStudentsOfClubByCurrentGraduate：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));

        int sortType = WCClubServiceImpl.SORT_BY_REAL_NAME; // 默认排序为按照真实姓名
        if (requestData.containsKey("sort_type")) {
            sortType = WCCommonUtil.getIntegerData(requestData.get("sort_type"));
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("sort_type", sortType);
        result.put("groups", mClubService.getStudentsByCurrentGraduate(clubId, sortType));
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_my_clubs", method = RequestMethod.POST)
    public WCResultData getMyClubs(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        List<WCMyClubModel> myClubs = mClubService.getMyClubs(studentId);

        ArrayList<HashMap<String, Object>> myClubHash = new ArrayList<HashMap<String, Object>>();
        if (myClubs != null && myClubs.size() > 0) {
            for (WCMyClubModel myClub : myClubs) {
                HashMap<String, Object> hash = new HashMap<String, Object>();
                hash = getClubBaseInfo(myClub, hash);
                hash.put("member_count", myClub.getMemberCount());
                hash.put("todo_count", myClub.getTodoCount());
                hash.put("activity_count", myClub.getActivityCount());
                hash.put("level", myClub.getLevel());

                myClubHash.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club", myClubHash);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_participation_detail")
    public WCResultData getParticipationDetail(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCCommonUtil.getLongData(requestData.get("user_id"));
        long studentId = WCCommonUtil.getLongData(requestData.get("student_id"));
        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean == null) {
            check.msg = "找不到该学生";
            return WCResultData.getHttpStatusData(check, null);
        }

        List<WCClubBean> userClubs = mClubService.getClubsByStudentId(userId);
        List<WCClubBean> studentClubs = mClubService.getClubsByStudentId(studentId);
        boolean isEqual = false;
        if (userClubs != null && studentClubs != null) {
            log.error("getParticipationDetail: userClubs = " + userClubs.toString() + "; studentClubs = " + studentClubs.toString());
            for (WCClubBean userClub : userClubs) {
                for (WCClubBean studentClub : studentClubs) {
                    if (userClub.getClubId() == studentClub.getClubId()) {
                        isEqual = true;
                        break;
                    }
                }
            }
        }

        HashMap<String, Object> result = getUserInfoByStudent(studentBean);
        result.put("is_together", isEqual ? 1 : 0);

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

    private ArrayList<HashMap<String, Object>> getClubMember(List<WCClubStudentBean> list) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();

        int max = list.size() > 6 ? 6 : list.size();
        for (int i = 0; i < max; i++) {
            HashMap<String, Object> student = new HashMap<>();
            student.put("student_id", list.get(i).getStudentId());
            student.put("student_name", list.get(i).getRealName());
            student.put("avatar_url", list.get(i).getAvatarUrl());
            result.add(student);
        }
        return result;
    }

    /**
     * 根据 student 实体获取得到 userInfo 的字典
     *
     * @param student   学生实体
     *
     * @return  userInfo 的字典
     */
    private HashMap<String, Object> getUserInfoByStudent(WCStudentBean student) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user_id", student.getStudentId());
        result.put("mobile", student.getMobile());
        result.put("nick_name", student.getNickName());
        result.put("real_name", student.getRealName());
        result.put("avatar_url", student.getAvatarUrl());
        result.put("gender", student.getGender());
        result.put("class_name", student.getClassName());
        result.put("graduate_year", student.getGraduateYear());
        result.put("is_auth", student.getStatus());
        result.put("student_card_id", student.getStudentIdNo());

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(student.getSchoolId());
        result.put("school_id", schoolBean != null ? schoolBean.getSchoolId() : 0);
        result.put("school_name", schoolBean == null ? "" : schoolBean.getName());

        return result;
    }
}
