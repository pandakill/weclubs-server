package com.weclubs.model;

/**
 * 任务的基本类，包括完成程度等信息
 *
 * Created by fangzanpan on 2017/3/28.
 */
public class WCMissionBaseModel {

    private long missionId;
    private String attribution;
    private String address;
    private String createDate;
    private String deadline;
    private int type;

    private int status;

    private long studentId;
    private String studentName;
    private String avatarUrl;

    private String clubId;
    private long parentId;

    public long getMissionId() {
        return missionId;
    }

    public void setMissionId(long missionId) {
        this.missionId = missionId;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "WCMissionBaseModel{" +
                "missionId=" + missionId +
                ", attribution='" + attribution + '\'' +
                ", address='" + address + '\'' +
                ", createDate='" + createDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", clubId='" + clubId + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
