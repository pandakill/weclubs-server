package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCSponsorMeetingModel;
import com.weclubs.model.WCStudentForClubModel;
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
    private WCIClubService mClubService;

    @Autowired
    public WCManageClubMeetingAPI(WCIClubMeetingService mClubMeetingService, WCISecurityService mSecurityService,
                                  WCIClubService clubService) {
        this.mClubMeetingService = mClubMeetingService;
        this.mSecurityService = mSecurityService;
        this.mClubService = clubService;
    }

    @RequestMapping(value = "/get_my_meeting", method = RequestMethod.POST)
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

        if (sponsorId <= 0) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "缺少参数：sponsor_id";
            return WCResultData.getHttpStatusData(check, null);
        }

        PageHelper.startPage(pageNo, pageSize);
        List<WCSponsorMeetingModel> meetingModelList = mClubMeetingService.getMeetingBySponsor(sponsorId);
        PageInfo<WCSponsorMeetingModel> pageInfo = new PageInfo<WCSponsorMeetingModel>(meetingModelList);

        List<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCSponsorMeetingModel meetingModel : pageInfo.getList()) {
                HashMap<String, Object> hash = getSponsorMeetingHash(meetingModel);
                resultArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("meeting", resultArray);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_my_meeting_detail", method = RequestMethod.POST)
    public WCResultData getMyMeetingDetail(@RequestBody WCRequestModel requestModel) {

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

        long meetingId = WCCommonUtil.getLongData(requestData.get("meeting_id"));
        WCSponsorMeetingModel meetingModel = mClubMeetingService.getSponsorMeetingDetail(meetingId);


        List<WCStudentBean> leaders = mClubMeetingService.getMeetingLeaderByMeetingId(meetingId);
        List<HashMap<String, Object>> leaderHash = new ArrayList<HashMap<String, Object>>();
        if (leaders != null && leaders.size() > 0) {
            for (WCStudentBean leader : leaders) {
                HashMap<String, Object> hash = new HashMap<String, Object>();
                WCStudentForClubModel studentForClubModel
                        = mClubService.getClubStudentByStudentId(leader.getStudentId(), meetingModel.getClubId());
                hash.put("student_id", studentForClubModel.getStudentId());
                hash.put("student_name", studentForClubModel.getRealName());
                hash.put("department_name", studentForClubModel.getDepartmentName());
                hash.put("job_name", studentForClubModel.getJobName());
                hash.put("mobile", studentForClubModel.getMobile());
                hash.put("avatar_url", studentForClubModel.getAvatarUrl());
                leaderHash.add(hash);
            }
        }

        HashMap<String, Object> result = getSponsorMeetingHash(meetingModel);
        result.put("leader", leaderHash);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/public_meeting", method = RequestMethod.POST)
    public WCResultData publicMeeting(@RequestBody WCRequestModel requestModel) {

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
        String address = (String) requestData.get("address");
        long deadline = WCCommonUtil.getLongData(requestData.get("deadline"));
        int needSign = WCCommonUtil.getIntegerData(requestData.get("need_sign"));
        String leader = null;
        if (requestData.containsKey("leader")) {
            leader = (String) requestData.get("leader");
        }
        String participation = (String) requestData.get("participation");
        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));

        check = mClubMeetingService.publicMeeting(sponsorId, content, address,
                deadline, needSign, leader, participation, clubId);

        return WCResultData.getHttpStatusData(check, null);
    }

    @RequestMapping(value = "/edit_meeting", method = RequestMethod.POST)
    public WCResultData editMeeting(@RequestBody WCRequestModel requestModel) {

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
        String address = (String) requestData.get("address");
        long deadline = WCCommonUtil.getLongData(requestData.get("deadline"));
        int needSign = WCCommonUtil.getIntegerData(requestData.get("need_sign"));
        String leader = null;
        if (requestData.containsKey("leader")) {
            leader = (String) requestData.get("leader");
        }
        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
        long meetingId = WCCommonUtil.getLongData(requestData.get("meeting_id"));

        check = mClubMeetingService.editMeeting(content, address, deadline, needSign, leader, clubId, meetingId);

        return WCResultData.getHttpStatusData(check, null);
    }

    private HashMap<String, Object> getSponsorMeetingHash(WCSponsorMeetingModel meetingModel) {
        HashMap<String, Object> result = new HashMap<String, Object>();
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

        result.put("already_confirm_count", meetingModel.getTotalCount() - meetingModel.getUnConfirmCount());
        result.put("unconfirm_count", meetingModel.getUnConfirmCount());
        result.put("total_count", meetingModel.getTotalCount());
        result.put("unsign_count", meetingModel.getTotalCount() - meetingModel.getSignCount());
        result.put("already_sign_count", meetingModel.getSignCount());

        return result;
    }
}
