package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.dynamic.WCIDynamicService;
import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMissionBaseModel;
import com.weclubs.model.WCStudentForClubModel;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.Constants;
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
 * 动态相关的 API
 *
 * Created by fangzanpan on 2017/3/24.
 */
@RestController
@RequestMapping(value = "/dynamic")
class WCDynamicAPI {

    private Logger log = Logger.getLogger(WCDynamicAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubMeetingService mMeetingService;
    private WCINotificationService mNotifyService;
    private WCIClubMissionService mMissionService;
    private WCIDynamicService mDynamicService;
    private WCIClubService mClubService;

    @Autowired
    public WCDynamicAPI(WCISecurityService mSecurityService, WCIClubMeetingService mMeetingService,
                        WCINotificationService mNotifyService, WCIClubMissionService mMissionService,
                        WCIDynamicService dynamicService, WCIClubService clubService) {
        this.mSecurityService = mSecurityService;
        this.mMeetingService = mMeetingService;
        this.mNotifyService = mNotifyService;
        this.mMissionService = mMissionService;
        this.mDynamicService = dynamicService;
        this.mClubService = clubService;
    }

    @RequestMapping(value = "/get_dynamic_list")
    public WCResultData getTodoList(@RequestBody WCRequestModel requestModel) {

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

        if (!requestData.containsKey("dynamic_type")) {
            check = WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        String dynamicType = (String) requestData.get("dynamic_type");
        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);
        log.info("getTodoList：dynamicType = " + dynamicType);
        long clubId = 0;
        if (requestData.get("club_id") instanceof String) {
            clubId = Long.parseLong((String) requestData.get("club_id"));
        } else if (requestData.get("club_id") instanceof Integer) {
            clubId = (Integer) requestData.get("club_id");
        }

        PageHelper.startPage(pageNo, pageSize);

        List<WCStudentMissionRelationBean> resultList = new ArrayList<WCStudentMissionRelationBean>();
        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            resultList = mNotifyService.getNotificationsByStudentId(studentId, clubId);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            resultList = mMissionService.getMissionsByStudentId(studentId, clubId);
        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            resultList = mMeetingService.getMeetingsByStudentId(studentId, clubId);
        }
        PageInfo<WCStudentMissionRelationBean> pageInfo = new PageInfo<WCStudentMissionRelationBean>(resultList);

        ArrayList<HashMap<String, Object>> todoList = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCStudentMissionRelationBean studentMissionRelationBean : pageInfo.getList()) {
                HashMap<String, Object> hash = getTodoHash(studentMissionRelationBean, dynamicType, null);
                todoList.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("dynamicType", dynamicType);
        result.put(dynamicType, todoList);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_dynamic_detail")
    public WCResultData getNotifyDetail(@RequestBody WCRequestModel requestModel) {

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

        if (!requestData.containsKey("dynamic_id") || !requestData.containsKey("dynamic_type")) {
            check = WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = WCRequestParamsUtil.getUserId(requestModel);

        long dynamicId = 0;
        if (requestData.get("dynamic_id") instanceof String) {
            dynamicId = Long.parseLong((String) requestData.get("dynamic_id"));
        } else if (requestData.get("dynamic_id") instanceof Integer) {
            dynamicId = (Integer) requestData.get("dynamic_id");
        }

        String dynamicType = (String) requestData.get("dynamic_type");

        HashMap<String, Object> result = getDynamicDetailHash(studentId, dynamicId, dynamicType);
        if (result == null) {
            result = new HashMap<String, Object>();
        }
        result.put("dynamic_type", dynamicType);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getTodoHash(WCStudentMissionRelationBean relationBean,
                                                String dynamicType, WCClubMissionBean detailBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        HashMap<String, Object> sponsor = new HashMap<String, Object>();

        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            result.put("notify_id", relationBean.getMissionId());
            result.put("confirm_receive", relationBean.getStatus() <= 0 ? 0 : 1);   // status <= 0 即为未确认

            if (detailBean == null) {
                detailBean = mNotifyService.getNotificationDetailById(relationBean.getMissionId());
            }
            result.put("content", detailBean.getAttribution());
            result.put("create_date", detailBean.getCreateDate());
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            result.put("mission_id", relationBean.getMissionId());
            result.put("deadline",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getDeadline() : null);

            if (detailBean == null) {
                detailBean = mMissionService.getMissionDetailWithChildById(relationBean.getMissionId());
            }
            result.put("content", detailBean.getAttribution());
            result.put("create_date", detailBean.getCreateDate());

            boolean confirmReceive = relationBean.getStatus() > 0;
            result.put("confirm_receive", confirmReceive ? 1 : 0);

            boolean finish = relationBean.getStatus() == 2;
            result.put("finish", finish ? 1 : 0);

        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            result.put("meeting_id", relationBean.getMissionId());
            result.put("deadline",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getDeadline() : null);

            result.put("address",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getAddress() : null);

            if (detailBean == null) {
                detailBean = mMeetingService.getMeetingDetailById(relationBean.getMissionId());
            }
            result.put("content", detailBean.getAttribution());
            result.put("create_date", detailBean.getCreateDate());

            boolean confirmReceive = relationBean.getStatus() > 0 && relationBean.getStatus() != 3;
            result.put("confirm_join", confirmReceive ? 1 : 0);

            result.put("has_sign", relationBean.getIsSign() == 1 ? 1 : 0);
        }

        if (detailBean != null) {
            sponsor.put("sponsor_id", detailBean.getSponsorId());
            if (detailBean.getSponsorStudentBean() != null) {
                sponsor.put("sponsor_name", detailBean.getSponsorStudentBean().getRealName());
                sponsor.put("sponsor_avatar", detailBean.getSponsorStudentBean().getAvatarUrl());
            }
            sponsor.put("sponsor_type", "student");
        }
        result.put("sponsor", sponsor);

        return result;
    }

    private HashMap<String, Object> getDynamicDetailHash(long studentId, long dynamicId, String dynamicType) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        WCStudentMissionRelationBean relationBean
                = mDynamicService.getDynamicStudentRelationByDynamicId(studentId, dynamicId, dynamicType);
        WCClubMissionBean detailBean;

        if (relationBean == null) {
            log.error("getDynamicDetailHash：relationBean == null");
            return null;
        }

        if (Constants.TODO_MEETING.equals(dynamicType)) {
            List<WCStudentBean> leaders = mMeetingService.getMeetingLeaderByMeetingId(dynamicId);
            List<HashMap<String, Object>> leaderHash = new ArrayList<HashMap<String, Object>>();
            detailBean = mMeetingService.getMeetingDetailById(dynamicId);
            if (leaders != null && leaders.size() > 0) {
                for (WCStudentBean leader : leaders) {
                    HashMap<String, Object> hash = new HashMap<String, Object>();
                    WCStudentForClubModel studentForClubModel
                            = mClubService.getClubStudentByStudentId(leader.getStudentId(), detailBean.getClubId());
                    hash.put("student_id", studentForClubModel.getStudentId());
                    hash.put("student_name", studentForClubModel.getRealName());
                    hash.put("department_name", studentForClubModel.getDepartmentName());
                    hash.put("job_name", studentForClubModel.getJobName());
                    hash.put("mobile", studentForClubModel.getMobile());
                    leaderHash.add(hash);
                }
            }
            result = getTodoHash(relationBean, dynamicType, detailBean);
            result.put("leader", leaderHash);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            detailBean = mMissionService.getMissionDetailById(dynamicId);
            detailBean.setChildMissonDetails(mMissionService.getChildMissionDetailByMissionIdWithStudent(studentId, dynamicId));

            result = getTodoHash(relationBean, dynamicType, detailBean);

            ArrayList<HashMap<String, Object>> childMissions = new ArrayList<HashMap<String, Object>>();
            if (detailBean.getChildMissonDetails() != null && detailBean.getChildMissonDetails().size() > 0) {
                for (WCMissionBaseModel missionBaseModel : detailBean.getChildMissonDetails()) {
                    HashMap<String, Object> child = new HashMap<String, Object>();
                    child.put("mission_id", missionBaseModel.getMissionId());
                    child.put("content", missionBaseModel.getAttribution());
                    child.put("finish", missionBaseModel.getStatus() == 2 ? 1 : 0);
                    child.put("has_child", missionBaseModel.getChildCount() > 0 ? 1 : 0);

                    List<HashMap<String, Object>> participants = new ArrayList<HashMap<String, Object>>();
                    List<WCStudentBean> participantBeans = mMissionService.getRelatedStudentByMissionId(missionBaseModel.getMissionId());
                    for (WCStudentBean studentBean : participantBeans) {
                        HashMap<String, Object> hash = new HashMap<String, Object>();
                        hash.put("student_id", studentBean.getStudentId());
                        hash.put("student_name", studentBean.getRealName());
                        hash.put("avatar_url", studentBean.getAvatarUrl());
                        participants.add(hash);
                    }
                    child.put("participant", participants);

                    childMissions.add(child);
                }

                result.put("child", childMissions);
            } else {
                List<HashMap<String, Object>> participants = new ArrayList<HashMap<String, Object>>();
                List<WCStudentBean> participantBeans = mMissionService.getRelatedStudentByMissionId(dynamicId);
                for (WCStudentBean studentBean : participantBeans) {
                    HashMap<String, Object> hash = new HashMap<String, Object>();
                    hash.put("student_id", studentBean.getStudentId());
                    hash.put("student_name", studentBean.getRealName());
                    hash.put("avatar_url", studentBean.getAvatarUrl());
                    participants.add(hash);
                }
                result.put("participant", participants);
            }


        } else if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            result = getTodoHash(relationBean, dynamicType, null);
        }

        return result;
    }
}
