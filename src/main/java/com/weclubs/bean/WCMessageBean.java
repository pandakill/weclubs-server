package com.weclubs.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 推送消息实体，对应 t_message 表
 *
 * Created by fangzanpan on 2017/6/12.
 */
public class WCMessageBean implements Serializable {

    private long messageId;
    private String title;
    private String content;
    private String data;
    private int isDel;
    private int type;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
