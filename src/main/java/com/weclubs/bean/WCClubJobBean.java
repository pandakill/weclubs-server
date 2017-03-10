package com.weclubs.bean;

import java.util.List;

/**
 * 社团职位实体，对应 t_club_job 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubJobBean {

    private long id;
    private String name;
    private int isSuggest;
    private int isDel;

    List<WCClubAuthorityBean> authorities;

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

    public int getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(int isSuggest) {
        this.isSuggest = isSuggest;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public List<WCClubAuthorityBean> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<WCClubAuthorityBean> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "WCClubJobBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isSuggest=" + isSuggest +
                ", isDel=" + isDel +
                ", authorities=" + (authorities == null ? "null" : authorities.toString()) +
                '}';
    }
}
