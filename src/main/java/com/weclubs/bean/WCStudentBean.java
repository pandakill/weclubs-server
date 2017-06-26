package com.weclubs.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 学生实体类，对应数据库的 t_student 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentBean implements Serializable {

    public enum STATUS {

        NOT_AUTH(0),    // 未认证状态
        ALREADY_AUTH(1),    // 认证成功状态
        ALREADY_GRADUATE(2),    // 已经毕业状态
        FAIL_AUTH(3),   // 认证失败状态
        FROZEN(4)   // 账号冻结状态
        ;

        public int status;

        STATUS(int status) {
            this.status = status;
        }
    }

    private long studentId;
    private long schoolId;
    private String mobile;
    private String nickName;
    private String realName;
    private String password;
    private String avatarUrl;
    private int gender;
    private String birthday;
    private String qrcodeUrl;
    private String className;
    private int graduateYear;
    private int status;
    private int isDel;
    private String idCardNo;
    private String studentIdNo;
    private String stuCertificationFront;
    private String stuCertificationBg;

    private WCSchoolBean schoolBean;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCSchoolBean getSchoolBean() {
        return schoolBean;
    }

    public void setSchoolBean(WCSchoolBean schoolBean) {
        this.schoolBean = schoolBean;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getStudentIdNo() {
        return studentIdNo;
    }

    public void setStudentIdNo(String studentIdNo) {
        this.studentIdNo = studentIdNo;
    }

    public String getStuCertificationFront() {
        return stuCertificationFront;
    }

    public void setStuCertificationFront(String stuCertificationFront) {
        this.stuCertificationFront = stuCertificationFront;
    }

    public String getStuCertificationBg() {
        return stuCertificationBg;
    }

    public void setStuCertificationBg(String stuCertificationBg) {
        this.stuCertificationBg = stuCertificationBg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
