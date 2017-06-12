package com.weclubs.api;

import com.weclubs.application.message.WCIMessageService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 消息相关的 api 接口
 *
 * Created by fangzanpan on 2017/6/12.
 */
@RestController
@RequestMapping(value = "/message")
class WCMessageAPI {

    private WCISecurityService mSecurityService;
    private WCIMessageService mMessageService;

    @Autowired
    public WCMessageAPI(WCISecurityService securityService, WCIMessageService messageService) {
        this.mSecurityService = securityService;
        this.mMessageService = messageService;
    }

    @RequestMapping(value = "/get_message_list", method = RequestMethod.POST)
    public WCResultData getMessageList(@RequestBody WCRequestModel requestModel) {

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

        return WCResultData.getHttpStatusData(check, null);
    }

}
