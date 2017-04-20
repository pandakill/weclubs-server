package com.weclubs.bean;

import java.io.Serializable;
import java.util.HashMap;

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
    private long createDate;
    private long signDate;
    private long applyDate;

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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getSignDate() {
        return signDate;
    }

    public void setSignDate(long signDate) {
        this.signDate = signDate;
    }

    public long getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(long applyDate) {
        this.applyDate = applyDate;
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
                ", createDate=" + createDate +
                ", signDate=" + signDate +
                ", applyDate=" + applyDate +
                ", studentBean=" + studentBean +
                ", clubActivityBean=" + clubActivityBean +
                '}';
    }

    public HashMap<String, Object> getSignData() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("student_id", getStudentId());
        result.put("student_name", getStudentBean().getRealName());
        result.put("student_avatar", getStudentBean().getAvatarUrl());

        result.put("is_apply", getIsApply());
        result.put("is_sign", getIsSign());

        result.put("create_date", getIsSign() == 1 ? getSignDate() : (getIsApply() == 1 ? getApplyDate() : getCreateDate()));
        return result;
    }
}
