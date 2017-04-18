package com.weclubs.model;

import com.weclubs.bean.WCClubMissionBean;

/**
 * 发起者的
 *
 * Created by fangzanpan on 2017/4/18.
 */
public class WCSponsorNotifyModel extends WCClubMissionBean {

    private long unreadCount;
    private long totalCount;

    public long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(long unreadCount) {
        this.unreadCount = unreadCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return super.toString() + "WCSponsorNotifyModel{" +
                "unreadCount=" + unreadCount +
                ", totalCount=" + totalCount +
                '}';
    }
}
