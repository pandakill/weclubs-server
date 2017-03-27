package com.weclubs.api;

import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 社团任务api
 *
 * Created by fangzanpan on 2017/3/14.
 */
@RestController
@RequestMapping(value = "/meeting")
class WCClubMeetingAPI {

    private Logger log = Logger.getLogger(WCClubMeetingAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubMeetingService mClubMeetingService;

    @Autowired
    public WCClubMeetingAPI(WCISecurityService mSecurityService, WCIClubMeetingService mClubMeetingService) {
        this.mSecurityService = mSecurityService;
        this.mClubMeetingService = mClubMeetingService;
    }

    @RequestMapping(value = "/get_meeting_by_student_id")
    public WCResultData getMeetingByStudentId(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getMissionByStudentId：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

//        check = mSecurityService.checkTokenAvailable(requestModel);
//        if (check != WCHttpStatus.SUCCESS) {
//            log.error("getMissionByStudentId：token失效");
//            return WCResultData.getHttpStatusData(check, null);
//        }

        try {
            HashMap<String, Object> result = new HashMap<String, Object>();

            HashMap requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
            if (requestParams == null || requestParams.size() <= 0) {
                check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
                return WCResultData.getHttpStatusData(check, null);
            }
            long studentId = Long.parseLong((String) requestParams.get("student_id"));

            List<WCStudentMissionRelationBean> studentMeetingRelations = mClubMeetingService.getMeetingsByStudentId(studentId);
            ArrayList<HashMap<String, Object>> resultMeetings = new ArrayList<HashMap<String, Object>>();
            if (studentMeetingRelations != null) {
                for (WCStudentMissionRelationBean studentMeetingRelation : studentMeetingRelations) {

                    HashMap<String, Object> meetHash = new HashMap<String, Object>();
                    meetHash.put("meeting_id", studentMeetingRelation.getMissionId());
                    meetHash.put("meeting_name", studentMeetingRelation.getClubMissionBean().getAttribution());
                    meetHash.put("dead_line", studentMeetingRelation.getClubMissionBean().getDeadline());
                    meetHash.put("create_date", studentMeetingRelation.getClubMissionBean().getCreateDate());
                    meetHash.put("comment_count", "2");

                    log.info("getMeetingByStudentId：studentMissionRelation.getStatus = " + studentMeetingRelation.getStatus());
                    meetHash.put("is_confirm", studentMeetingRelation.getStatus() == 0 ? "0" : "1");
                    meetHash.put("is_finish", studentMeetingRelation.getStatus() == 2 ? "1" : "0");
                    meetHash.put("show_scan", "1");

                    meetHash.put("is_leader", studentMeetingRelation.getIsLeader());

                    WCStudentBean studentBean = studentMeetingRelation.getClubMissionBean().getSponsorStudentBean();
                    if (studentBean != null) {
                        log.info("getMeetingByStudentId：" + studentBean.toString());
                        meetHash.put("sponsor_id", studentBean.getStudentId());
                        meetHash.put("sponsor_name", studentBean.getNickName());
                        meetHash.put("sponsor_avatar", studentBean.getAvatarUrl());
                    }

                    resultMeetings.add(meetHash);
                }
            }

            result.put("meeting", resultMeetings);

            return WCResultData.getSuccessData(result);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }

    }

    @RequestMapping(value = "/get_meeting_detail")
    public WCResultData getMeetingDetail(WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getMissionByStudentId：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

//        check = mSecurityService.checkTokenAvailable(requestModel);
//        if (check != WCHttpStatus.SUCCESS) {
//            log.error("getMissionByStudentId：token失效");
//            return WCResultData.getHttpStatusData(check, null);
//        }

        try {
            HashMap requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
            if (requestParams == null || requestParams.size() <= 0) {
                check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
                return WCResultData.getHttpStatusData(check, null);
            }
            long meetingId = Long.getLong((String) requestParams.get("meeting_id"));

            WCClubMissionBean clubMissionBean = mClubMeetingService.getMeetingDetailById(meetingId);
//            List<WCStudentMissionRelationBean> studentRelations = mClubMeetingService.

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        return WCResultData.getSuccessData(result);
    }
}
