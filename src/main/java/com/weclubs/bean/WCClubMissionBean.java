package com.weclubs.bean;

import com.weclubs.model.WCMissionBaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 通知\任务基本信息表，对应数据库 t_club_mission 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubMissionBean implements Serializable {

    public static int TYPE_NOTIFY = 0;  // 通知类型
    public static int TYPE_MISSION = 1; // 任务类型
    public static int TYPE_MEETING = 2; // 会议类型

    private long missionId;
    private long clubId;
    private String attribution;
    private long graduateId;
    private int type;
    private int signType;
    private long parentId;
    private String address;
    private long deadline;
    private long sponsorId;
    private long createDate;
    private int isDel;

    private WCClubBean clubBean;
    private WCClubMissionBean parentMissionBean;

    private WCStudentBean sponsorStudentBean;

    private List<WCClubMissionBean> childMissions;
    private List<WCMissionBaseModel> childMissonDetails;

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

    public long getGraduateId() {
        return graduateId;
    }

    public void setGraduateId(long graduateId) {
        this.graduateId = graduateId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
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

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
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

    public List<WCMissionBaseModel> getChildMissonDetails() {
        return childMissonDetails;
    }

    public void setChildMissonDetails(List<WCMissionBaseModel> childMissonDetails) {
        this.childMissonDetails = childMissonDetails;
    }

    public List<WCClubMissionBean> getChildMissions() {
        return childMissions;
    }

    public void setChildMissions(List<WCClubMissionBean> childMissions) {
        this.childMissions = childMissions;
    }

    @Override
    public String toString() {
        return "WCClubMissionBean{" +
                "missionId=" + missionId +
                ", clubId=" + clubId +
                ", attribution='" + attribution + '\'' +
                ", graduateId=" + graduateId +
                ", type=" + type +
                ", signType=" + signType +
                ", parentId=" + parentId +
                ", address='" + address + '\'' +
                ", deadline=" + deadline +
                ", sponsorId=" + sponsorId +
                ", createDate=" + createDate +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                ", parentMissionBean=" + parentMissionBean +
                ", sponsorStudentBean=" + sponsorStudentBean +
                ", childMissions=" + childMissions +
                ", childMissonDetails=" + childMissonDetails +
                '}';
    }
}
