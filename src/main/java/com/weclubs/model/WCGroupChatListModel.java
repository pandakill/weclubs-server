package com.weclubs.model;

import com.alibaba.fastjson.JSON;
import com.weclubs.application.rongcloud.WCRongCloudServiceImpl;
import com.weclubs.bean.WCClubBean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 群聊列表
 *
 * Created by fangzanpan on 2017/6/22.
 */
public class WCGroupChatListModel implements Serializable {

    private long clubId;
    private String clubName;
    private String avatarUrl;
    private String lastMsgContent;
    private long freshMsgCount;
    private String imGroupId;
    private long lastMsgDate;

    public WCGroupChatListModel() {
    }

    public WCGroupChatListModel(WCClubBean clubBean) {
        setClubId(clubBean.getClubId());
        setClubName(clubBean.getName());
        setAvatarUrl(clubBean.getAvatarUrl());
        setImGroupId(WCRongCloudServiceImpl.RONG_CLUB_ID_TAG + clubBean.getClubId());

        setLastMsgContent(null);
        setLastMsgDate(0);
        setFreshMsgCount(0);
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLastMsgContent() {
        return lastMsgContent;
    }

    public void setLastMsgContent(String lastMsgContent) {
        this.lastMsgContent = lastMsgContent;
    }

    public long getFreshMsgCount() {
        return freshMsgCount;
    }

    public void setFreshMsgCount(long freshMsgCount) {
        this.freshMsgCount = freshMsgCount;
    }

    public String getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(String imGroupId) {
        this.imGroupId = imGroupId;
    }

    public long getLastMsgDate() {
        return lastMsgDate;
    }

    public void setLastMsgDate(long lastMsgDate) {
        this.lastMsgDate = lastMsgDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club_id", getClubId());
        result.put("club_name", getClubName());
        result.put("avatar_url", getAvatarUrl());
        result.put("im_group_id", getImGroupId());
        result.put("last_msg_content", getLastMsgContent());
        result.put("last_msg_date", getLastMsgDate());
        result.put("fresh_msg_count", getFreshMsgCount());
        return result;
    }
}
