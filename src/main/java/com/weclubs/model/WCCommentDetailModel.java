package com.weclubs.model;

/**
 * 评论的 model 类
 *
 * Created by fangzanpan on 2017/4/1.
 */
public class WCCommentDetailModel {

    private long commentId;
    private String content;
    private long createDate;
    private String sourceType;
    private long sourceId;
    private int status;

    private long studentId;
    private String studentName;
    private String studentAvatar;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
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

    public String getStudentAvatar() {
        return studentAvatar;
    }

    public void setStudentAvatar(String studentAvatar) {
        this.studentAvatar = studentAvatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WCCommentDetailModel{" +
                "commentId=" + commentId +
                ", content=" + content +
                ", createDate=" + createDate +
                ", sourceType='" + sourceType + '\'' +
                ", sourceId=" + sourceId +
                ", status=" + status +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentAvatar='" + studentAvatar + '\'' +
                '}';
    }
}
