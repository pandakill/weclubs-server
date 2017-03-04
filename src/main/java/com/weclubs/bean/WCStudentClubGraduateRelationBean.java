package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、届数关系表，对应数据库 t_student_club_graduate_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentClubGraduateRelationBean implements Serializable {

    private long id;
    private long graduateId;
    private long studentId;
    private int status;
    private int isDel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WCStudentClubGraduateRelationBean{" +
                "id=" + id +
                ", graduateId=" + graduateId +
                ", studentId=" + studentId +
                ", status=" + status +
                ", isDel=" + isDel +
                '}';
    }
}
