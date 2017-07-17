package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCSponsorNotifyModel;
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
 * 社团通知的管理相关 API 接口
 *
 * Created by fangzanpan on 2017/4/17.
 */
@RestController
@RequestMapping(value = "/manage/club_notify")
class WCManageClubNotifyAPI {

    private Logger log = Logger.getLogger(WCManageClubNotifyAPI.class);

    private WCISecurityService mSecurityService;
    private WCINotificationService mNotificationService;

    @Autowired
    public WCManageClubNotifyAPI(WCISecurityService mSecurityService, WCINotificationService mNotificationService) {
        this.mSecurityService = mSecurityService;
        this.mNotificationService = mNotificationService;
    }

    @RequestMapping(value = "/get_my_notify", method = RequestMethod.POST)
    public WCResultData getMyNotify(@RequestBody WCRequestModel requestModel) {

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

        long sponsorId = WCCommonUtil.getLongData(requestData.get("sponsor_id"));
        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCSponsorNotifyModel> notifyModelList = mNotificationService.getNotifyBySponsor(sponsorId);
        PageInfo<WCSponsorNotifyModel> pageInfo = new PageInfo<WCSponsorNotifyModel>(notifyModelList);

        List<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCSponsorNotifyModel sponsorNotifyModel : pageInfo.getList()) {
                HashMap<String, Object> hash = getNotifyHash(sponsorNotifyModel);
                resultArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String , Object>();
        result.put("notify", resultArray);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/public_notify", method = RequestMethod.POST)
    public WCResultData publicNotify(@RequestBody WCRequestModel requestModel) {

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

        long sponsorId = WCRequestParamsUtil.getUserId(requestModel);
        String content = (String) requestData.get("content");
        String students = (String) requestData.get("students");
        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));

        check = mNotificationService.publicNotify(sponsorId, content, clubId, students);

        return WCResultData.getHttpStatusData(check, null);
    }

    @RequestMapping(value = "/get_my_notify_detail", method = RequestMethod.POST)
    public WCResultData getMyNotifyDetail(@RequestBody WCRequestModel requestModel) {

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

        long notifyId = WCCommonUtil.getLongData(requestData.get("notify_id"));

        WCSponsorNotifyModel notifyModel = mNotificationService.getMyNotificationDetailById(notifyId);

        HashMap<String, Object> result = getNotifyHash(notifyModel);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_notify_check_status", method = RequestMethod.POST)
    public WCResultData getNotifyCheckStatus(@RequestBody WCRequestModel requestModel) {

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

        long notifyId = WCCommonUtil.getLongData(requestData.get("notify_id"));

        List<WCStudentMissionRelationBean> relationBeanList = mNotificationService.getNotifyRelationByNotifyId(notifyId);
        long unreadCount = 0;
        long totalCount = 0;
        ArrayList<HashMap<String, Object>> relationHash = new ArrayList<HashMap<String, Object>>();
        if (relationBeanList != null && relationBeanList.size() > 0) {
            totalCount = relationBeanList.size();
            for (WCStudentMissionRelationBean relationBean : relationBeanList) {
                HashMap<String, Object> hash = getStudentNotifyRelationHash(relationBean);
                relationHash.add(hash);

                if (relationBean.getStatus() == 0) {
                    unreadCount ++;
                }
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("unread_count", unreadCount);
        result.put("total_count", totalCount);
        result.put("already_read_count", totalCount - unreadCount);
        result.put("confirm_status", relationHash);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/remind_confirm_notify", method = RequestMethod.POST)
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

        long notifyId = WCCommonUtil.getLongData(requestData.get("notify_id"));

        check = mNotificationService.remindToUnConfirm(notifyId);

        return WCResultData.getHttpStatusData(check, new HashMap<String, Object>());
    }

    private HashMap<String, Object> getNotifyHash(WCSponsorNotifyModel notifyModel) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club_id", notifyModel.getClubId());
        result.put("club_name", notifyModel.getClubBean().getName());
        result.put("club_avatar", notifyModel.getClubBean().getAvatarUrl());

        result.put("notify_id", notifyModel.getMissionId());
        result.put("content", notifyModel.getAttribution());
        result.put("create_date", notifyModel.getCreateDate());

        result.put("dynamic_type", "notify");
        result.put("unread_count", notifyModel.getUnreadCount());
        result.put("total_count", notifyModel.getTotalCount());
        result.put("already_read_count", notifyModel.getTotalCount() - notifyModel.getUnreadCount());

        int status = 1;
        if (notifyModel.getIsDel() == 1) {
            status = 0;
        }
        result.put("status", status);

        return result;
    }

    private HashMap<String, Object> getStudentNotifyRelationHash(WCStudentMissionRelationBean relationBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("student_id", relationBean.getStudentId());

        WCStudentBean studentBean = relationBean.getStudentBean();
        result.put("student_avatar", studentBean != null ? studentBean.getAvatarUrl() : null);
        result.put("student_name", studentBean != null ? studentBean.getRealName() : null);

        result.put("is_confirm", relationBean.getStatus() == 0 ? 0 : 1);
        if (relationBean.getSignDate() > 0) {
            result.put("create_date", relationBean.getSignDate());
        } else {
            result.put("create_date", relationBean.getCreateDate());
        }

        return result;
    }
}
