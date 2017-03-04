package com.weclubs.bean;

import java.io.Serializable;

/**
 * 活动基本信息表，对应 t_club_activity 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubActivityBean implements Serializable {

    private long id;
    private long clubId;
    private String name;
    private String address;
    private String posterUrl;
    private String applyDeadline;
    private String holdDeadline;
    private int allowApply;
    private int allowPreApply;
    private String recordComment;
    private String recordPicUrl;
    private int checkCount;
    private int isDel;

    private WCClubBean clubBean;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getApplyDeadline() {
        return applyDeadline;
    }

    public void setApplyDeadline(String applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    public String getHoldDeadline() {
        return holdDeadline;
    }

    public void setHoldDeadline(String holdDeadline) {
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
                "id=" + id +
                ", clubId=" + clubId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", applyDeadline='" + applyDeadline + '\'' +
                ", holdDeadline='" + holdDeadline + '\'' +
                ", allowApply=" + allowApply +
                ", allowPreApply=" + allowPreApply +
                ", recordComment='" + recordComment + '\'' +
                ", recordPicUrl='" + recordPicUrl + '\'' +
                ", checkCount=" + checkCount +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                '}';
    }
}
