package com.weclubs.model;

/**
 * 社团的学生基本信息类
 *
 * Created by fangzanpan on 2017/3/27.
 */
public class WCStudentForClubModel extends WCStudentBaseInfoModel {

    private long clubId;
    private String clubName;
    private String clubLogo;
    private int clubLevel;
    private String clubSlogan;
    private String clubIntroduction;

    private long departmentId;
    private String departmentName;

    private long jobId;
    private String jobName;

    public WCStudentForClubModel() {}

    public WCStudentForClubModel(WCStudentBaseInfoModel baseInfoModel) {
        if (baseInfoModel == null) {
            return;
        }

        setStudentId(baseInfoModel.getStudentId());
        setAvatarUrl(baseInfoModel.getAvatarUrl());
        setGraduate(baseInfoModel.getGraduate());
        setSchoolName(baseInfoModel.getSchoolName());
        setClassName(baseInfoModel.getClassName());
        setCollegeId(baseInfoModel.getCollegeId());
        setCollegeName(baseInfoModel.getCollegeName());
        setSchoolId(baseInfoModel.getSchoolId());
        setMajorName(baseInfoModel.getMajorName());
        setRealName(baseInfoModel.getRealName());
        setNickName(baseInfoModel.getNickName());
        setMobile(baseInfoModel.getMobile());
    }

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

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public int getClubLevel() {
        return clubLevel;
    }

    public void setClubLevel(int clubLevel) {
        this.clubLevel = clubLevel;
    }

    public String getClubSlogan() {
        return clubSlogan;
    }

    public void setClubSlogan(String clubSlogan) {
        this.clubSlogan = clubSlogan;
    }

    public String getClubIntroduction() {
        return clubIntroduction;
    }

    public void setClubIntroduction(String clubIntroduction) {
        this.clubIntroduction = clubIntroduction;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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

    @Override
    public String toString() {
        return "WCStudentForClubModel{" +
                "clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", clubLevel=" + clubLevel +
                ", clubSlogan='" + clubSlogan + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                '}';
    }
}
