package com.weclubs.application.activity;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.comment.WCICommentService;
import com.weclubs.bean.WCClubActivityBean;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCStudentActivityRelationBean;
import com.weclubs.mapper.WCClubActivityMapper;
import com.weclubs.model.WCActivityDetailBaseModel;
import com.weclubs.model.WCCommentDetailModel;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 活动相关的 service 实现类
 *
 * Created by fangzanpan on 2017/3/28.
 */
@Service(value = "activityService")
class WCActivityServiceImpl implements WCIActivityService {

    private Logger log = Logger.getLogger(WCActivityServiceImpl.class);

    private WCClubActivityMapper mActivityMapper;
    private WCICommentService mCommentService;
    private WCIClubService mClubService;

    @Autowired
    public WCActivityServiceImpl(WCClubActivityMapper mActivityMapper, WCICommentService mCommentService,
                                 WCIClubService clubService) {
        this.mActivityMapper = mActivityMapper;
        this.mCommentService = mCommentService;
        this.mClubService = clubService;
    }

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

    @Override
    public WCHttpStatus publicActivity(HashMap<String, Object> requestData) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        int activityType = WCCommonUtil.getIntegerData(requestData.get("activity_type"));
        String activityName = (String) requestData.get("activity_name");
        String attribution = (String) requestData.get("attribution");
        String address = (String) requestData.get("address");
        long holdDate = WCCommonUtil.getLongData(requestData.get("hold_date"));
        long holdDeadline = WCCommonUtil.getLongData(requestData.get("hold_deadline"));
        String posterUrl = (String) requestData.get("poster_url");
        int needSign = WCCommonUtil.getIntegerData(requestData.get("need_sign"));
        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
        long sponsorId = WCCommonUtil.getLongData(requestData.get("user_id"));

