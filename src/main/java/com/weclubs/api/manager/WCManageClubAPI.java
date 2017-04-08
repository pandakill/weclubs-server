package com.weclubs.api.manager;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCSchoolBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCManageClubModel;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 社团管理API
 *
 * Created by fangzanpan on 2017/4/5.
 */
@RestController
@RequestMapping(value = "manage/club")
class WCManageClubAPI {

    private Logger log = Logger.getLogger(WCManageClubAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIClubService mClubService;
    @Autowired
    private WCISchoolService mSchoolService;
    @Autowired
    private WCIUserService mUserService;

    @RequestMapping(value = "/get_my_club")
    public WCResultData getMyManagerClub(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
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

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        List<WCManageClubModel> models = mClubService.getMyManageClubs(studentId);
        if (models != null && models.size() > 0) {
            for (WCManageClubModel model : models) {
                resultArray.add(getMyManageClub(model));
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club", resultArray);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/create_club")
    private WCResultData createClub(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
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

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        WCSchoolBean schoolBean = mSchoolService.getSchoolById(studentBean.getSchoolId());
        if (schoolBean == null) {
            log.error("找不到该学生的学校信息。");
            check = WCHttpStatus.FAIL_APPLICATION_ERROR;
            return WCResultData.getHttpStatusData(check, null);
        }

        String clubName = (String) requestData.get("club_name");
        int level = 0;
        if (requestData.get("level") instanceof Integer) {
            level = (Integer) requestData.get("level");
        } else if (requestData.get("level") instanceof String) {
            level = Integer.parseInt((String) requestData.get("level"));
        }
        String avatarUrl = (String) requestData.get("avatar_url");

        WCClubBean clubBean = new WCClubBean();
        clubBean.setName(clubName);
        clubBean.setLevel(level);
        clubBean.setAvatarUrl(avatarUrl);
        clubBean.setSchoolId(schoolBean.getSchoolId());

        long clubId = mClubService.createClub(clubBean, studentId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club_id", clubId);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getMyManageClub(WCManageClubModel manageClubModel) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("club_id", manageClubModel.getClubId());
        result.put("club_name", manageClubModel.getClubName());
        result.put("avatar_url", manageClubModel.getAvatarUrl());
        result.put("level", manageClubModel.getLevel());
        result.put("introduction", manageClubModel.getIntroduction());
        result.put("slogan", manageClubModel.getSlogan());

        result.put("departments", manageClubModel.getDepartments());
        result.put("jobs", manageClubModel.getJobs());

        result.put("honor_finish", manageClubModel.getHonorCount() > 0 ? 1 : 0);
        result.put("member_finish", manageClubModel.getMemberCount() > 1 ? 1 : 0);

        // 完成情况 START
        // 有 头像、slogan、介绍、荣誉介绍、人员情况、部门配置、职位设定 共七项评定指标
        float finishCount = 0;
        if (!StringUtils.isEmpty(manageClubModel.getAvatarUrl())) {
            finishCount ++;
        }
        if (!StringUtils.isEmpty(manageClubModel.getSlogan())) {
            finishCount ++;
        }
        if (!StringUtils.isEmpty(manageClubModel.getIntroduction())) {
            finishCount ++;
        }
        if (manageClubModel.getHonorCount() > 0) {
            finishCount ++;
        }
        if (manageClubModel.getMemberCount() > 1) {
            finishCount ++;
        }
        if (!StringUtils.isEmpty(manageClubModel.getDepartments())) {
            finishCount ++;
        }
        if (!StringUtils.isEmpty(manageClubModel.getJobs())) {
            finishCount ++;
        }

        if (finishCount > 0) {
            finishCount = finishCount / 7;
        }
        result.put("finish_count", String.format("%.2f", finishCount * 100));
        // 完成情况 END

        return result;
    }
}