package com.weclubs.model;

import com.weclubs.bean.WCClubMissionBean;

/**
 * 会议发布者的实体
 *
 * Created by fangzanpan on 2017/4/18.
 */
public class WCSponsorMeetingModel extends WCClubMissionBean {

    public WCSponsorMeetingModel(){}

    public WCSponsorMeetingModel(WCClubMissionBean missionBean) {
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

    private long totalCount;
    private long unConfirmCount;
    private long signCount;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getUnConfirmCount() {
        return unConfirmCount;
    }

    public void setUnConfirmCount(long unConfirmCount) {
        this.unConfirmCount = unConfirmCount;
    }

    public long getSignCount() {
        return signCount;
    }

    public void setSignCount(long signCount) {
        this.signCount = signCount;
    }

    @Override
    public String toString() {
        return "WCSponsorMeetingModel{" +
                "totalCount=" + totalCount +
                ", unConfirmCount=" + unConfirmCount +
                ", signCount=" + signCount +
                '}';
    }
}
