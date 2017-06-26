package com.weclubs.application.user;

import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCStudentBaseInfoModel;
import com.weclubs.util.WCHttpStatus;

import java.util.HashMap;

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

    /**
     * 用户初始化资料
     *
     * @param userId    用户ID
     * @param nickName  用户昵称
     * @param schoolId  学校ID
     * @param majorId   院系ID
     * @param gender    性别
     * @param className 专业名称
     * @param avatarUrl 用户头像地址
     * @param graduateYear 用户入学年份
     *
     * @return  如果保存成功，则返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus initUserInfo(long userId, String nickName, long schoolId, long majorId,
                              int gender, String className, String avatarUrl, int graduateYear);

    /**
     * 修改绑定手机号码
     *
     * @param userId    用户ID
     * @param mobile    用户昵称
     * @param code      手机验证码
     *
     * @return      如果修改成功，则返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus changeMobile(long userId, String mobile, String code);

    /**
     * 获取用户的认证信息
     *
     * @param userId    用户id
     *
     * @return  如果获取成功，则返回信息键值对；否则返回带有"error_code"的键值对
     */
    HashMap<String, Object> getUserCertificationInfo(long userId);

    /**
     * 设置认证信息
     *
     * @param certificationInfo 认证信息键值对
     * @return  如果设置成功，则返回{@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus setCertificationInfo(HashMap<String, Object> certificationInfo);
}
