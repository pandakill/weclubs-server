package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.dynamic.WCIDynamicService;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMissionBaseModel;
import com.weclubs.model.WCSponsorMissionModel;
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
 * 社团任务管理相关的 API 接口
 *
 * Created by fangzanpan on 2017/4/20.
 */
@RestController
@RequestMapping(value = "/manage/club_mission")
class WCManageClubMissionAPI {

    private Logger log = Logger.getLogger(WCManageClubActivityAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubMissionService mClubMissionService;
    private WCIDynamicService mDynamicService;

    @Autowired
    public WCManageClubMissionAPI(WCISecurityService mSecurityService, WCIClubMissionService mClubMissionService) {
        this.mSecurityService = mSecurityService;
        this.mClubMissionService = mClubMissionService;
    }

    @RequestMapping(value = "/get_my_mission", method = RequestMethod.POST)
    public WCResultData getMyMission(@RequestBody WCRequestModel requestModel) {

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

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);
        long sponsorId = WCRequestParamsUtil.getUserId(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCSponsorMissionModel> missionModelList = mClubMissionService.getMissionBySponsorId(sponsorId);
        PageInfo<WCSponsorMissionModel> pageInfo = new PageInfo<WCSponsorMissionModel>(missionModelList);

        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCSponsorMissionModel sponsorMissionModel : pageInfo.getList()) {
                resultArray.add(sponsorMissionModel.getMissionHash());
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("mission", resultArray);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/public_mission", method = RequestMethod.POST)
    public WCResultData publicMission(@RequestBody WCRequestModel requestModel) {

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

        check = mClubMissionService.publicMission(requestData);

        return WCResultData.getHttpStatusData(check, null);
    }

    @RequestMapping(value = "/get_my_mission_detail", method = RequestMethod.POST)
    public WCResultData getMyMissionDetail(@RequestBody WCRequestModel requestModel) {

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

        long missionId = WCCommonUtil.getLongData(requestData.get("mission_id"));
        long studentId = WCCommonUtil.getLongData(requestData.get("user_id"));

        WCClubMissionBean detailBean = mClubMissionService.getMissionDetailById(missionId);
        detailBean.setChildMissonDetails(mClubMissionService.getChildMissionDetailByMissionIdWithStudent(studentId, missionId));

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("mission_id", detailBean.getMissionId());
        result.put("content", detailBean.getAttribution());
        result.put("create_date", detailBean.getCreateDate());
        result.put("deadline", detailBean.getDeadline());

        result.put("club_id", detailBean.getClubBean().getClubId());
        result.put("club_name", detailBean.getClubBean().getName());
        result.put("club_avatar", detailBean.getClubBean().getAvatarUrl());

//        if (detailBean.getChildMissonDetails() != null && detailBean.getChildMissonDetails().size() > 0) {
            List<HashMap<String, Object>> childMissionHash = new ArrayList<HashMap<String, Object>>();
            for (WCMissionBaseModel missionBaseModel : detailBean.getChildMissonDetails()) {
                HashMap<String, Object> hash = new HashMap<String, Object>();

                hash.put("mission_id", missionBaseModel.getMissionId());
                hash.put("content", missionBaseModel.getAttribution());

                List<HashMap<String, Object>> participants = new ArrayList<HashMap<String, Object>>();
                List<WCStudentMissionRelationBean> participantBeans
                        = mClubMissionService.getMissionRelationsByMissionId(missionBaseModel.getMissionId());

                if (participantBeans != null && participantBeans.size() > 0) {
                    for (WCStudentMissionRelationBean participation : participantBeans) {
                        HashMap<String, Object> student = new HashMap<String, Object>();
                        student.put("student_id", participation.getStudentId());
                        student.put("student_name", participation.getStudentBean().getRealName());
                        student.put("student_avatar", participation.getStudentBean().getAvatarUrl());

                        student.put("is_finish", participation.getStatus() == 2 ? 1 : 0);
                        student.put("is_confirm", participation.getStatus() > 0 ? 1 : 0);

                        student.put("has_child", participation.getChildMissionCount() > 0 ? 1 : 0);

                        participants.add(student);
                    }
                }
                hash.put("participant", participants);

                childMissionHash.add(hash);
            }

            result.put("child", childMissionHash);

        int status = 1;
        if (detailBean.getIsDel() == 1) {
            status = 0;
        } else if (detailBean.getDeadline() == 0) {
            status = WCCommonUtil.isExpire(detailBean.getCreateDate()) ? 2 : 1;
        } else if (detailBean.getDeadline() != 0) {
            status = WCCommonUtil.isExpire(detailBean.getDeadline()) ? 2 : 1;
        }
        result.put("status", status);
//        } else {
//            List<HashMap<String, Object>> participants = new ArrayList<HashMap<String, Object>>();
//            List<WCStudentMissionRelationBean> participantBeans = mClubMissionService.getMissionRelationsByMissionId(missionId);
//            for (WCStudentMissionRelationBean participation : participantBeans) {
//                HashMap<String, Object> hash = new HashMap<String, Object>();
//                hash.put("student_id", participation.getStudentId());
//                hash.put("student_name", participation.getStudentBean().getRealName());
//                hash.put("student_avatar", participation.getStudentBean().getAvatarUrl());
//
//                hash.put("is_finish", participation.getStatus() == 2 ? 1 : 0);
//                hash.put("is_confirm", participation.getStatus() > 0 ? 1 : 0);
//
//                hash.put("has_child", participation.getChildMissionCount() > 0 ? 1 : 0);
//
//                participants.add(hash);
//            }
//            result.put("participant", participants);
//        }

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/remind_confirm_mission", method = RequestMethod.POST)
    public WCResultData remindToConfirm(@RequestBody WCRequestModel requestModel) {

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

        long missionId = WCCommonUtil.getLongData(requestData.get("mission_id"));

        check = mClubMissionService.remindToUnConfirm(missionId);

        return WCResultData.getHttpStatusData(check, new HashMap<String, Object>());
    }

    @RequestMapping(value = "/remind_finish_mission", method = RequestMethod.POST)
    public WCResultData remindToFinish(@RequestBody WCRequestModel requestModel) {

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

        long missionId = WCCommonUtil.getLongData(requestData.get("mission_id"));

        check = mClubMissionService.remindToUnFinish(missionId);

        return WCResultData.getHttpStatusData(check, new HashMap<String, Object>());
    }

    @RequestMapping(value = "/revert_mission", method = RequestMethod.POST)
    public WCResultData revertMission(@RequestBody WCRequestModel requestModel) {

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

        long missionId = WCCommonUtil.getLongData(requestData.get("mission_id"));
        long userId = WCRequestParamsUtil.getUserId(requestModel);

        check = mClubMissionService.revertMission(missionId, userId);

        return WCResultData.getHttpStatusData(check, null);
    }
}
