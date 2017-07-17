package com.weclubs.application.user;

import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCSchoolBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.mapper.WCStudentMapper;
import com.weclubs.model.WCStudentBaseInfoModel;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRegexUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 用户的service实现类
 *
 * Created by fangzanpan on 2017/3/21.
 */
@Service("userService")
public class WCUserSeriviceImpl implements WCIUserService {

    private Logger log = Logger.getLogger(WCUserSeriviceImpl.class);

    @Autowired
    private WCStudentMapper mStudentMapper;
    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCISchoolService mSchoolService;

    public WCStudentBean getUserInfoById(long userId) {

        if (userId <= 0) {
            log.error("getUserInfoById：userId不能小于等于0");
            return null;
        }

        return mStudentMapper.getStudentById(userId);
    }

    public WCStudentBean getUserInfoByMobile(String mobile) {

        if (StringUtils.isEmpty(mobile)) {
            log.error("getUserInfoByMobile：mobile不能为空");
            return null;
        }

        return mStudentMapper.getStudentByMobile(mobile);
    }

    public int checkMobileExist(String mobile) {

        int exitStatus = 404;

        if (StringUtils.isEmpty(mobile)) {
            log.error("checkMobileExist：mobile不能为空");
            exitStatus = -1;
            return exitStatus;
        }

        WCStudentBean studentBean = mStudentMapper.getStudentByMobile(mobile);

        if (studentBean != null) {
            exitStatus = studentBean.getIsDel();
        }

        return exitStatus;
    }

    public WCStudentBean createUserByMobile(String mobile) {

        if (StringUtils.isEmpty(mobile)) {
            log.error("createUserByMobile：mobile不能为空");
            return null;
        }

        WCStudentBean studentBean = getUserInfoByMobile(mobile);
        if (studentBean != null) {
            log.error("createUserByMobile：已经存在该用户" + mobile);
            return null;
        }

        studentBean = new WCStudentBean();
        studentBean.setMobile(mobile);

        mStudentMapper.createStudent(studentBean);

        log.info("创建完之后studentBean = " + studentBean.toString());
        return studentBean;
    }

    public WCStudentBean createUserByMobileAndPsw(String mobile, String password) {

        if (StringUtils.isEmpty(mobile)) {
            log.error("createUserByMobileAndPsw：mobile不能为空");
            return null;
        }

        if (StringUtils.isEmpty(password)) {
            log.error("createUserByMobileAndPsw：password不能为空");
            return null;
        }

        WCStudentBean studentBean = new WCStudentBean();
        studentBean.setMobile(mobile);

        mStudentMapper.createStudent(studentBean);

        changePassword(studentBean.getStudentId(), password);


        log.info("创建完之后studentBean = " + studentBean.toString());
        return studentBean;
    }

    public String changePassword(long userId, String password) {

        if (userId <= 0) {
            log.error("changePassword：userId不能小于等于0");
            return null;
        }

        if (StringUtils.isEmpty(password)) {
            log.error("changePassword：密码不能为空");
            return null;
        }

        String encodePsw = mSecurityService.encodePassword(userId, password);

        mStudentMapper.updateStudentPassword(userId, encodePsw);
        return encodePsw;
    }

    public void updateSchoolInfo(long userId, long schoolId) {

        if (userId <= 0) {
            log.error("updateSchoolInfo：userId不能小于等于0");
            return;
        }

        WCStudentBean studentBean = mStudentMapper.getStudentById(userId);
        if (studentBean == null) {
            log.error("updateSchoolInfo：找不到userId = " + userId + " 的学生");
            return;
        }

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(schoolId);
        if (schoolBean == null || schoolBean.getParentId() > 0) {
            log.error("updateSchoolInfo：找不到school或者schoolId不是学校");
            return;
        }

        studentBean.setSchoolId(schoolId);

        mStudentMapper.updateStudent(studentBean);
    }

    public void updateMajorInfo(long userId, long majorId) {


        if (userId <= 0) {
            log.error("updateMajorInfo：userId不能小于等于0");
            return;
        }

        WCStudentBean studentBean = mStudentMapper.getStudentById(userId);
        if (studentBean == null) {
            log.error("updateMajorInfo：找不到userId = " + userId + " 的学生");
            return;
        }

        WCSchoolBean majorBean = mSchoolService.getSchoolById(majorId);
        if (majorBean == null || majorBean.getParentId() <= 0) {
            log.error("updateMajorInfo：找不到major或者majorId不是院系");
            return;
        }

        majorBean.setSchoolId(majorId);

        mStudentMapper.updateStudent(studentBean);
    }

