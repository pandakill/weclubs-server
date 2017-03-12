package com.weclubs.application.security;

import com.weclubs.application.token.WCITokenService;
import com.weclubs.model.WCRequestModel;
import com.weclubs.util.MD5;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 安全service的检查接口
 *
 * Created by fangzanpan on 2017/2/5.
 */
@Service(value = "securityService")
public class WCSecurityServiceImpl implements WCISecurityService {

    private final static String secretKey = MD5.md5("pukongjie");

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
        String signStr = sign(data);
        log.info("checkDataAvailable：签名后的数据是：" + signStr);
        if (sign.equals(signStr)) {
            return WCHttpStatus.SUCCESS;
        } else {
            return WCHttpStatus.FAIL_REQUEST_UNVAILID_SIGN;
        }
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

    /**
     * 签名方法
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     */
    public String sign(HashMap<String, Object> paramValues) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            Collections.sort(paramNames);

            sb.append(secretKey);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secretKey);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return MD5.toHexString(sha1Digest);
        } catch (IOException e) {
            log.error("sign：Sign 值签名错误");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SHA1加密
     */
    private byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            log.error("getSHA1Digest：SHA1加密失败");
            gse.printStackTrace();
        }

        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     */
    private String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

}
