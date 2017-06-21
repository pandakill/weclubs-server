package com.weclubs.api;

import com.weclubs.application.rongcloud.WCIRongCloudService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * IM 相关的 API 接口
 *
 * Created by fangzanpan on 2017/6/22.
 */
@RestController
@RequestMapping(value = "/im")
class WCIMAPI {

    private static Logger log = Logger.getLogger(WCIMAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIRongCloudService mRongCloudService;

    @RequestMapping(value = "/get_token", method = RequestMethod.POST)
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

        String token = mRongCloudService.getUserToken(WCCommonUtil.getLongData(requestData.get("user_id")));
        if (StringUtils.isEmpty(token)) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "获取token失败，请检查后重试";
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("im_token", token);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_group_chat_list", method = RequestMethod.POST)
    public WCResultData getGroupChatList(@RequestBody WCRequestModel requestModel) {

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

        long userId = WCCommonUtil.getLongData(requestData.get("user_id"));
        List<HashMap<String, Object>> groupChatList = mRongCloudService.getMyClubChatListForMap(userId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("group_chat_list", (groupChatList == null || groupChatList.size() == 0) ? null : groupChatList);

        return WCResultData.getSuccessData(result);
    }
}
