package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、社团关系表，对应数据库 t_student_club_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentClubRelationBean implements Serializable {

    private long id;
    private long studentId;
    private long clubId;
    private int isEnter;
    private int isFollow;

    private WCStudentBean studentBean;
    private WCClubBean clubBean;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public int getIsEnter() {
        return isEnter;
    }

    public void setIsEnter(int isEnter) {
        this.isEnter = isEnter;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public WCStudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(WCStudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public WCClubBean getClubBean() {
        return clubBean;
    }

    public void setClubBean(WCClubBean clubBean) {
        this.clubBean = clubBean;
    }

    @Override
    public String toString() {
        return "WCStudentClubRelationBean{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", clubId=" + clubId +
                ", isEnter=" + isEnter +
                ", isFollow=" + isFollow +
                ", studentBean=" + studentBean +
                ", clubBean=" + clubBean +
                '}';
    }
}
