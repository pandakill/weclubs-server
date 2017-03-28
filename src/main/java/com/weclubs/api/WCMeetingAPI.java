package com.weclubs.api;

import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.WCMeetingParticipationModel;
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
 * 会议相关的 API 接口
 *
 * Created by fangzanpan on 2017/3/28.
 */
@RestController
@RequestMapping(value = "/meeting")
public class WCMeetingAPI {

    private Logger log = Logger.getLogger(WCMeetingAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIClubMeetingService mMeetingService;

    @RequestMapping(value = "/get_meeting_participation")
    public WCResultData getMeetingParticipation(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestParams == null || requestParams.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        long meetingId = 0;
        if (requestParams.get("meeting_id") instanceof Integer) {
            meetingId = (Integer) requestParams.get("meeting_id");
        } else if (requestParams.get("meeting_id") instanceof String) {
            meetingId = Long.parseLong((String) requestParams.get("meeting_id"));
        }

        List<WCMeetingParticipationModel> participationModels = mMeetingService.getMeetingParticipation(meetingId);
        ArrayList<HashMap<String, Object>>  hashArray = new ArrayList<HashMap<String, Object>>();
        if (participationModels != null && participationModels.size() > 0) {
            for (WCMeetingParticipationModel participationModel : participationModels) {
                HashMap<String, Object> hash = new HashMap<String, Object>();

                hash.put("is_confirm", participationModel.getStatus() == 1 ? 1 : 0);
                hash.put("is_leave", participationModel.getStatus() == 3 ? 1 : 0);
                hash.put("is_sign", participationModel.getIsSign());
                hash.put("sign_date", participationModel.getSignDate());
                hash.put("dynamic_date", participationModel.getCreateDate());

                hash.put("student_id", participationModel.getStudentId());
                hash.put("name", participationModel.getStudentName());
                hash.put("avatar_url", participationModel.getStudentAvatar());
                hash.put("department_name", participationModel.getDepartmentName());
                hash.put("job_name", participationModel.getJobName());

                hashArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("participation", hashArray);
        return WCResultData.getSuccessData(result);
    }
}
