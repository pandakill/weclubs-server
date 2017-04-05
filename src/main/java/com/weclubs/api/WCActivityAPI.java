package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.activity.WCIActivityService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.WCActivityDetailBaseModel;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
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
 * 活动相关的 API
 *
 * Created by fangzanpan on 2017/3/28.
 */
@RestController
@RequestMapping(value = "/activity")
class WCActivityAPI {

    private Logger log = Logger.getLogger(WCActivityAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIActivityService mActivityService;

    @RequestMapping(value = "/get_club_activities", method = RequestMethod.POST)
    public WCResultData getClubActivities(@RequestBody WCRequestModel requestModel) {

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

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCActivityDetailBaseModel> activities = mActivityService.getActivitiesByCurrentClub(clubId);
        PageInfo<WCActivityDetailBaseModel> pageInfo = new PageInfo<WCActivityDetailBaseModel>(activities);
        List<HashMap<String, Object>> activityHashs = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCActivityDetailBaseModel activityDetailBaseModel : pageInfo.getList()) {
                HashMap<String, Object> hash = getClubDetailBaseInfo(activityDetailBaseModel);

                activityHashs.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("activity", activityHashs);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_activity_detail")
    public WCResultData getActivityDetail(@RequestBody WCRequestModel requestModel) {

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

        long activityId = 0;
        if (requestData.get("activity_id") instanceof String) {
            activityId = Long.parseLong((String) requestData.get("activity_id"));
        } else if (requestData.get("activity_id") instanceof Integer) {
            activityId = (Integer) requestData.get("activity_id");
        }

        WCActivityDetailBaseModel activityDetailBaseModel = mActivityService.getActivityDetail(activityId);
        HashMap<String, Object> result = getClubDetailBaseInfo(activityDetailBaseModel);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getClubDetailBaseInfo(WCActivityDetailBaseModel detailBaseModel) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("club_id", detailBaseModel.getClubId());
        result.put("club_name", detailBaseModel.getClubName());

        result.put("activity_id", detailBaseModel.getActivityId());
        result.put("activity_name", detailBaseModel.getActivityName());
        result.put("attribution", detailBaseModel.getAttribution());
        result.put("apply_deadline", detailBaseModel.getApplyDeadline());
        result.put("hold_date", detailBaseModel.getHoldDate());
        result.put("hold_deadline", detailBaseModel.getHoldDeadline());
        result.put("allow_apply", detailBaseModel.getAllowApply());
        result.put("allow_pre_apply", detailBaseModel.getAllowPreApply());

        result.put("comment_count", detailBaseModel.getCommentCount());
        result.put("love_count", detailBaseModel.getLoveCount());
        result.put("favor_count", detailBaseModel.getFavorCount());

        return result;
    }
}
