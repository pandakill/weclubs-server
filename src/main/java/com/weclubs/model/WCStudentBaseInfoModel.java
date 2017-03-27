package com.weclubs.model;

import java.io.Serializable;

/**
 * 学生基本信息类
 *
 * Created by fangzanpan on 2017/3/27.
 */
public class WCStudentBaseInfoModel implements Serializable {

    private long studentId;
    private String realName;
    private String nickName;

    private int graduate;

    private long schoolId;
    private String schoolName;

    private long collegeId;
    private String collegeName;

    private String className;
    private String majorName;

    private String avatarUrl;
    private String mobile;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGraduate() {
        return graduate;
    }

    public void setGraduate(int gradeate) {
        this.graduate = gradeate;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "WCStudentBaseInfoModel{" +
                "studentId=" + studentId +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", graduate=" + graduate +
                ", schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", collegeId=" + collegeId +
                ", collegeName='" + collegeName + '\'' +
                ", className='" + className + '\'' +
                ", majorName='" + majorName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
