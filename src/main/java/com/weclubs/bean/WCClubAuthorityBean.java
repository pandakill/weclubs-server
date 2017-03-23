package com.weclubs.bean;

/**
 * 社团权限实体，对应 t_club_authority 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubAuthorityBean {

    private long clubAuthorityId;
    private String name;
    private String attribute;
    private int isDel;

    public long getClubAuthorityId() {
        return clubAuthorityId;
    }

    public void setClubAuthorityId(long clubAuthorityId) {
        this.clubAuthorityId = clubAuthorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WCClubAuthorityBean{" +
                "clubAuthorityId=" + clubAuthorityId +
                ", name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
