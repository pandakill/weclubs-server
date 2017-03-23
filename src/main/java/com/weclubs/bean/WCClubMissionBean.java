package com.weclubs.bean;

import java.io.Serializable;

/**
 * 通知\任务基本信息表，对应数据库 t_club_mission 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubMissionBean implements Serializable {

    private long missionId;
    private long clubId;
    private String attribution;
    private int type;
    private long parentId;
    private String address;
    private String deadline;
    private long sponsorId;
    private String createDate;
    private int isDel;

    private WCClubBean clubBean;
    private WCClubMissionBean parentMissionBean;

    private WCStudentBean sponsorStudentBean;

    public long getMissionId() {
        return missionId;
    }

    public void setMissionId(long missionId) {
        this.missionId = missionId;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCClubBean getClubBean() {
        return clubBean;
    }

    public void setClubBean(WCClubBean clubBean) {
        this.clubBean = clubBean;
    }

    public WCClubMissionBean getParentMissionBean() {
        return parentMissionBean;
    }

    public void setParentMissionBean(WCClubMissionBean parentMissionBean) {
        this.parentMissionBean = parentMissionBean;
    }

    public WCStudentBean getSponsorStudentBean() {
        return sponsorStudentBean;
    }

    public void setSponsorStudentBean(WCStudentBean sponsorStudentBean) {
        this.sponsorStudentBean = sponsorStudentBean;
    }

    @Override
    public String toString() {
        return "WCClubMissionBean{" +
                "missionId=" + missionId +
                ", clubId=" + clubId +
                ", attribution='" + attribution + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", address='" + address + '\'' +
                ", deadline='" + deadline + '\'' +
                ", sponsorId=" + sponsorId +
                ", createDate='" + createDate + '\'' +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                ", parentMissionBean=" + parentMissionBean +
                ", sponsorStudentBean=" + sponsorStudentBean +
                '}';
    }
}
