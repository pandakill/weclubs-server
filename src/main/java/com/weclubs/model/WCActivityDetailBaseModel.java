package com.weclubs.model;

import java.io.Serializable;

/**
 * 活动详情基本类
 *
 * Created by fangzanpan on 2017/3/29.
 */
public class WCActivityDetailBaseModel implements Serializable {

    private long activityId;
    private String activityName;
    private String attribution;
    private String posterUrl;
    private String address;
    private int allowApply;
    private int allowPreApply;

    private long applyDeadline;
    private long holdDate;
    private long holdDeadline;
    private long createDate;

    private int applyStop;

    private long commentCount;
    private long loveCount;
    private long favorCount;

    private long clubId;
    private String clubName;

    private int isDel;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public long getApplyDeadline() {
        return applyDeadline;
    }

    public void setApplyDeadline(long applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    public long getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(long holdDate) {
        this.holdDate = holdDate;
    }

    public long getHoldDeadline() {
        return holdDeadline;
    }

    public void setHoldDeadline(long holdDeadline) {
        this.holdDeadline = holdDeadline;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getApplyStop() {
        return applyStop;
    }

    public void setApplyStop(int applyStop) {
        this.applyStop = applyStop;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(long loveCount) {
        this.loveCount = loveCount;
    }

    public long getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(long favorCount) {
        this.favorCount = favorCount;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WCActivityDetailBaseModel{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", attribution='" + attribution + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", address='" + address + '\'' +
                ", allowApply=" + allowApply +
                ", allowPreApply=" + allowPreApply +
                ", applyDeadline=" + applyDeadline +
                ", holdDate=" + holdDate +
                ", holdDeadline=" + holdDeadline +
                ", createDate=" + createDate +
                ", applyStop=" + applyStop +
                ", commentCount=" + commentCount +
                ", loveCount=" + loveCount +
                ", favorCount=" + favorCount +
                ", clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
