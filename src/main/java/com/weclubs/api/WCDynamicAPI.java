package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.dynamic.WCIDynamicService;
import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCRequestModel;
import com.weclubs.model.WCResultData;
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
    private WCIUserService mUserService;

    @Autowired
    public WCDynamicAPI(WCISecurityService mSecurityService, WCIClubMeetingService mMeetingService,
                        WCINotificationService mNotifyService, WCIClubMissionService mMissionService,
                        WCIDynamicService dynamicService, WCIUserService userService) {
        this.mSecurityService = mSecurityService;
        this.mMeetingService = mMeetingService;
        this.mNotifyService = mNotifyService;
        this.mMissionService = mMissionService;
        this.mDynamicService = dynamicService;
        this.mUserService = userService;
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
        studentId = 1;

        PageHelper.startPage(pageNo, pageSize);

        List<WCStudentMissionRelationBean> resultList = new ArrayList<WCStudentMissionRelationBean>();
        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            resultList = mNotifyService.getNotificationsByStudentId(studentId);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            resultList = mMissionService.getMissionsByStudentId(studentId);
        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            resultList = mMeetingService.getMeetingsByStudentId(studentId);
        }
        PageInfo<WCStudentMissionRelationBean> pageInfo = new PageInfo<WCStudentMissionRelationBean>(resultList);

        ArrayList<HashMap<String, Object>> todoList = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList().size() > 0) {
            for (WCStudentMissionRelationBean studentMissionRelationBean : pageInfo.getList()) {
                HashMap<String, Object> hash = getTodoHash(studentMissionRelationBean, dynamicType);
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
        studentId = 1;

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

    private HashMap<String, Object> getTodoHash(WCStudentMissionRelationBean relationBean, String dynamicType) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("student_id", relationBean.getStudentId());
        WCClubMissionBean detailBean;

        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            result.put("notify_id", relationBean.getMissionId());
            result.put("confirm_receive", relationBean.getStatus() <= 0 ? 0 : 1);   // status <= 0 即为未确认

            detailBean = mNotifyService.getNotificationDetailById(relationBean.getMissionId());
            result.put("content", detailBean.getAttribution());
            result.put("create_date", detailBean.getCreateDate());
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            result.put("mission_id", relationBean.getMissionId());
            result.put("deadline",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getDeadline() : null);

            detailBean = mMissionService.getMissionDetailById(relationBean.getMissionId());
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

            detailBean = mMeetingService.getMeetingDetailById(relationBean.getMissionId());
            result.put("content", detailBean.getAttribution());
            result.put("create_date", detailBean.getCreateDate());

            boolean confirmReceive = relationBean.getStatus() > 0 && relationBean.getStatus() != 3;
            result.put("confirm_join", confirmReceive ? 1 : 0);

            result.put("has_sign", relationBean.getIsSign() == 1 ? 1 : 0);
        }

        return result;
    }

    private HashMap<String, Object> getDynamicDetailHash(long studentId, long dynamicId, String dynamicType) {

        HashMap<String, Object> result;

        WCStudentMissionRelationBean relationBean
                = mDynamicService.getDynamicStudentRelationByDynamicId(studentId, dynamicId, dynamicType);
        if (relationBean == null) {
            log.error("getDynamicDetailHash：relationBean == null");
            return null;
        }

        result = getTodoHash(relationBean, dynamicType);

        return result;
    }
}
