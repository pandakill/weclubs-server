package com.weclubs.application.security;

import com.weclubs.application.token.WCITokenService;
import com.weclubs.model.WCRequestModel;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 安全service的检查接口
 *
 * Created by fangzanpan on 2017/2/5.
 */
@Service(value = "securityService")
public class WCSecurityServiceImpl implements WCISecurityService {

    private Logger log = Logger.getLogger(WCSecurityServiceImpl.class);

    @Autowired
    private WCITokenService mTokenService;

    public WCHttpStatus checkTokenAvailable(long userId, String caller, String token) {
        if (mTokenService.isTokenAvailable(userId, caller, token)) {
            return WCHttpStatus.SUCCESS;
        } else {
            return WCHttpStatus.FAIL_USER_TOKEN_UNVALID;
        }
    }

    public WCHttpStatus checkDataAvailable(HashMap<String, Object> data, String sign) {
        return WCHttpStatus.SUCCESS;
    }

    public WCHttpStatus checkRequestID(long id) {
        return WCHttpStatus.SUCCESS;
    }

    public WCHttpStatus checkRequestParams(HashMap<String, Object> params) {

        if (checkRequestID(WCRequestParamsUtil.getRequestId(params)) != WCHttpStatus.SUCCESS) {
            return checkRequestID(WCRequestParamsUtil.getRequestId(params));
        } else if (checkDataAvailable(WCRequestParamsUtil.getRequestParams(params), WCRequestParamsUtil.getSign(params))
                != WCHttpStatus.SUCCESS) {
            return checkDataAvailable(WCRequestParamsUtil.getRequestParams(params), WCRequestParamsUtil.getSign(params));
        }

        return WCHttpStatus.SUCCESS;
    }

    public WCHttpStatus checkRequestParams(WCRequestModel requestModel) {
        if (checkRequestID(WCRequestParamsUtil.getRequestId(requestModel)) != WCHttpStatus.SUCCESS) {
            return checkRequestID(WCRequestParamsUtil.getRequestId(requestModel));
        } else if (checkDataAvailable((HashMap<String, Object>) WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class),
                WCRequestParamsUtil.getSign(requestModel))
                != WCHttpStatus.SUCCESS) {
            return checkDataAvailable((HashMap<String, Object>) WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class),
                    WCRequestParamsUtil.getSign(requestModel));
        }

        return WCHttpStatus.SUCCESS;
    }

    public WCHttpStatus checkTokenAvailable(WCRequestModel requestModel) {
        long userId = WCRequestParamsUtil.getUserId(requestModel);
        String caller = WCRequestParamsUtil.getClientCaller(requestModel);
        String token = WCRequestParamsUtil.getToken(requestModel);
        return checkTokenAvailable(userId, caller, token);
    }
}
