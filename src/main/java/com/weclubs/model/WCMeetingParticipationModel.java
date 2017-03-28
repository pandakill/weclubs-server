package com.weclubs.model;

import java.io.Serializable;

/**
 * 会议参与情况实体类
 *
 * Created by fangzanpan on 2017/3/28.
 */
public class WCMeetingParticipationModel implements Serializable{

    private long studentId;
    private String studentName;
    private String studentAvatar;

    private String departmentName;
    private String jobName;

    private int status;
    private int isLeader;
    private String comment;
    private String createDate;
    private String signDate;
    private int isSign;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentAvatar() {
        return studentAvatar;
    }

    public void setStudentAvatar(String studentAvatar) {
        this.studentAvatar = studentAvatar;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    @Override
    public String toString() {
        return "WCMeetingParticipationModel{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentAvatar='" + studentAvatar + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", status=" + status +
                ", isLeader=" + isLeader +
                ", comment='" + comment + '\'' +
                ", createDate='" + createDate + '\'' +
                ", signDate='" + signDate + '\'' +
                ", isSign=" + isSign +
                '}';
    }
}
