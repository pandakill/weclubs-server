package com.weclubs.api;

import com.weclubs.application.mob_sms.SmsVerifyKit;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.application.token.WCITokenService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCStudentBean;
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
        String password = (String) requestData.get("password");

        if (StringUtils.isEmpty(mobile)) {
            log.error("register：请求参数中的mobile参数为空");
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }

        if (StringUtils.isEmpty(password)) {
            log.error("register：请求参数中的password参数为空");
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

            WCStudentBean createStatus = mUserService.createUserByMobileAndPsw(mobile, password);
            if (createStatus == null || createStatus.getStudentId() == 0) {
                log.error(createStatus == null ? "student == null：" : ("createStatus.getStudentId = " + createStatus.getStudentId()));
                check = WCHttpStatus.FAIL_CUSTOM_DAILOG;
                check.msg = "未知错误";
                return WCResultData.getHttpStatusData(check, null);
            }

            String caller = WCRequestParamsUtil.getClientCaller(requestModel);
            String token = mTokenService.createTokenByUserIdAndCaller(createStatus.getStudentId(), createStatus.getPassword(), caller);

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("user_id", createStatus.getStudentId());
            result.put("mobile", createStatus.getMobile());
            result.put("token", token);
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
            if (StringUtils.isEmpty(student.getPassword())) {
                check = WCHttpStatus.FAIL_CUSTOM_DAILOG_AND_CLOSE;
                check.msg = "您还未设置密码";
                return WCResultData.getHttpStatusData(check, null);
            }
            check = mSecurityService.checkPasswordAvailable(student,  password);
            if (check != WCHttpStatus.SUCCESS) {
                log.error("用户密码输入错误");
                check = WCHttpStatus.FAIL_USER_PSW_UNVALID;
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

        long userId = 0;
        if (requestData.containsKey("token")) {
            check = mSecurityService.checkTokenAvailable(requestModel);
            if (check != WCHttpStatus.SUCCESS) {
                log.error("changePassword：token失效");
                return WCResultData.getHttpStatusData(check, null);
            }
            userId = WCRequestParamsUtil.getUserId(requestModel);
        }

        String caller = WCRequestParamsUtil.getClientCaller(requestModel);
        String password = (String) requestData.get("password");

        if (requestData.containsKey("code")) {
            String mobile = (String) requestData.get("mobile");
            String code = (String) requestData.get("code");

            WCStudentBean studentBean = mUserService.getUserInfoByMobile(mobile);
            if (studentBean == null) {
                check = WCHttpStatus.FAIL_REQUEST;
                return WCResultData.getHttpStatusData(check, null);
            }
            userId = studentBean.getStudentId();

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
        }

        String realPsw = mUserService.changePassword(userId, password);

        String token = mTokenService.createTokenByUserIdAndCaller(userId, realPsw, caller);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user_id", userId);
        result.put("token", token);

        return WCResultData.getSuccessData(result);
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

    @RequestMapping(value = "/init_user_info", method = RequestMethod.POST)
    public WCResultData initUserInfo(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("initUserInfo：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("initUserInfo：请求参数为空");
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("initUserInfo：token失效");
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCRequestParamsUtil.getUserId(requestModel);
        long schoolId = WCCommonUtil.getLongData(requestData.get("school_id"));
        long majorId = WCCommonUtil.getLongData(requestData.get("major_id"));
        int gender = WCCommonUtil.getIntegerData(requestData.get("gender"));
        String nickName = String.valueOf(requestData.get("nick_name"));
        String className = String.valueOf(requestData.get("class_name"));
        String avatarUrl = String.valueOf(requestData.get("avatar_url"));
        int graduateYear = WCCommonUtil.getIntegerData(requestData.get("graduate_year"));

        check = mUserService.initUserInfo(userId, nickName, schoolId, majorId, gender, className, avatarUrl, graduateYear);

        return WCResultData.getHttpStatusData(check, new HashMap<>());
    }

    @RequestMapping(value = "/change_mobile", method = RequestMethod.POST)
    public WCResultData changeMobile(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("changeMobile：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("changeMobile：请求参数为空");
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("changeMobile：token失效");
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCRequestParamsUtil.getUserId(requestModel);
        String mobile = String.valueOf(requestData.get("mobile"));
        String code = String.valueOf(requestData.get("code"));

        check = mUserService.changeMobile(userId, mobile, code);

        return WCResultData.getHttpStatusData(check, new HashMap<>());
    }

    @RequestMapping(value = "/get_certification_info", method = RequestMethod.POST)
    public WCResultData getCertificationInfo(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getCertificationInfo：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            log.error("getCertificationInfo：请求参数为空");
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getCertificationInfo：token失效");
            return WCResultData.getHttpStatusData(check, null);
        }

        long userId = WCRequestParamsUtil.getUserId(requestModel);

        HashMap<String, Object> result = mUserService.getUserCertificationInfo(userId);

        if (result.containsKey("error_code")) {
            check.code = (int) result.get("error_code");
            check.msg = (String) result.get("error_msg");
            return WCResultData.getHttpStatusData(check, new HashMap<>());
        }

        return WCResultData.getSuccessData(result);
    }

//    @RequestMapping(value = "/setup_password", method = RequestMethod.POST)
    public WCResultData setPassword(@RequestBody WCRequestModel requestModel) {

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

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        String password = (String) requestData.get("password");

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean != null && !StringUtils.isEmpty(studentBean.getPassword())) {
            check = WCHttpStatus.FAIL_USER_SETUP_PASSWORD_UNVAILID;
            return WCResultData.getHttpStatusData(check, null);
        }

        String realPsw = mUserService.changePassword(studentId, password);
        if (StringUtils.isEmpty(realPsw)) {
            check = WCHttpStatus.FAIL_USER_PSW_UNVALID;
            return WCResultData.getHttpStatusData(check, null);
        }

        String caller = WCRequestParamsUtil.getClientCaller(requestModel);
        String token = mTokenService.createTokenByUserIdAndCaller(studentId, realPsw, caller);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user_id", studentId);
        result.put("token", token);
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
