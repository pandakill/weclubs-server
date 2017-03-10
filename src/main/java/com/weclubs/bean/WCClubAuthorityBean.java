package com.weclubs.bean;

/**
 * 社团权限实体，对应 t_club_authority 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubAuthorityBean {

    private long id;
    private String name;
    private String attribute;
    private int isDel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
