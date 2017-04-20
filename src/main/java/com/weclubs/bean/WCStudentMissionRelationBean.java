package com.weclubs.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 学生、任务\通知关系表，对应数据库 t_student_mission_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentMissionRelationBean implements Serializable {

    private long stuMisRelId;
    private long studentId;
    private long missionId;
    private int isLeader;
    private int status;
    private int isSign;
    private String comment;
    private long createDate;
    private long signDate;
    private int isDel;

    private WCStudentBean studentBean;
    private WCClubMissionBean clubMissionBean;

    private long childMissionCount;

    public long getStuMisRelId() {
        return stuMisRelId;
    }

    public void setStuMisRelId(long stuMisRelId) {
        this.stuMisRelId = stuMisRelId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getMissionId() {
        return missionId;
    }

    public void setMissionId(long missionId) {
        this.missionId = missionId;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getSignDate() {
        return signDate;
    }

    public void setSignDate(long signDate) {
        this.signDate = signDate;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCStudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(WCStudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public WCClubMissionBean getClubMissionBean() {
        return clubMissionBean;
    }

    public void setClubMissionBean(WCClubMissionBean clubMissionBean) {
        this.clubMissionBean = clubMissionBean;
    }

    public long getChildMissionCount() {
        return childMissionCount;
    }

    public void setChildMissionCount(long childMissionCount) {
        this.childMissionCount = childMissionCount;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
