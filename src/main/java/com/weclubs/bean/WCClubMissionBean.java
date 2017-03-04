package com.weclubs.bean;

import java.io.Serializable;

/**
 * 通知\任务基本信息表，对应数据库 t_club_mission 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubMissionBean implements Serializable {

    private long id;
    private long clubId;
    private String attribution;
    private int type;
    private long parentId;
    private String address;
    private String deadline;
    private int isDel;

    private WCClubBean clubBean;
    private WCClubMissionBean parentMissionBean;

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

    @Override
    public String toString() {
        return "WCClubMissionBean{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", attribution='" + attribution + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", address='" + address + '\'' +
                ", deadline='" + deadline + '\'' +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                ", parentMissionBean=" + parentMissionBean +
                '}';
    }
}