        if (StringUtils.isEmpty(activityName)) {
            check.msg = "活动标题不能为空";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (StringUtils.isEmpty(attribution)) {
            check.msg = "活动介绍不能为空";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (StringUtils.isEmpty(address)) {
            check.msg = "地址不能为空";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (holdDate == 0 || String.valueOf(holdDate).length() != 13) {
            check.msg = "活动举办日期格式错误";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (holdDeadline == 0 || String.valueOf(holdDeadline).length() != 13) {
            check.msg = "活动截止日期格式错误";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (holdDeadline < holdDate) {
            check.msg = "活动举办截止时间不能早于举办开始时间";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (needSign != 0 && needSign != 1) {
            check.msg = "需要签到字段格式错误";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        if (clubId <= 0) {
            check.msg = "club_id 不能小于等于0";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        WCClubBean clubBean = mClubService.getClubInfoById(clubId);
        if (clubBean == null) {
            check.msg = "找不到该社团";
            log.error("publicActivity：" + check.msg);
            return check;
        }

        WCClubActivityBean activityBean = new WCClubActivityBean();
        activityBean.setName(activityName);
        activityBean.setAttribution(attribution);
        activityBean.setAddress(address);
        activityBean.setHoldDate(holdDate);
        activityBean.setHoldDeadline(holdDeadline);
        activityBean.setPosterUrl(posterUrl);
        activityBean.setNeedSign(needSign);
        activityBean.setClubId(clubId);
        activityBean.setSponsorId(sponsorId);
        activityBean.setCreateDate(System.currentTimeMillis());

        if (activityType == WCClubActivityBean.TYPE_SCHOOL_PUBLIC) {    // 活动类型为校园公开活动
            int needApply = WCCommonUtil.getIntegerData(requestData.get("need_apply"));

            if (needApply != 0 && needApply != 1) {
                check.msg = "需要预报名字段格式错误";
                log.error("publicActivity：" + check.msg);
                return check;
            }

            activityBean.setActivityType(activityType);
            activityBean.setAllowPreApply(needApply);

            if (needApply == 1) {
                long applyDeadline = WCCommonUtil.getLongData(requestData.get("apply_deadline"));

                if (applyDeadline == 0 || String.valueOf(applyDeadline).length() != 13) {
                    check.msg = "报名截止日期格式错误";
                    log.error("publicActivity：" + check.msg);
                    return check;
                }

                if (applyDeadline > holdDeadline) {
                    check.msg = "报名截止时间不能超过活动举办截止时间";
                    log.error("publicActivity：" + check.msg);
                    return check;
                }

                if (applyDeadline < holdDate) {
                    check.msg = "报名截止时间不能早于活动举办开始时间";
                    log.error("publicActivity：" + check.msg);
                    return check;
                }

                activityBean.setApplyDeadline(applyDeadline);
            }

            List<WCClubActivityBean> list = new ArrayList<>();
            list.add(activityBean);
            mActivityMapper.createActivity(list);
            log.info("activity = " + list.toString());

            check = WCHttpStatus.SUCCESS;

        } else if (activityType == WCClubActivityBean.TYPE_CLUB_PRIVATE) {  // 活动类型为组织内部活动

            activityBean.setActivityType(activityType);

            String invitee = (String) requestData.get("invitee");

            if (StringUtils.isEmpty(invitee)) {
                check.msg = "受邀人员不能为空";
                log.error("publicActivity：" + check.msg);
                return check;
            }

            String[] ids = invitee.split(",");
            if (ids.length <= 0) {
                check.msg = "受邀人员不能为空";
                log.error("publicActivity：" + check.msg);
                return check;
            }

            List<WCClubActivityBean> list = new ArrayList<>();
            list.add(activityBean);
            mActivityMapper.createActivity(list);

            List<WCStudentActivityRelationBean> relationList = new ArrayList<>();
            for (String id : ids) {
                WCStudentActivityRelationBean relationBean = new WCStudentActivityRelationBean();
                relationBean.setActivityId(list.get(0).getClubActivityId());
                relationBean.setStudentId(Long.parseLong(id));

                relationList.add(relationBean);
            }
            mActivityMapper.createStudentActivityRelation(relationList);

            check = WCHttpStatus.SUCCESS;
        }

        return check;
    }

    @Override
    public List<WCStudentActivityRelationBean> getSignData(long activityId) {

        if (activityId <= 0) {
            log.error("getSignData：activityId 不能小于等于0");
            return null;
        }

        return mActivityMapper.getActivityRelationList(activityId);
    }

    // TODO: 2017/4/20 暂时没有实现修改受邀人员
    @Override
    public WCHttpStatus editActivity(HashMap<String, Object> requestData) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        long activityId = WCCommonUtil.getLongData(requestData.get("activity_id"));

        WCClubActivityBean clubActivityBean = mActivityMapper.getActivityByActivityId(activityId);
        if (clubActivityBean == null) {
            check.msg = "找不到 id = 【" + activityId + "】的活动";
            log.error("editActivity：" + check.msg);
            return check;
        }

        if (requestData.containsKey("activity_name")) {
            String activityName = (String) requestData.get("activity_name");

            if (StringUtils.isEmpty(activityName)) {
                check.msg = "活动标题不能为空";
                log.error("editActivity：" + check.msg);
                return check;
            }

            clubActivityBean.setName(activityName);
        }

        if (requestData.containsKey("attribution")) {
            String attribution = (String) requestData.get("attribution");

            if (StringUtils.isEmpty(attribution)) {
                check.msg = "活动介绍不能为空";
                log.error("editActivity：" + check.msg);
                return check;
            }

            clubActivityBean.setAttribution(attribution);
        }

        if (requestData.containsKey("address")) {
            String address = (String) requestData.get("address");

            if (StringUtils.isEmpty(address)) {
                check.msg = "地址不能为空";
                log.error("editActivity：" + check.msg);
                return check;
            }

            clubActivityBean.setAddress(address);
        }

        if (requestData.containsKey("hold_date")) {
            long holdDate = WCCommonUtil.getLongData(requestData.get("hold_date"));

            if (holdDate == 0 || String.valueOf(holdDate).length() != 13) {
                check.msg = "活动举办日期格式错误";
                log.error("editActivity：" + check.msg);
                return check;
            }

            clubActivityBean.setHoldDate(holdDate);
        }

        if (requestData.containsKey("hold_deadline")) {
            long holdDeadline = WCCommonUtil.getLongData(requestData.get("hold_deadline"));

            if (holdDeadline == 0 || String.valueOf(holdDeadline).length() != 13) {
                check.msg = "活动截止日期格式错误";
                log.error("editActivity：" + check.msg);
                return check;
            }

            if (holdDeadline < clubActivityBean.getHoldDate()) {
                check.msg = "活动举办截止时间不能早于举办开始时间";
                log.error("editActivity：" + check.msg);
                return check;
            }
        }

        if (requestData.containsKey("poster_url")) {
            String posterUrl = (String) requestData.get("poster_url");
            clubActivityBean.setPosterUrl(posterUrl);
        }

        if (requestData.containsKey("need_sign")) {
            int needSign = WCCommonUtil.getIntegerData(requestData.get("need_sign"));

            if (needSign != 0 && needSign != 1) {
                check.msg = "需要签到字段格式错误";
                log.error("editActivity：" + check.msg);
                return check;
            }

            clubActivityBean.setNeedSign(needSign);
        }

        if (requestData.containsKey("need_apply")) {
            int needApply = WCCommonUtil.getIntegerData(requestData.get("need_apply"));

            if (needApply != 0 && needApply != 1) {
                check.msg = "需要报名字段格式错误";
                log.error("editActivity：" + check.msg);
                return check;
            }

            clubActivityBean.setAllowPreApply(needApply);
        }

        mActivityMapper.updateActivity(clubActivityBean);
        check = WCHttpStatus.SUCCESS;

        return check;
    }
}
