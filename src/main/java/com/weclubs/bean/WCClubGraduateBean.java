package com.weclubs.bean;

import java.io.Serializable;

/**
 * 社团届数表信息，对应 t_club_graduate 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubGraduateBean implements Serializable {

    private long clubGraduateId;
    private long clubId;
    private int graduateCount;
    private String graduateName;
    private String departments;
    private String jobs;
    private int isCurrent;

    private WCClubBean clubBean;

    public long getClubGraduateId() {
        return clubGraduateId;
    }

    public void setClubGraduateId(long clubGraduateId) {
        this.clubGraduateId = clubGraduateId;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public int getGraduateCount() {
        return graduateCount;
    }

    public void setGraduateCount(int graduateCount) {
        this.graduateCount = graduateCount;
    }

    public String getGraduateName() {
        return graduateName;
    }

    public void setGraduateName(String graduateName) {
        this.graduateName = graduateName;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public int getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(int isCurrent) {
        this.isCurrent = isCurrent;
    }

    public WCClubBean getClubBean() {
        return clubBean;
    }

    public void setClubBean(WCClubBean clubBean) {
        this.clubBean = clubBean;
    }

    @Override
    public String toString() {
        return "WCClubGraduateBean{" +
                "clubGraduateId=" + clubGraduateId +
                ", clubId=" + clubId +
                ", graduateCount=" + graduateCount +
                ", graduateName='" + graduateName + '\'' +
                ", departments='" + departments + '\'' +
                ", jobs='" + jobs + '\'' +
                ", isCurrent=" + isCurrent +
                ", clubBean=" + clubBean +
                '}';
    }

}
