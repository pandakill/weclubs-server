package com.weclubs.model;

import java.io.Serializable;

/**
 * 我的社团 model 类
 *
 * Created by fangzanpan on 2017/3/13.
 */
public class WCMyClubModel implements Serializable {

    private long clubId;
    private String clubName;
    private long memberCount;
    private String clubLevel;
    private long todoCount;
    private long activityCount;

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

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public String getClubLevel() {
        return clubLevel;
    }

    public void setClubLevel(String clubLevel) {
        this.clubLevel = clubLevel;
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
                "clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", memberCount=" + memberCount +
                ", clubLevel='" + clubLevel + '\'' +
                ", todoCount=" + todoCount +
                ", activityCount=" + activityCount +
                '}';
    }
}
