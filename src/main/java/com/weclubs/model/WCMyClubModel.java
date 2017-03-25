package com.weclubs.model;

import com.weclubs.bean.WCClubBean;

import java.io.Serializable;

/**
 * 我的社团 model 类
 *
 * Created by fangzanpan on 2017/3/13.
 */
public class WCMyClubModel extends WCClubBean implements Serializable {

    private long memberCount;
    private long todoCount;
    private long activityCount;

    public WCMyClubModel(WCClubBean clubBean) {
        super();

        setClubId(clubBean.getClubId());
        setAvatarUrl(clubBean.getAvatarUrl());
        setSchoolId(clubBean.getSchoolId());
        setIntroduction(clubBean.getIntroduction());
        setIsAuth(clubBean.getIsAuth());
        setLevel(clubBean.getLevel());
        setName(clubBean.getName());
        setSchoolBean(clubBean.getSchoolBean());
        setSlogan(clubBean.getSlogan());
        setStatus(clubBean.getStatus());
    }

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public long getTodoCount() {
        return todoCount;
    }

    public void setTodoCount(long todoCount) {
        this.todoCount = todoCount;
    }

    public long getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(long activityCount) {
        this.activityCount = activityCount;
    }

    @Override
    public String toString() {
        return "WCMyClubModel{" +
                "memberCount=" + memberCount +
                ", todoCount=" + todoCount +
                ", activityCount=" + activityCount +
                '}';
    }
}
