package com.weclubs.mapper;

import com.weclubs.bean.WCClubActivityBean;
import com.weclubs.bean.WCStudentActivityRelationBean;
import com.weclubs.model.WCActivityDetailBaseModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织活动相关的的 mapper
 *
 * Created by fangzanpan on 2017/3/28.
 */
public interface WCClubActivityMapper {

    /**
     * 根据社团id获取该社团当前届的活动列表
     *
     * @param clubId    社团id
     * @return  该社团当届的活动列表
     */
    List<WCActivityDetailBaseModel> getActivitiesByCurrentClubId(@Param("clubId") long clubId);

    WCActivityDetailBaseModel getActivityDetail(@Param("activityId") long activityId);

    /**
     * 获取发布者发布的活动列表
     *
     * @param sponsorId 发布者id
     *
     * @return  该发布者发布的活动列表
     */
    List<WCActivityDetailBaseModel> getActivitiesBySponsorId(@Param("sponsorId") long sponsorId);

    /**
     * 批量新增活动列表
     *
     * @param activities    活动列表
     */
    void createActivity(@Param("list") List<WCClubActivityBean> activities);

    /**
     * 批量新增学生活动关系列表
     *
     * @param relations 关系列表
     */
    void createStudentActivityRelation(@Param("list")List<WCStudentActivityRelationBean> relations);
}
