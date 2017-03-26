package com.weclubs.api;

import com.weclubs.application.mob_sms.SmsVerifyKit;
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
class WCUserAPI {

    private Logger log = Logger.getLogger(WCUserAPI.class);

    private WCISecurityService mSecurityService;
    private WCIUserService mUserService;
    private WCITokenService mTokenService;

    @Autowired
    public WCUserAPI(WCIUserService mUserService, WCITokenService mTokenService, WCISecurityService mSecurityService) {
        this.mUserService = mUserService;
        this.mTokenService = mTokenService;
        this.mSecurityService = mSecurityService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public WCResultData register(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("register：注册失败，请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("register：请求参数为空");
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
        }

        String mobile = (String) requestData.get("mobile");
        String code = (String) requestData.get("code");

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

            SmsVerifyKit smsVerifyKit = new SmsVerifyKit(WCRequestParamsUtil.getClientCaller(requestModel), mobile, code);
            boolean verifySuccess = true;
            try {
                SmsVerifyKit.STATUS verifyStatus = smsVerifyKit.go();
                if (verifyStatus != SmsVerifyKit.STATUS.SUCCESS) {
                    verifySuccess = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                verifySuccess = false;
            }

            log.error("最终结果是：verifySuccess = " + verifySuccess);

            if (!verifySuccess) {
                check = WCHttpStatus.FAIL_REQUEST;
                check.msg = "验证码错误";
                return WCResultData.getHttpStatusData(check, null);
            }

            WCStudentBean createStatus = mUserService.createUserByMobile(mobile);
            if (createStatus == null || createStatus.getStudentId() == 0) {
                check = WCHttpStatus.FAIL_CUSTOM_DAILOG;
                check.msg = "未知错误";
                return WCResultData.getHttpStatusData(check, null);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("user_id", createStatus.getStudentId());
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

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
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
        if (student == null || student.getIsDel() == 1) {
            log.error(student == null ? "找不到该用户" : "该用户被删除");
            check = WCHttpStatus.FAIL_USER_UNKNOWK;
            check.msg = "用户名或密码错误，请重新输入";
            return WCResultData.getHttpStatusData(check, null);
        } else {
            check = mSecurityService.checkPasswordAvailable(student,  password);
            if (check != WCHttpStatus.SUCCESS) {
                log.error("用户密码输入错误");
                check = WCHttpStatus.FAIL_REQUEST;
                check.msg = "用户名或密码错误，请重新输入";
                return WCResultData.getHttpStatusData(check, null);
            } else {
                String token = mTokenService.createTokenByUserIdAndCaller(student.getStudentId(), student.getPassword(), caller);

                HashMap<String, Object> result = getUserInfoByStudent(student);
                result.put("token", token);

                return WCResultData.getSuccessData(result);
            }
        }
    }

    @RequestMapping(value = "/get_user_info", method = RequestMethod.POST)
    public WCResultData getUserInfo(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getUserInfo：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("getUserInfo：请求参数为空");
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getUserInfo：token失效");
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCRequestParamsUtil.getUserId(requestModel);
        WCStudentBean studentBean = mUserService.getUserInfoById(userId);

        if (studentBean == null || studentBean.getIsDel() == 1) {
            log.error("getUserInfo：用户不存在");
            check = WCHttpStatus.FAIL_USER_UNKNOWK;
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> result = getUserInfoByStudent(studentBean);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public WCResultData changePassword(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("changePassword：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("changePassword：请求参数为空");
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("changePassword：token失效");
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCRequestParamsUtil.getUserId(requestModel);
        String caller = WCRequestParamsUtil.getClientCaller(requestModel);
        String mobile = (String) requestData.get("mobile");
        String password = (String) requestData.get("password");
        String code = (String) requestData.get("code");

        SmsVerifyKit smsVerifyKit = new SmsVerifyKit(caller, mobile, code);
        boolean checkCodeSuccess = true;
        try {
            SmsVerifyKit.STATUS smsCheckStatus = smsVerifyKit.go();
            if (smsCheckStatus != SmsVerifyKit.STATUS.SUCCESS) {
                log.error("changePassword：验证码结果 ： " + smsCheckStatus.toString());
                checkCodeSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            checkCodeSuccess = false;
        }

        if (!checkCodeSuccess) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "验证码错误";
            return WCResultData.getHttpStatusData(check, null);
        }

        mUserService.changePassword(userId, password);

        return WCResultData.getSuccessData(null);
    }

    @RequestMapping(value = "/update_school_info", method = RequestMethod.POST)
    public WCResultData updateSchoolInfo(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("updateUserInfo：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("updateUserInfo：请求参数为空");
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("updateUserInfo：token失效");
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCRequestParamsUtil.getUserId(requestModel);
        if (requestData.containsKey("school_id")) {
            long schoolId = Long.parseLong((String) requestData.get("school_id"));
            mUserService.updateSchoolInfo(userId, schoolId);
        } else if (requestData.containsKey("major_id")) {
            long majorId = Long.parseLong((String) requestData.get("major_id"));
            mUserService.updateMajorInfo(userId, majorId);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        return WCResultData.getSuccessData(result);
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
        result.put("user_id", student.getStudentId());
        result.put("mobile", student.getMobile());
        result.put("nick_name", student.getNickName());
        result.put("real_name", student.getRealName());
        result.put("avatar_url", student.getAvatarUrl());
        result.put("gender", 1);
        result.put("class_name", student.getClassName());
        result.put("graduate_year", student.getGraduateYear() + "级");
        result.put("is_auth", student.getStatus() == WCStudentBean.STATUS.ALREADY_AUTH.status ? 1 : 0);

        result.put("school_id", student.getSchoolId());
        result.put("school_name", student.getSchoolBean() == null ? "" : student.getSchoolBean().getName());

        return result;
    }
}
