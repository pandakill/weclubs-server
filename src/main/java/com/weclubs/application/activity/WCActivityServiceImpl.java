package com.weclubs.application.activity;

import com.weclubs.application.comment.WCICommentService;
import com.weclubs.mapper.WCClubActivityMapper;
import com.weclubs.model.WCActivityDetailBaseModel;
import com.weclubs.model.WCCommentDetailModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动相关的 service 实现类
 *
 * Created by fangzanpan on 2017/3/28.
 */
@Service(value = "activityService")
class WCActivityServiceImpl implements WCIActivityService {

    private Logger log = Logger.getLogger(WCActivityServiceImpl.class);

    @Autowired
    private WCClubActivityMapper mActivityMapper;
    @Autowired
    private WCICommentService mCommentService;

    public List<WCActivityDetailBaseModel> getActivitiesByCurrentClub(long clubId) {

        if (clubId <= 0) {
            log.error("getActivitiesByCurrentClub：clubId不能小于等于0");
            return null;
        }

        List<WCActivityDetailBaseModel> activities = mActivityMapper.getActivitiesByCurrentClubId(clubId);

        if (activities != null && activities.size() > 0) {
            for (WCActivityDetailBaseModel activity : activities) {

                List<WCCommentDetailModel> commentBeanList = mCommentService.getCommentListBySourceId("activity", activity.getActivityId());
                if (commentBeanList != null) {
                    activity.setCommentCount(commentBeanList.size());
                }

                // TODO: 2017/4/19 需要获取活动的关注数量等参数
            }
        }

        return activities;
    }

    public WCActivityDetailBaseModel getActivityDetail(long activityId) {

        if (activityId <= 0) {
            log.error("getActivityDetail：activityId不能小于等于0");
            return null;
        }

        return mActivityMapper.getActivityDetail(activityId);
    }

    @Override
    public List<WCActivityDetailBaseModel> getManageClubBySponsorId(long sponsorId) {

        if (sponsorId <= 0) {
            log.error("getActivitiesByCurrentClub：sponsorId 不能小于等于0");
            return null;
        }

        List<WCActivityDetailBaseModel> activities = mActivityMapper.getActivitiesBySponsorId(sponsorId);

        if (activities != null && activities.size() > 0) {
            for (WCActivityDetailBaseModel activity : activities) {

                List<WCCommentDetailModel> commentBeanList = mCommentService.getCommentListBySourceId("activity", activity.getActivityId());
                if (commentBeanList != null) {
                    activity.setCommentCount(commentBeanList.size());
                }

                // TODO: 2017/4/19 需要获取活动的关注数量等参数
            }
        }

        return activities;
    }
}
