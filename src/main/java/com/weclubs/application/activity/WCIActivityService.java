package com.weclubs.application.activity;

import com.weclubs.model.WCActivityDetailBaseModel;

import java.util.List;

/**
 * 活动相关的 service 接口类
 *
 * Created by fangzanpan on 2017/3/28.
 */
public interface WCIActivityService {

    List<WCActivityDetailBaseModel> getActivitiesByCurrentClub(long clubId);

    WCActivityDetailBaseModel getActivityDetail(long activityId);

    /**
     * 根据发布者 id 获取该学生发布的活动列表
     *
     * @param sponsorId 发布者学生id
     *
     * @return  活动列表
     */
    List<WCActivityDetailBaseModel> getManageClubBySponsorId(long sponsorId);
}
