package com.weclubs.application.user;

import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCStudentBaseInfoModel;

/**
 * 用户service接口层
 *
 * Created by fangzanpan on 2017/3/21.
 */
public interface WCIUserService {

    WCStudentBean getUserInfoById(long userId);

    WCStudentBean getUserInfoByMobile(String mobile);

    int checkMobileExist(String mobile);

    WCStudentBean createUserByMobile(String mobile);

    WCStudentBean createUserByMobileAndPsw(String mobile, String password);

    String changePassword(long userId, String password);

    void updateSchoolInfo(long userId, long schoolId);

    void updateMajorInfo(long userId, long majorId);

    WCStudentBaseInfoModel getUserBaseInfo(long userId);
}
