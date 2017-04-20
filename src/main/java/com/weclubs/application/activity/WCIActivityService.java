package com.weclubs.application.activity;

import com.weclubs.bean.WCStudentActivityRelationBean;
import com.weclubs.model.WCActivityDetailBaseModel;
import com.weclubs.util.WCHttpStatus;

import java.util.HashMap;
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

    /**
     * 发布新的活动
     *
     * @param requestData   请求参数
     *
     * @return  如果发布成功，则返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus publicActivity(HashMap<String, Object> requestData);

    /**
     * 获取活动的签到\报名数据
     *
     * @param activityId    活动id
     *
     * @return  返回该活动的签到数据列表
     */
    List<WCStudentActivityRelationBean> getSignData(long activityId);
}
