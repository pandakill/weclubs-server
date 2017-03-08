package com.weclubs.bean;

import java.io.Serializable;

/**
 * 对应数据库t_token表
 *
 * Created by fangzanpan on 2017/2/6.
 */
public class WCTokenBean implements Serializable {

    private long uId;
    private String token;
    private String caller;
    private long createDate;

    public long getUId() {
        return uId;
    }

    public void setUId(long uId) {
        this.uId = uId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "WCTokenBean{" +
                "uId=" + uId +
                ", token='" + token + '\'' +
                ", caller='" + caller + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
