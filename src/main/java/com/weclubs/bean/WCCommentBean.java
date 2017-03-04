package com.weclubs.bean;

import java.io.Serializable;

/**
 * 评论基本信息表，对应数据库 t_comment 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCCommentBean implements Serializable {

    private long id;
    private long studentId;
    private String content;
    private String createDate;
    private int sourceType;
    private long sourceId;
    private int status;
    private int isDel;

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

    @Override
    public String toString() {
        return "WCCommentBean{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", sourceType=" + sourceType +
                ", sourceId=" + sourceId +
                ", status=" + status +
                ", isDel=" + isDel +
                '}';
    }
}
