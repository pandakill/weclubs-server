package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、活动关系表，对应数据库 t_student_activity_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentActivityRelationBean implements Serializable {

    private long stuActRelId;
    private long activityId;
    private long studentId;
    private int isApply;
    private int isSign;
    private int isDel;

    private WCStudentBean studentBean;
    private WCClubActivityBean clubActivityBean;

    public long getStuActRelId() {
        return stuActRelId;
    }

    public void setStuActRelId(long stuActRelId) {
        this.stuActRelId = stuActRelId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getIsApply() {
        return isApply;
    }

    public void setIsApply(int isApply) {
        this.isApply = isApply;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCStudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(WCStudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public WCClubActivityBean getClubActivityBean() {
        return clubActivityBean;
    }

    public void setClubActivityBean(WCClubActivityBean clubActivityBean) {
        this.clubActivityBean = clubActivityBean;
    }

    @Override
    public String toString() {
        return "WCStudentActivityRelationBean{" +
                "stuActRelId=" + stuActRelId +
                ", activityId=" + activityId +
                ", studentId=" + studentId +
                ", isApply=" + isApply +
                ", isSign=" + isSign +
                ", isDel=" + isDel +
                ", studentBean=" + studentBean +
                ", clubActivityBean=" + clubActivityBean +
                '}';
    }
}
