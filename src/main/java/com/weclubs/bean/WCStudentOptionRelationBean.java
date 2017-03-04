package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、投票选项关系实体，对应数据库 t_student_option_relation 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCStudentOptionRelationBean implements Serializable {

    private long id;
    private long studentId;
    private long voteId;
    private String voteOptionIds;
    private int status;

    private WCStudentBean studentBean;
    private WCVoteBean voteBean;

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

    public long getVoteId() {
        return voteId;
    }

    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }

    public String getVoteOptionIds() {
        return voteOptionIds;
    }

    public void setVoteOptionIds(String voteOptionIds) {
        this.voteOptionIds = voteOptionIds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WCStudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(WCStudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public WCVoteBean getVoteBean() {
        return voteBean;
    }

    public void setVoteBean(WCVoteBean voteBean) {
        this.voteBean = voteBean;
    }

    @Override
    public String toString() {
        return "WCStudentOptionRelationBean{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", voteId=" + voteId +
                ", voteOptionIds='" + voteOptionIds + '\'' +
                ", status=" + status +
                ", studentBean=" + studentBean +
                ", voteBean=" + voteBean +
                '}';
    }
}
