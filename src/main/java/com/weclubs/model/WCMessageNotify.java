package com.weclubs.model;

import io.rong.messages.BaseMessage;
import io.rong.util.GsonUtil;

/**
 * 自定义的动态消息
 *
 * Created by fangzanpan on 2017/7/7.
 */
public class WCMessageNotify extends BaseMessage {

    public static final String TYPE_NOTIFY = "dynamic_notify";
    public static final String TYPE_ADD_USER = "add_new_user";

    private String message_type = "";
    private String title = "";
    private String content = "";
    private long user_id = 0;
    private String user_name = "";
    private String user_avatar = "";
    private String user_type = "";
    private long sponsor_date = 0;
    private String extra = "";

    private transient static final String TYPE = "WC:DynamicMsg";

    public WCMessageNotify(String messageType) {
        this.message_type = messageType;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public long getSponsor_date() {
        return sponsor_date;
    }

    public void setSponsor_date(long sponsor_date) {
        this.sponsor_date = sponsor_date;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, WCMessageNotify.class);
    }
}
