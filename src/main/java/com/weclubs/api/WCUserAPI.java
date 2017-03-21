package com.weclubs.api;

import com.weclubs.application.security.WCISecurityService;
import com.weclubs.application.token.WCITokenService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCRequestModel;
import com.weclubs.model.WCResultData;
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

/**
 * 用户api接口
 *
 * Created by fangzanpan on 2017/3/19.
 */
@RestController
@RequestMapping("/user")
public class WCUserAPI {

    private Logger log = Logger.getLogger(WCUserAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIUserService mUserService;
    @Autowired
    private WCITokenService mTokenService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public WCResultData register(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("register：注册失败，请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null) {
            log.error("register：请求参数为空");
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
        }

        String mobile = (String) requestData.get("mobile");

        if (StringUtils.isEmpty(mobile)) {
            log.error("register：请求参数中的mobile参数为空");
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }

        int checkUserExit = mUserService.checkMobileExist(mobile);
        if (checkUserExit != 404) {

            check = WCHttpStatus.FAIL_CUSTOM_DAILOG;

            if (checkUserExit == 0) {
                check.msg = "已经存在该手机用户";
            }
            return WCResultData.getHttpStatusData(check, null);

        } else {
            WCStudentBean createStatus = mUserService.createUserByMobile(mobile);
            if (createStatus == null || createStatus.getId() == 0) {
                check = WCHttpStatus.FAIL_CUSTOM_DAILOG;
                check.msg = "未知错误";
                return WCResultData.getHttpStatusData(check, null);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("user_id", createStatus.getId());
            result.put("mobile", createStatus.getMobile());
            return WCResultData.getSuccessData(result);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public WCResultData login(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("login：登录，请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null) {
            log.error("login：请求参数为空");
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
        }

        String mobile = (String) requestData.get("mobile");
        String password = (String) requestData.get("password");
        String caller = WCRequestParamsUtil.getClientCaller(requestModel);

        if (StringUtils.isEmpty(mobile)) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "请输入正确的手机号码";
            return WCResultData.getHttpStatusData(check, null);
        } else if (StringUtils.isEmpty(password)) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "密码不能为空";
            return WCResultData.getHttpStatusData(check, null);
        }

        WCStudentBean student = mUserService.getUserInfoByMobile(mobile);
        if (student == null || student.getStatus() == 0) {
            check = WCHttpStatus.FAIL_USER_UNKNOWK;
            check.msg = "用户名或密码错误，请重新输入";
            return WCResultData.getHttpStatusData(check, null);
        } else {
            if (!password.equals(student.getPassword())) {
                check = WCHttpStatus.FAIL_REQUEST;
                check.msg = "用户名或密码错误，请重新输入";
                return WCResultData.getHttpStatusData(check, null);
            } else {
                String token = mTokenService.createTokenByUserIdAndCaller(student.getId(), student.getPassword(), caller);

                HashMap<String, Object> result = getUserInfoByStudent(student);
                result.put("token", token);

                return WCResultData.getSuccessData(result);
            }
        }
    }

    /**
     * 根据 student 实体获取得到 userInfo 的字典
     *
     * @param student   学生实体
     *
     * @return  userInfo 的字典
     */
    private HashMap<String, Object> getUserInfoByStudent(WCStudentBean student) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user_id", student.getId());
        result.put("mobile", student.getMobile());
        result.put("nick_name", student.getNickName());
        result.put("real_name", student.getRealName());
        result.put("avatar_url", student.getAvatarUrl());
        result.put("gender", 1);

        result.put("school_id", student.getSchoolId());
        result.put("school_name", student.getSchoolBean() == null ? "" : student.getSchoolBean().getName());

        return result;
    }
}