package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、活动关系表，对应数据库 t_student_activity_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentActivityRelationBean implements Serializable {

    private long id;
    private long activityId;
    private long studentId;
    private int isApply;
    private int isDel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WCStudentActivityRelationBean{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", studentId=" + studentId +
                ", isApply=" + isApply +
                ", isDel=" + isDel +
                '}';
    }
}