    public WCStudentBaseInfoModel getUserBaseInfo(long userId) {

        WCStudentBaseInfoModel studentBaseInfoModel = new WCStudentBaseInfoModel();

        WCStudentBean studentBean = getUserInfoById(userId);

        studentBaseInfoModel.setStudentId(studentBean.getStudentId());
        studentBaseInfoModel.setRealName(studentBean.getRealName());
        studentBaseInfoModel.setNickName(studentBean.getNickName());
        studentBaseInfoModel.setClassName(studentBean.getClassName());
        studentBaseInfoModel.setAvatarUrl(studentBean.getAvatarUrl());
        studentBaseInfoModel.setGraduate(studentBean.getGraduateYear());
        studentBaseInfoModel.setMobile(studentBean.getMobile());

        if (studentBean.getSchoolId() != 0) {
            WCSchoolBean collegeBean = mSchoolService.getCollegeById(studentBean.getSchoolId());
            if (collegeBean == null) {
                collegeBean = mSchoolService.getSchoolById(studentBean.getSchoolId());

                studentBaseInfoModel.setSchoolId(collegeBean.getSchoolId());
                studentBaseInfoModel.setSchoolName(collegeBean.getName());
            } else {
                WCSchoolBean schoolBean = mSchoolService.getSchoolById(collegeBean.getParentId());

                studentBaseInfoModel.setSchoolName(schoolBean.getName());
                studentBaseInfoModel.setSchoolId(schoolBean.getSchoolId());

                log.debug("getUserBaseInfo：schoolBean.getId = " + schoolBean.getSchoolId()
                        + ";college.getParentId = " + collegeBean.getParentId());
                studentBaseInfoModel.setCollegeId(collegeBean.getSchoolId());
                studentBaseInfoModel.setCollegeName(collegeBean.getName());
            }
        }

        return studentBaseInfoModel;
    }

    @Override
    public WCHttpStatus initUserInfo(long userId, String nickName, long schoolId, long majorId, int gender,
                                     String className, String avatarUrl, int graduateYear) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;
        WCStudentBean studentBean = getUserInfoById(userId);

        if (studentBean == null) {
            check = WCHttpStatus.FAIL_USER_UNKNOWK;
            return check;
        }

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(schoolId);
        WCSchoolBean majorBean = mSchoolService.getCollegeById(majorId);
        if (schoolBean == null) {
            check.msg = "找不到该学校，请检查后重试！";
            return check;
        }

        if (majorBean == null) {
            check.msg = "找不到该院系，请检查后重试！";
            return check;
        }

        if (majorBean.getParentId() != schoolBean.getSchoolId()) {
            check.msg = "学校和院系不匹配，请检查后重试！";
            return check;
        }

        if (StringUtils.isEmpty(avatarUrl)) {
            check.msg = "头像不能为空，请上传头像后再重试！";
            return check;
        }

        if (StringUtils.isEmpty(className)) {
            check.msg = "专业不能为空，请完善专业！";
            return check;
        }

        if (graduateYear > WCCommonUtil.getCurrentYear()) {
            log.warn("currentYear = " + WCCommonUtil.getCurrentYear());
            check.msg = "入学年份不能超过今年！";
            return check;
        }

        studentBean.setNickName(nickName);
        studentBean.setSchoolId(majorId);
        studentBean.setGender(gender);
        studentBean.setClassName(className);
        studentBean.setAvatarUrl(avatarUrl);
        studentBean.setGraduateYear(graduateYear);

        mStudentMapper.updateStudent(studentBean);

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    @Override
    public WCHttpStatus changeMobile(long userId, String mobile, String code) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (!WCRegexUtils.isMobileSimple(mobile)) {
            check.msg = "输入的手机号码不正确，请重新输入！";
            return check;
        }

        if (StringUtils.isEmpty(code)) {
            check.msg = "验证码不能为空，请重新输入！";
            return check;
        }

        WCStudentBean user = getUserInfoById(userId);
        if (user == null) {
            check = WCHttpStatus.FAIL_USER_UNKNOWK;
            return check;
        }

