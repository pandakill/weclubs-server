package com.weclubs.bean;

import java.util.List;

/**
 * 社团职位实体，对应 t_club_job 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubJobBean {

    private long jobId;
    private String name;
    private int isSuggest;
    private int isDel;

    List<WCClubAuthorityBean> authorities;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
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
                "jobId=" + jobId +
                ", name='" + name + '\'' +
                ", isSuggest=" + isSuggest +
                ", isDel=" + isDel +
                ", authorities=" + authorities +
                '}';
    }
}
