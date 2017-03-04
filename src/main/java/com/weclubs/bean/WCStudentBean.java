package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生实体类，对应数据库的 t_student 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentBean implements Serializable {

    private long id;
    private long schoolId;
    private String nickName;
    private String realName;
    private String password;
    private String avatarUrl;
    private String birthday;
    private String className;
    private int graduateYear;
    private int status;
    private int isDel;

    private WCSchoolBean schoolBean;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
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

    @Override
    public String toString() {
        return "WCStudentBean{" +
                "id=" + id +
                ", schoolId=" + schoolId +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", birthday='" + birthday + '\'' +
                ", className='" + className + '\'' +
                ", graduateYear=" + graduateYear +
                ", status=" + status +
                ", isDel=" + isDel +
                ", schoolBean=" + schoolBean +
                '}';
    }
}
