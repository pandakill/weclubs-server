package com.weclubs.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 管理社团的实体类
 *
 * Created by fangzanpan on 2017/4/5.
 */
public class WCManageClubModel implements Serializable {

    private long clubId;
    private String clubName;
    private String avatarUrl;
    private int level;
    private String introduction;
    private String slogan;

    private String departments;
    private String jobs;

    private long memberCount;
    private long honorCount;

    private long myDepartment;
    private long myJob;

    private JSONObject jobAuthority;

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
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

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public long getHonorCount() {
        return honorCount;
    }

    public void setHonorCount(long honorCount) {
        this.honorCount = honorCount;
    }

    public long getMyDepartment() {
        return myDepartment;
    }

    public void setMyDepartment(long myDepartment) {
        this.myDepartment = myDepartment;
    }

    public long getMyJob() {
        return myJob;
    }

    public void setMyJob(long myJob) {
        this.myJob = myJob;
    }

    public JSONObject getJobAuthority() {
        return jobAuthority;
    }

    public void setJobAuthority(JSONObject jobAuthority) {
        this.jobAuthority = jobAuthority;
    }

    @Override
    public String toString() {
        return "WCManageClubModel{" +
                "clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", level=" + level +
                ", introduction='" + introduction + '\'' +
                ", slogan='" + slogan + '\'' +
                ", departments='" + departments + '\'' +
                ", jobs='" + jobs + '\'' +
                ", memberCount=" + memberCount +
                ", honorCount=" + honorCount +
                ", myDepartment=" + myDepartment +
                ", myJob=" + myJob +
                ", jobAuthority=" + jobAuthority +
                '}';
    }
}
