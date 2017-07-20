package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学生、消息之间的关系表
 *
 * Created by fangzanpan on 2017/6/12.
 */
public class WCStudentMessageRelationBean implements Serializable {

    private long stuMsgId;
    private long studentId;
    private long messageId;
    private int status;

    public WCStudentBean studentBean;
    public WCMessageBean messageBean;

    public long getStuMsgId() {
        return stuMsgId;
    }

    public void setStuMsgId(long stuMsgId) {
        this.stuMsgId = stuMsgId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
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

    public WCMessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(WCMessageBean messageBean) {
        this.messageBean = messageBean;
    }
}
