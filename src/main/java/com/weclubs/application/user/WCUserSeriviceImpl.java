package com.weclubs.application.user;

import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCSchoolBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.mapper.WCStudentMapper;
import com.weclubs.model.WCStudentBaseInfoModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        WCStudentBean studentBean = new WCStudentBean();
        studentBean.setMobile(mobile);

        mStudentMapper.createStudent(studentBean);

        log.info("创建完之后studentBean = " + studentBean.toString());
        return studentBean;
    }

    public void changePassword(long userId, String password) {

        if (userId <= 0) {
            log.error("changePassword：userId不能小于等于0");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            log.error("changePassword：密码不能为空");
            return;
        }

        String encodePsw = mSecurityService.encodePassword(userId, password);

        mStudentMapper.updateStudentPaasword(userId, encodePsw);
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
            WCSchoolBean collegeBean = mSchoolService.getMajorById(studentBean.getSchoolId());
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
}
