package com.weclubs.mapper;

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
}
