package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.security.WCISecurityService;
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

    @Autowired
    public WCDynamicAPI(WCISecurityService mSecurityService, WCIClubMeetingService mMeetingService, WCINotificationService mNotifyService, WCIClubMissionService mMissionService) {
        this.mSecurityService = mSecurityService;
        this.mMeetingService = mMeetingService;
        this.mNotifyService = mNotifyService;
        this.mMissionService = mMissionService;
    }

    @RequestMapping(value = "/get_todo_list")
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

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        String todoType = (String) requestData.get("todo_type");
        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);
        log.info("getTodoList：todo_type = " + todoType);
        studentId = 1;

        PageHelper.startPage(pageNo, pageSize);

        List<WCStudentMissionRelationBean> resultList = new ArrayList<WCStudentMissionRelationBean>();
        if (Constants.TODO_NOTIFY.equals(todoType)) {
            resultList = mNotifyService.getNotificationsByStudentId(studentId);
        } else if (Constants.TODO_MISSION.equals(todoType)) {
            resultList = mMissionService.getMissionsByStudentId(studentId);
        } else if (Constants.TODO_MEETING.equals(todoType)) {
            resultList = mMeetingService.getMeetingsByStudentId(studentId);
        }
        PageInfo<WCStudentMissionRelationBean> pageInfo = new PageInfo<WCStudentMissionRelationBean>(resultList);

        ArrayList<HashMap<String, Object>> todoList = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList().size() > 0) {
            for (WCStudentMissionRelationBean studentMissionRelationBean : pageInfo.getList()) {
                HashMap<String, Object> hash = getTodoHash(studentMissionRelationBean, todoType);
                todoList.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("todo_type", todoType);
        result.put(todoType, todoList);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getTodoHash(WCStudentMissionRelationBean relationBean, String todoType) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("student_id", relationBean.getStudentId());
        result.put("create_date", relationBean.getCreateDate());
        result.put("content",
                relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getAttribution() : null);

        if (Constants.TODO_NOTIFY.equals(todoType)) {
            result.put("notify_id", relationBean.getMissionId());
            result.put("confirm_receive", relationBean.getStatus() <= 0 ? 0 : 1);   // status <= 0 即为未确认
        } else if (Constants.TODO_MISSION.equals(todoType)) {
            result.put("mission_id", relationBean.getMissionId());
            result.put("deadline",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getDeadline() : null);

            boolean confirmReceive = relationBean.getStatus() > 0;
            result.put("confirm_receive", confirmReceive ? 1 : 0);

            boolean finish = relationBean.getStatus() == 2;
            result.put("finish", finish ? 1 : 0);

        } else if (Constants.TODO_MEETING.equals(todoType)) {
            result.put("meeting_id", relationBean.getMissionId());
            result.put("deadline",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getDeadline() : null);

            result.put("address",
                    relationBean.getClubMissionBean() != null ? relationBean.getClubMissionBean().getAddress() : null);

            boolean confirmReceive = relationBean.getStatus() > 0 && relationBean.getStatus() != 3;
            result.put("confirm_join", confirmReceive ? 1 : 0);

            result.put("has_sign", relationBean.getIsSign() == 1 ? 1 : 0);
        }

        return result;
    }
}
