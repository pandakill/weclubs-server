package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.model.WCSponsorMeetingModel;
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

import static com.weclubs.util.WCCommonUtil.getLongData;

/**
 * 会议管理的接口API文档
 *
 * Created by fangzanpan on 2017/4/18.
 */
@RestController
@RequestMapping(value = "/manage/club_meeting")
class WCManageClubMeetingAPI {

    private Logger log = Logger.getLogger(WCManageClubNotifyAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubMeetingService mClubMeetingService;

    @Autowired
    public WCManageClubMeetingAPI(WCIClubMeetingService mClubMeetingService, WCISecurityService mSecurityService) {
        this.mClubMeetingService = mClubMeetingService;
        this.mSecurityService = mSecurityService;
    }

    @RequestMapping(value = "/get_my_meeting")
    public WCResultData getMyMeeting(@RequestBody WCRequestModel requestModel) {

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
        long sponsorId = getLongData(requestData.get("sponsor_id"));

        PageHelper.startPage(pageNo, pageSize);
        List<WCSponsorMeetingModel> meetingModelList = mClubMeetingService.getMeetingBySponsor(sponsorId);
        PageInfo<WCSponsorMeetingModel> pageInfo = new PageInfo<>(meetingModelList);

        List<HashMap<String, Object>> resultArray = new ArrayList<>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCSponsorMeetingModel meetingModel : pageInfo.getList()) {
                HashMap<String, Object> hash = getSponsorMeetingHash(meetingModel);
                resultArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("meeting", resultArray);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getSponsorMeetingHash(WCSponsorMeetingModel meetingModel) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("meeting_id", meetingModel.getMissionId());
        result.put("content", meetingModel.getAttribution());
        result.put("create_date", meetingModel.getCreateDate());
        result.put("deadline", meetingModel.getDeadline());
        result.put("address", meetingModel.getAddress());

        result.put("club_id", meetingModel.getClubId());
        WCClubBean clubBean = meetingModel.getClubBean();
        result.put("club_name", clubBean == null ? null : clubBean.getName());
        result.put("club_avatar", clubBean == null ? null : clubBean.getAvatarUrl());

        result.put("sign_type", meetingModel.getSignType() == 0 ? 0 : 1);   // 0：不需要签到，大于0说明二维码签到或者签到已过期
        // 判断是否已经可以开始签到，以服务器时间为准
        // time_to_sign：0：还未到点签到；1：可以签到；2：停止签到；
        boolean timeToSign = false;
        if (meetingModel.getSignType() > 0 && meetingModel.getDeadline() >= System.currentTimeMillis()) {
            timeToSign = true;
        }
        result.put("time_to_sign", timeToSign ? 1 : (meetingModel.getSignType() == 0 ? 0 : 2));

        result.put("unconfirm_count", meetingModel.getUnConfirmCount());
        result.put("total_count", meetingModel.getTotalCount());
        result.put("already_sign_count", meetingModel.getSignCount());

        return result;
    }
}
