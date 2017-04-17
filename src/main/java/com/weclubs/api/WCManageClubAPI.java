package com.weclubs.api;

import com.weclubs.application.club.WCIClubGraduateService;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.club_responsibility.WCIClubResponsibilityService;
import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.model.WCManageClubModel;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
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
@RequestMapping(value = "/manage/club")
class WCManageClubAPI {

    private Logger log = Logger.getLogger(WCManageClubAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubService mClubService;
    private WCISchoolService mSchoolService;
    private WCIUserService mUserService;
    private WCIClubResponsibilityService mClubResponsibilityService;
    private WCIClubGraduateService mClubGraduateService;

    @Autowired
    public WCManageClubAPI(WCIUserService mUserService, WCISchoolService mSchoolService,
                           WCISecurityService mSecurityService, WCIClubService mClubService,
                           WCIClubResponsibilityService mClubResponsibilityService,
                           WCIClubGraduateService graduateService) {
        this.mUserService = mUserService;
        this.mSchoolService = mSchoolService;
        this.mSecurityService = mSecurityService;
        this.mClubService = mClubService;
        this.mClubResponsibilityService = mClubResponsibilityService;
        this.mClubGraduateService = graduateService;
    }

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
    public WCResultData createClub(@RequestBody WCRequestModel requestModel) {

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

    @RequestMapping(value = "/get_club_department")
    public WCResultData getClubDepartment(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        int pureSelected = 0;
        if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        } else if (requestData.get("club_id") instanceof String) {
            clubId = Integer.parseInt((String) requestData.get("club_id"));
        }
        if (requestData.containsKey("pure_selected")) {
            if (requestData.get("pure_selected") instanceof Integer) {
                pureSelected = (Integer) requestData.get("pure_selected");
            } else if (requestData.get("pure_selected") instanceof String) {
                pureSelected = Integer.parseInt((String) requestData.get("pure_selected"));
            }
        }

        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        List<WCClubDepartmentBean> clubDepartmentBeans = mClubResponsibilityService.getDepartmentsByClubId(clubId, pureSelected == 1);
        if (clubDepartmentBeans != null && clubDepartmentBeans.size() > 0) {
            for (WCClubDepartmentBean clubDepartmentBean : clubDepartmentBeans) {
                HashMap<String, Object> departmentHash = getDepartmentHash(clubDepartmentBean);
                resultArray.add(departmentHash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("department", resultArray);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_club_job")
    public WCResultData getClubJob(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        ArrayList<HashMap<String, Object>> jobArrayHash = new ArrayList<HashMap<String, Object>>();
        List<WCClubJobBean> suggestJobs = mClubResponsibilityService.getJobsByClubId(clubId);
        if (suggestJobs != null && suggestJobs.size() > 0) {
            for (WCClubJobBean suggestJob : suggestJobs) {
                HashMap<String, Object> hash = getJobHash(suggestJob);
                jobArrayHash.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("job", jobArrayHash);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/set_department")
    public WCResultData setDepartment(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        String selected = null;
        if (requestData.containsKey("selected")) {
            selected = (String) requestData.get("selected");
        }
        String newDepartments = null;
        if (requestData.containsKey("new_department")) {
            newDepartments = (String) requestData.get("new_department");
        }

        if (StringUtils.isEmpty(selected) && StringUtils.isEmpty(newDepartments)) {
            log.info("社团 id = 【" + clubId + "】删除所有部门");
        }

        mClubResponsibilityService.setNewDepartmentsByClubId(clubId, selected, newDepartments);

        HashMap<String, Object> result = new HashMap<String, Object>();
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/set_job")
    public WCResultData setJob(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        String jobAuth = (String) requestData.get("job_auth");
        try {
            JSONObject jsonObject = new JSONObject(jobAuth);
            log.info("setJob：jobAuthJsonObj = " + jsonObject.toString());

            mClubResponsibilityService.setNewJobByClubId(clubId, jsonObject);

            HashMap<String, Object> result = new HashMap<String, Object>();
            return WCResultData.getSuccessData(result);
        } catch (JSONException e) {
            log.error("setJob：jobAuth = " + jobAuth);
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            e.printStackTrace();
            return WCResultData.getHttpStatusData(check, null);
        }
    }

    @RequestMapping(value = "/get_all_authority")
    public WCResultData getAllAuthority(@RequestBody WCRequestModel requestModel) {

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

        List<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        List<WCClubAuthorityBean> authorityBeans = mClubResponsibilityService.getAllAuthority();
        for (WCClubAuthorityBean authorityBean : authorityBeans) {
            resultArray.add(getAuthorityHash(authorityBean));
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("authority", resultArray);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_club_honor")
    public WCResultData getClubHonor(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        List<WCClubHonorBean> honorBeanList = mClubService.getClubHonorByClubId(clubId);
        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        if (honorBeanList != null && honorBeanList.size() > 0) {
            for (WCClubHonorBean clubHonorBean : honorBeanList) {
                HashMap<String, Object> hash = getHonorHash(clubHonorBean);
                resultArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("honor", resultArray);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/add_club_honor")
    public WCResultData addClubHonor(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        ArrayList<HashMap<String, Object>> honorList = (ArrayList<HashMap<String, Object>>) requestData.get("honor");
        if (honorList == null || honorList.size() == 0) {
            log.error("addClubHonor：添加部门荣誉失败");
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "荣誉列表不能为空";
            return WCResultData.getHttpStatusData(check, null);
        }

        mClubService.addClubHonor(clubId, honorList);

        HashMap<String, Object> result = new HashMap<String, Object>();
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/edit_club_honor")
    public WCResultData editClubHonor(@RequestBody WCRequestModel requestModel) {

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

        ArrayList<HashMap<String, Object>> honorList = (ArrayList<HashMap<String, Object>>) requestData.get("honor");
        if (honorList == null || honorList.size() == 0) {
            log.error("editClubHonor：编辑部门荣誉失败");
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "荣誉列表不能为空";
            return WCResultData.getHttpStatusData(check, null);
        }

        mClubService.updateClubHonor(honorList);

        HashMap<String, Object> result = new HashMap<>();
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/edit_club_introduction")
    public WCResultData editClubIntroduction(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        String introduction = (String) requestData.get("introduction");

        WCClubBean clubBean = mClubService.getClubInfoById(clubId);
        if (clubBean == null) {
            check = WCHttpStatus.FAIL_REQUEST;
            return WCResultData.getHttpStatusData(check, null);
        }

        clubBean.setIntroduction(introduction);
        mClubService.updateClub(clubBean);

        HashMap<String, Object> result = new HashMap<>();
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/edit_club_slogan")
    public WCResultData editClubSlogan(@RequestBody WCRequestModel requestModel) {

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

        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        WCClubBean clubBean = mClubService.getClubInfoById(clubId);
        if (clubBean == null) {
            check = WCHttpStatus.FAIL_REQUEST;
            return WCResultData.getHttpStatusData(check, null);
        }

        String slogan = (String) requestData.get("slogan");

        clubBean.setSlogan(slogan);
        mClubService.updateClub(clubBean);

        HashMap<String, Object> result = new HashMap<>();
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/set_student_department")
    public WCResultData setStudentDepartment(@RequestBody WCRequestModel requestModel) {

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

        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
        long studentId = WCCommonUtil.getLongData(requestData.get("student_id"));
        long departmentId = WCCommonUtil.getLongData(requestData.get("department_id"));

        check = mClubGraduateService.updateClubCurrentGraduateStudentDepartment(clubId, studentId, departmentId);

        return WCResultData.getHttpStatusData(check, null);
    }

    @RequestMapping(value = "/set_student_job")
    public WCResultData setStudentJob(@RequestBody WCRequestModel requestModel) {

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

        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
        long studentId = WCCommonUtil.getLongData(requestData.get("student_id"));
        long jobId = WCCommonUtil.getLongData(requestData.get("job_id"));

        check = mClubGraduateService.updateClubCurrentGraduateStudentJob(clubId, studentId, jobId);

        return WCResultData.getHttpStatusData(check, null);
    }

    @RequestMapping(value = "/delete_student")
    public WCResultData deleteStudent(@RequestBody WCRequestModel requestModel) {

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

        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
        long studentId = WCCommonUtil.getLongData(requestData.get("student_id"));

        check = mClubGraduateService.deleteStudentFromClubCurrentGraduate(clubId, studentId);

        return WCResultData.getHttpStatusData(check, null);
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

    private HashMap<String, Object> getDepartmentHash(WCClubDepartmentBean departmentBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("department_id", departmentBean.getDepartmentId());
        result.put("department_name", departmentBean.getDepartmentName());
        result.put("is_selected", departmentBean.getIsSelected());

        return result;
    }

    private HashMap<String, Object> getJobHash(WCClubJobBean jobBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("job_id", jobBean.getJobId());
        result.put("job_name", jobBean.getJobName());
        result.put("is_selected", jobBean.getIsSelected());

        ArrayList<HashMap<String, Object>> authArray = new ArrayList<HashMap<String, Object>>();
        if (jobBean.getAuthorities() != null && jobBean.getAuthorities().size() > 0) {
            for (WCClubAuthorityBean authorityBean : jobBean.getAuthorities()) {
                HashMap<String, Object> hash = new HashMap<String, Object>();
                hash.put("authority_id", authorityBean.getClubAuthorityId());
                hash.put("authority_name", authorityBean.getName());
                authArray.add(hash);
            }
        }

        result.put("authority", authArray);
        return result;
    }

    private HashMap<String, Object> getAuthorityHash(WCClubAuthorityBean authorityBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("authority_id", authorityBean.getClubAuthorityId());
        result.put("authority_name", authorityBean.getName());
        result.put("authority_attribution", authorityBean.getAttribute());
        return result;
    }

    private HashMap<String, Object> getHonorHash(WCClubHonorBean honorBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("honor_id", honorBean.getClubId());
        result.put("honor_name", honorBean.getContent());
        result.put("get_date", honorBean.getGetDate());
        return result;
    }
}