        user.setMobile(mobile);
        mStudentMapper.updateStudent(user);

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    @Override
    public HashMap<String, Object> getUserCertificationInfo(long userId) {
        HashMap<String, Object> result = new HashMap<>();

        WCStudentBean userBean = getUserInfoById(userId);
        if (userBean == null) {
            result.put("error_code", WCHttpStatus.FAIL_USER_UNKNOWK.code);
            result.put("error_msg", WCHttpStatus.FAIL_USER_UNKNOWK.msg);
            return result;
        }

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(userBean.getSchoolId());
        WCSchoolBean collegeBean = mSchoolService.getCollegeById(userBean.getSchoolId());

        result.put("real_name", userBean.getRealName());
        result.put("graduate_year", userBean.getGraduateYear());
        result.put("class_name", userBean.getClassName());
        result.put("id_card", userBean.getIdCardNo());
        result.put("school_id", schoolBean.getSchoolId());
        result.put("school_name", schoolBean.getName());
        result.put("major_id", collegeBean.getSchoolId());
        result.put("major_name", collegeBean.getName());
        result.put("certification_front", userBean.getStuCertificationFront());
        result.put("certification_bg", userBean.getStuCertificationBg());
        result.put("student_card_id", userBean.getStudentIdNo());

        //学生状态，0未认证，1已认证，2认证中，3认证失败
        result.put("certify_status", userBean.getStatus());

        return result;
    }

    @Override
    public WCHttpStatus setCertificationInfo(HashMap<String, Object> certificationInfo) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        long userId = WCCommonUtil.getLongData(certificationInfo.get("user_id"));

        WCStudentBean userBean = getUserInfoById(userId);
        if (userBean == null) {
            check = WCHttpStatus.FAIL_USER_UNKNOWK;
            return check;
        }

        String certificationFront = String.valueOf(certificationInfo.get("certification_front"));
        String certificationBg = String.valueOf(certificationInfo.get("certification_bg"));

        if (StringUtils.isEmpty(certificationFront) || StringUtils.isEmpty(certificationBg)) {
            check.msg = "学生证照片不能为空！";
            return check;
        }

        String realName = String.valueOf(certificationInfo.get("real_name"));
        if (StringUtils.isEmpty(realName)) {
            check.msg = "真实姓名不能为空！";
            return check;
        }

        String stuCardId = String.valueOf(certificationInfo.get("student_card_id"));
        if (StringUtils.isEmpty(stuCardId)) {
            check.msg = "学号不能为空！";
            return check;
        }

        int graduateYear = WCCommonUtil.getIntegerData(certificationInfo.get("graduate_year"));
        if (graduateYear <= 0) {
            check.msg = "入学年份不能为空！";
            return check;
        }

        long schoolId = WCCommonUtil.getLongData(certificationInfo.get("school_id"));
        WCSchoolBean schoolBean = mSchoolService.getSchoolById(schoolId);
        if (schoolBean == null) {
            check.msg = "找不到该学校！";
            return check;
        }

        long majorId = WCCommonUtil.getLongData(certificationInfo.get("major_id"));
        WCSchoolBean majorBean = mSchoolService.getCollegeById(majorId);
        if (majorBean == null) {
            check.msg = "找不到该院系！";
            return check;
        }

        String className = String.valueOf(certificationInfo.get("class_name"));
        if (StringUtils.isEmpty(className)) {
            check.msg = "专业不能为空";
            return check;
        }

        String idCardNo = String.valueOf(certificationInfo.get("id_card_no"));
        if (StringUtils.isEmpty(idCardNo)
                || !WCRegexUtils.isIDCard18(idCardNo)) {
            check.msg = "身份证号码错误";
            return check;
        }

        userBean.setRealName(realName);
        userBean.setStuCertificationFront(certificationFront);
        userBean.setStuCertificationBg(certificationBg);
        userBean.setStudentIdNo(stuCardId);
        userBean.setSchoolId(majorId);
        userBean.setClassName(className);
        userBean.setIdCardNo(idCardNo);
        userBean.setGraduateYear(graduateYear);

        mStudentMapper.updateStudent(userBean);

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    @Override
    public WCHttpStatus updateUserInfo(WCStudentBean userInfo) {
        if (userInfo == null) {
            return WCHttpStatus.FAIL_USER_UNKNOWK;
        }

        mStudentMapper.updateStudent(userInfo);
        return WCHttpStatus.SUCCESS;
    }
}
