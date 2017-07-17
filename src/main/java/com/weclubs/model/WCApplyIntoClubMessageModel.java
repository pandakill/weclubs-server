package com.weclubs.model;

import io.rong.util.GsonUtil;

import java.io.Serializable;

/**
 * 申请加入社团消息实体
 *
 * Created by fangzanpan on 2017/7/17.
 */
public class WCApplyIntoClubMessageModel implements Serializable {

    /**
     * title : 申请加入社团
     * content : 社团联合会
     * user_id : 61
     * user_name : 林大佐
     * user_avatar : http://ww1.sinaimg.cn/mw600/b56eeebfgw1e09bfkvqpjj.jpg
     * user_type : student
     * sponsor_date : 1490463686617
     * message_type : add_new_user
     * extra : {"status":0,"club_id":12}
     */

    private String title;
    private String content;
    private long user_id;
    private String user_name;
    private String user_avatar;
    private String user_type;
    private long sponsor_date;
    private String message_type;
    private ExtraBean extra;

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

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public static class ExtraBean {
        /**
         * status : 0
         * club_id : 12
         */

        private int status;
        private long club_id;
        private long message_id;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getClub_id() {
            return club_id;
        }

        public void setClub_id(long club_id) {
            this.club_id = club_id;
        }

        public long getMessage_id() {
            return message_id;
        }

        public void setMessage_id(long message_id) {
            this.message_id = message_id;
        }
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, WCApplyIntoClubMessageModel.class);
    }
}
