package com.weclubs.model;

import com.weclubs.bean.WCClubMissionBean;

/**
 * 发起者的
 *
 * Created by fangzanpan on 2017/4/18.
 */
public class WCSponsorNotifyModel extends WCClubMissionBean {

    public WCSponsorNotifyModel(){}

    public WCSponsorNotifyModel(WCClubMissionBean missionBean) {
        setMissionId(missionBean.getMissionId());
        setClubId(missionBean.getClubId());
        setAttribution(missionBean.getAttribution());
        setGraduateId(missionBean.getGraduateId());
        setType(missionBean.getType());
        setParentId(missionBean.getParentId());
        setAddress(missionBean.getAddress());
        setDeadline(missionBean.getDeadline());
        setSponsorId(missionBean.getSponsorId());
        setCreateDate(missionBean.getCreateDate());
        setIsDel(missionBean.getIsDel());

        setClubBean(missionBean.getClubBean());
        setParentMissionBean(missionBean.getParentMissionBean());
        setSponsorStudentBean(missionBean.getSponsorStudentBean());
        setChildMissions(missionBean.getChildMissions());
        setChildMissonDetails(missionBean.getChildMissonDetails());
    }

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
