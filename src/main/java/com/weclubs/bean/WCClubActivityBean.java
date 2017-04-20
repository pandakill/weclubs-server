package com.weclubs.bean;

import java.io.Serializable;

/**
 * 活动基本信息表，对应 t_club_activity 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubActivityBean implements Serializable {

    public static int TYPE_SCHOOL_PUBLIC = 1;   //  校级公开活动
    public static int TYPE_CLUB_PRIVATE = 2;    //  组织内部私有活动

    private long clubActivityId;
    private long clubId;
    private String name;
    private String attribution;
    private int activityType;
    private int needSign;
    private String address;
    private String posterUrl;
    private long holdDate;
    private long holdDeadline;
    private int allowApply;
    private int allowPreApply;
    private long applyDeadline;
    private String recordComment;
    private String recordPicUrl;
    private int checkCount;
    private long sponsorId;
    private long createDate;
    private int isDel;

    private WCClubBean clubBean;

    public long getClubActivityId() {
        return clubActivityId;
    }

    public void setClubActivityId(long clubActivityId) {
        this.clubActivityId = clubActivityId;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getNeedSign() {
        return needSign;
    }

    public void setNeedSign(int needSign) {
        this.needSign = needSign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public long getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(long holdDate) {
        this.holdDate = holdDate;
    }

    public long getApplyDeadline() {
        return applyDeadline;
    }

    public void setApplyDeadline(long applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    public long getHoldDeadline() {
        return holdDeadline;
    }

    public void setHoldDeadline(long holdDeadline) {
        this.holdDeadline = holdDeadline;
    }

    public int getAllowApply() {
        return allowApply;
    }

    public void setAllowApply(int allowApply) {
        this.allowApply = allowApply;
    }

    public int getAllowPreApply() {
        return allowPreApply;
    }

    public void setAllowPreApply(int allowPreApply) {
        this.allowPreApply = allowPreApply;
    }

    public String getRecordComment() {
        return recordComment;
    }

    public void setRecordComment(String recordComment) {
        this.recordComment = recordComment;
    }

    public String getRecordPicUrl() {
        return recordPicUrl;
    }

    public void setRecordPicUrl(String recordPicUrl) {
        this.recordPicUrl = recordPicUrl;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
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

    @Override
    public String toString() {
        return "WCClubActivityBean{" +
                "clubActivityId=" + clubActivityId +
                ", clubId=" + clubId +
                ", name='" + name + '\'' +
                ", attribution='" + attribution + '\'' +
                ", activityType=" + activityType +
                ", needSign=" + needSign +
                ", address='" + address + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", holdDate=" + holdDate +
                ", holdDeadline=" + holdDeadline +
                ", allowApply=" + allowApply +
                ", allowPreApply=" + allowPreApply +
                ", applyDeadline=" + applyDeadline +
                ", recordComment='" + recordComment + '\'' +
                ", recordPicUrl='" + recordPicUrl + '\'' +
                ", checkCount=" + checkCount +
                ", sponsorId=" + sponsorId +
                ", createDate=" + createDate +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                '}';
    }
}
