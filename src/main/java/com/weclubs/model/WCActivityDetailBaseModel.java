package com.weclubs.model;

import com.weclubs.util.WCCommonUtil;

import java.io.Serializable;
import java.util.HashMap;

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
    private int activityType;

    private long applyDeadline;
    private long holdDate;
    private long holdDeadline;
    private long createDate;

    private long commentCount;
    private long loveCount;
    private long favorCount;

    private long clubId;
    private String clubName;
    private String clubAvatar;

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

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
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

    public String getClubAvatar() {
        return clubAvatar;
    }

    public void setClubAvatar(String clubAvatar) {
        this.clubAvatar = clubAvatar;
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
                ", activityType=" + activityType +
                ", applyDeadline=" + applyDeadline +
                ", holdDate=" + holdDate +
                ", holdDeadline=" + holdDeadline +
                ", createDate=" + createDate +
                ", commentCount=" + commentCount +
                ", loveCount=" + loveCount +
                ", favorCount=" + favorCount +
                ", clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", clubAvatar='" + clubAvatar + '\'' +
                ", isDel=" + isDel +
                '}';
    }

    public HashMap<String, Object> getClubDetailBaseInfo() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("club_id", getClubId());
        result.put("club_name", getClubName());
        result.put("club_avatar", getClubAvatar());

        result.put("activity_id", getActivityId());
        result.put("activity_name", getActivityName());
        result.put("attribution", getAttribution());
        result.put("apply_deadline", getApplyDeadline());
        result.put("hold_date", getHoldDate());
        result.put("hold_deadline", getHoldDeadline());
        result.put("allow_apply", getAllowApply());
        result.put("allow_pre_apply", getAllowPreApply());

        result.put("stop_apply", WCCommonUtil.isExpire(getApplyDeadline()) ? 1 : 0);
        result.put("stop_activity", WCCommonUtil.isExpire(getHoldDeadline()) ? 1 : 0);

        result.put("comment_count", getCommentCount());
        result.put("love_count", getLoveCount());
        result.put("favor_count", getFavorCount());

        return result;
    }
}
