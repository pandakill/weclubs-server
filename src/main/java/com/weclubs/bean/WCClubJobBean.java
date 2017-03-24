package com.weclubs.bean;

import java.util.List;

/**
 * 社团职位实体，对应 t_club_job 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubJobBean {

    private long jobId;
    private String jobName;
    private int isSuggest;
    private int isDel;

    List<WCClubAuthorityBean> authorities;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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
                ", jobName='" + jobName + '\'' +
                ", isSuggest=" + isSuggest +
                ", isDel=" + isDel +
                ", authorities=" + authorities +
                '}';
    }
}
