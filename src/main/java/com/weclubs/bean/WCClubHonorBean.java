package com.weclubs.bean;

import java.io.Serializable;

/**
 * 社团荣誉表，对应数据库 t_club_honor 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubHonorBean implements Serializable {

    private long honorId;
    private long clubId;
    private String content;
    private String getDate;
    private int isDel;

    private WCClubBean clubBean;

    public long getHonorId() {
        return honorId;
    }

    public void setHonorId(long honorId) {
        this.honorId = honorId;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
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
        return "WCClubHonorBean{" +
                "honorId=" + honorId +
                ", clubId=" + clubId +
                ", content='" + content + '\'' +
                ", getDate='" + getDate + '\'' +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                '}';
    }
}
