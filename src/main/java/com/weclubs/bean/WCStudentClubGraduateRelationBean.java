package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、届数关系表，对应数据库 t_student_club_graduate_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentClubGraduateRelationBean implements Serializable {

    public static int STATUS_ALREADY_EXIT = 0;  // 已经退出社团
    public static int STATUS_STILL_IN = 1;  // 仍在社团
    public static int STATUS_REFUSE_JOIN = 2;   // 被拒绝加入该社团

    private long stuCluGraRelId;
    private long graduateId;
    private long studentId;
    private int status;
    private long departmentId;
    private long jobId;
    private int superAdmin;
    private int isDel;

    private WCClubGraduateBean clubGraduateBean;
    private WCStudentBean studentBean;

    public long getStuCluGraRelId() {
        return stuCluGraRelId;
    }

    public void setStuCluGraRelId(long stuCluGraRelId) {
        this.stuCluGraRelId = stuCluGraRelId;
    }

    public long getGraduateId() {
        return graduateId;
    }

    public void setGraduateId(long graduateId) {
        this.graduateId = graduateId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public int getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(int superAdmin) {
        this.superAdmin = superAdmin;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCClubGraduateBean getClubGraduateBean() {
        return clubGraduateBean;
    }

    public void setClubGraduateBean(WCClubGraduateBean clubGraduateBean) {
        this.clubGraduateBean = clubGraduateBean;
    }

    public WCStudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(WCStudentBean studentBean) {
        this.studentBean = studentBean;
    }

    @Override
    public String toString() {
        return "WCStudentClubGraduateRelationBean{" +
                "stuCluGraRelId=" + stuCluGraRelId +
                ", graduateId=" + graduateId +
                ", studentId=" + studentId +
                ", status=" + status +
                ", departmentId=" + departmentId +
                ", jobId=" + jobId +
                ", superAdmin=" + superAdmin +
                ", isDel=" + isDel +
                ", clubGraduateBean=" + clubGraduateBean +
                ", studentBean=" + studentBean +
                '}';
    }
}
