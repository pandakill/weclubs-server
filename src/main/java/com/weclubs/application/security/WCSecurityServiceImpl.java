package com.weclubs.application.security;

import com.weclubs.application.token.WCITokenService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.util.MD5;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * 安全service的检查接口
 *
 * Created by fangzanpan on 2017/2/5.
 */
@Service(value = "securityService")
class WCSecurityServiceImpl implements WCISecurityService {

    private final static String secretKey = MD5.md5("pukongjie");

    private Logger log = Logger.getLogger(WCSecurityServiceImpl.class);

    @Autowired
    private WCITokenService mTokenService;
    @Autowired
    private WCIUserService mUserService;

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

    public WCHttpStatus checkRequestID(String id) {
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
        WCHttpStatus check = WCHttpStatus.SUCCESS;

        if (requestModel.getClient().getCaller().equals("MTg4NDY4ZjQ1Yz")) {    // 如果caller为该值，则直接跳过验证
            return check;
        }

        check = checkRequestID(WCRequestParamsUtil.getRequestId(requestModel));
        if (check != WCHttpStatus.SUCCESS) {
            return checkRequestID(WCRequestParamsUtil.getRequestId(requestModel));
        } else {
            check = checkCallerAvailable(WCRequestParamsUtil.getClientCaller(requestModel));
            if (check != WCHttpStatus.SUCCESS) {
                return check;
            } else {
                check = checkDataAvailable((HashMap<String, Object>) WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class),
                        WCRequestParamsUtil.getSign(requestModel));
                if (check != WCHttpStatus.SUCCESS) {
                    return check;
                } else {
                    return WCHttpStatus.SUCCESS;
                }
            }
        }
    }

    public WCHttpStatus checkTokenAvailable(WCRequestModel requestModel) {
        long userId = WCRequestParamsUtil.getUserId(requestModel);
        if (userId == -1) {
            log.error("checkTokenAvailable：请求参数中没有携带 user_id");
            return WCHttpStatus.FAIL_DONT_HAVE_USER_ID;
        }
        String caller = WCRequestParamsUtil.getClientCaller(requestModel);
        String token = WCRequestParamsUtil.getToken(requestModel);
        if (StringUtils.isEmpty(token)) {
            log.error("checkTokenAvailable：请求参数中没有携带 token");
            return WCHttpStatus.FAIL_DONT_HAVE_TOKEN;
        }
        return checkTokenAvailable(userId, caller, token);
    }

    public WCHttpStatus checkCallerAvailable(String caller) {
        String[] callers = new String[]{"ios_test", "weclubs_android", "chrome_test"};

        boolean available = false;
        for (String s : callers) {
            if (s.equals(caller)) {
                available = true;
                break;
            }
        }
        if (available) {
            return WCHttpStatus.SUCCESS;
        } else {
            return WCHttpStatus.FAIL_REQUEST_UNVAILID_CALLER;
        }
    }

    public WCHttpStatus checkPasswordAvailable(WCStudentBean studentBean, String password) {
        String encodePsw = encodePassword(studentBean.getStudentId(), password);
        if (encodePsw.equals(studentBean.getPassword())) {
            return WCHttpStatus.SUCCESS;
        } else {
            return WCHttpStatus.FAIL_REQUEST;
        }
    }

    public WCHttpStatus checkUserIsAuthorized(long userId) {
        WCStudentBean user = mUserService.getUserInfoById(userId);
        if (user == null) {
            return WCHttpStatus.FAIL_USER_UNKNOWK;
        }

        if (user.getStatus() == 1) {
            return WCHttpStatus.SUCCESS;
        }

        return WCHttpStatus.FAIL_USER_UNAUTHORIZED;
    }

    public String encodePassword(long userId, String password) {
        String encodeBaseStr = password + "_userId=" +userId;
        return MD5.md5(encodeBaseStr).toLowerCase(Locale.getDefault());
    }

    /**
     * 签名方法
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     */
    private String sign(HashMap<String, Object> paramValues) {
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
            log.info("sign = " + sb.toString());
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            String md5Sign = MD5.md5(MD5.toHexString(sha1Digest));
            return md5Sign.toLowerCase();
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
