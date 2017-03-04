package com.weclubs.bean;

import java.io.Serializable;

/**
 * 社团实体类，对应数据库中的 t_club
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubBean implements Serializable {

    private long id;
    private long schoolId;
    private String name;
    private String introduction;
    private String avatarUrl;
    private int isAuth;
    private int status;
    private long checkCount;
    private int isDel;

    private WCSchoolBean schoolBean;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(int isAuth) {
        this.isAuth = isAuth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(long checkCount) {
        this.checkCount = checkCount;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCSchoolBean getSchoolBean() {
        return schoolBean;
    }

    public void setSchoolBean(WCSchoolBean schoolBean) {
        this.schoolBean = schoolBean;
    }

    @Override
    public String toString() {
        return "WCClubBean{" +
                "id=" + id +
                ", schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isAuth=" + isAuth +
                ", status=" + status +
                ", checkCount=" + checkCount +
                ", isDel=" + isDel +
                ", schoolBean=" + schoolBean +
                '}';
    }
}
