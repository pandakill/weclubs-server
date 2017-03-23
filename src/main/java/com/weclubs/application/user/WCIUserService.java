package com.weclubs.application.user;

import com.weclubs.bean.WCStudentBean;

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

    void changePassword(long userId, String password);

    void updateSchoolInfo(long userId, long schoolId);
}
