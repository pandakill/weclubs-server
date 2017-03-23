package com.weclubs.bean;

import java.io.Serializable;

/**
 * 评论基本信息表，对应数据库 t_comment 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCCommentBean implements Serializable {

    private long commentId;
    private long studentId;
    private String content;
    private String createDate;
    private int sourceType;
    private long sourceId;
    private int status;
    private int isDel;

    private WCStudentBean studentBean;
    private WCClubActivityBean clubActivityBean;
    private WCClubMissionBean clubMissionBean;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
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

    public WCClubMissionBean getClubMissionBean() {
        return clubMissionBean;
    }

    public void setClubMissionBean(WCClubMissionBean clubMissionBean) {
        this.clubMissionBean = clubMissionBean;
    }

    @Override
    public String toString() {
        return "WCCommentBean{" +
                "commentId=" + commentId +
                ", studentId=" + studentId +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", sourceType=" + sourceType +
                ", sourceId=" + sourceId +
                ", status=" + status +
                ", isDel=" + isDel +
                ", studentBean=" + studentBean +
                ", clubActivityBean=" + clubActivityBean +
                ", clubMissionBean=" + clubMissionBean +
                '}';
    }
}
