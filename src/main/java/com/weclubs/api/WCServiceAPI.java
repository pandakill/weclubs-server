package com.weclubs.api;

import com.weclubs.application.qiniu.WCIQiNiuService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 综合类接口
 *
 * Created by fangzanpan on 2017/4/7.
 */
@RestController
@RequestMapping(value = "/service")
class WCServiceAPI {

    private Logger log = Logger.getLogger(WCServiceAPI.class);

    private WCISecurityService mSecurityService;
    private WCIQiNiuService mQiNiuService;

    @Autowired
    public WCServiceAPI(WCISecurityService mSecurityService, WCIQiNiuService mQiNiuService) {
        this.mSecurityService = mSecurityService;
        this.mQiNiuService = mQiNiuService;
    }

    @RequestMapping(value = "/get_upload_token", method = RequestMethod.POST)
    public WCResultData getUploadToken(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        String token = mQiNiuService.getUploadToken();

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_qiniu_config", method = RequestMethod.POST)
    public WCResultData getQiNiuConfig(@RequestBody WCRequestModel requestModel){

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("config", mQiNiuService.getUploadConfig());
        return WCResultData.getSuccessData(result);
    }
}
