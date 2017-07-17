package com.weclubs.model;

import com.weclubs.util.WCCommonUtil;
import io.rong.util.GsonUtil;

import java.util.HashMap;

/**
 * 发布者的任务模型
 *
 * Created by fangzanpan on 2017/4/20.
 */
public class WCSponsorMissionModel extends WCMissionBaseModel {

    public WCSponsorMissionModel() {}

    public WCSponsorMissionModel(WCMissionBaseModel missionBaseModel) {
        setMissionId(missionBaseModel.getMissionId());
        setAttribution(missionBaseModel.getAttribution());
        setAddress(missionBaseModel.getAddress());
        setCreateDate(missionBaseModel.getCreateDate());
        setDeadline(missionBaseModel.getDeadline());
        setType(missionBaseModel.getType());

        setStatus(missionBaseModel.getStatus());

        setStudentId(missionBaseModel.getStudentId());
        setStudentName(missionBaseModel.getStudentName());
        setAvatarUrl(missionBaseModel.getAvatarUrl());

        setClubId(missionBaseModel.getClubId());
        setParentId(missionBaseModel.getParentId());

        setChildCount(missionBaseModel.getChildCount());
    }

    private String clubName;
    private String clubAvatar;

    private long unConfirmCount;
    private long unFinishCount;

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubAvatar() {
        return clubAvatar;
    }

    public void setClubAvatar(String clubAvatar) {
        this.clubAvatar = clubAvatar;
    }

    public long getUnConfirmCount() {
        return unConfirmCount;
    }

    public void setUnConfirmCount(long unConfirmCount) {
        this.unConfirmCount = unConfirmCount;
    }

    public long getUnFinishCount() {
        return unFinishCount;
    }

    public void setUnFinishCount(long unFinishCount) {
        this.unFinishCount = unFinishCount;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, WCSponsorMissionModel.class);
    }

    public HashMap<String, Object> getMissionHash() {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("club_id", getClubId());
        result.put("club_name", getClubName());
        result.put("club_avatar", getClubAvatar());

        result.put("has_child", getChildCount() > 0 ? 1 : 0);
        result.put("unconfirm_count", getUnConfirmCount());
        result.put("unfinish_count", getUnFinishCount());
        result.put("child_count", getChildCount());

        result.put("mission_id", getMissionId());
        result.put("content", getAttribution());
        result.put("create_date", getCreateDate());
        result.put("deadline", getDeadline());

        int status = 1;
        if (getIsDel() == 1) {
            status = 0;
        } else if (getType() != 0 && WCCommonUtil.getLongData(getDeadline()) == 0) {
            status = WCCommonUtil.isExpire(WCCommonUtil.getLongData(getCreateDate())) ? 2 : 1;
        } else if (getType() != 0 && WCCommonUtil.getLongData(getDeadline()) != 0) {
            status = WCCommonUtil.isExpire(WCCommonUtil.getLongData(getDeadline())) ? 2 : 1;
        }
        result.put("status", status);

        return result;
    }
}
