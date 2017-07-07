package com.weclubs.application.dynamic;

import com.weclubs.application.jiguang_push.WCIJiGuangPushService;
import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.mapper.WCDynamicMapper;
import com.weclubs.util.Constants;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 动态的 service 实现类
 *
 * Created by fangzanpan on 2017/3/27.
 */
@Service(value = "dynamicService")
class WCDynamicServiceImpl implements WCIDynamicService {

    private Logger log = Logger.getLogger(WCDynamicServiceImpl.class);

    private WCIClubMissionService mMissionService;
    private WCIClubMeetingService mMeetingService;
    private WCINotificationService mNotifyService;
    private WCIUserService mUserService;
    private WCIJiGuangPushService mJiGuangPushService;

    private WCDynamicMapper mDynamicMapper;

    @Autowired
    public WCDynamicServiceImpl(WCIClubMissionService mMissionService, WCIClubMeetingService mMeetingService,
                                WCINotificationService mNotifyService, WCDynamicMapper dynamicMapper,
                                WCIUserService mUserService, WCIJiGuangPushService jiGuangPushService) {
        this.mMissionService = mMissionService;
        this.mMeetingService = mMeetingService;
        this.mNotifyService = mNotifyService;
        this.mDynamicMapper = dynamicMapper;
        this.mUserService = mUserService;
        this.mJiGuangPushService = jiGuangPushService;
    }

    public WCStudentMissionRelationBean getDynamicStudentRelationByDynamicId(long studentId, long dynamicId, String dynamicType) {

        log.info("getDynamicStudentRelationByDynamicId:[studentId = " + studentId + "][dynamicId = " + dynamicId
                + "][dynamicType = " + dynamicType + "]");

        WCStudentMissionRelationBean result = null;

        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            result = mDynamicMapper.getDynamicStudentRelation(studentId, dynamicId, 0);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            result = mDynamicMapper.getDynamicStudentRelation(studentId, dynamicId, 1);
        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            result = mDynamicMapper.getDynamicStudentRelation(studentId, dynamicId, 2);
        }

        return result;
    }

    public WCClubMissionBean getDynamicDetail(long dynamicId, String dynamicType) {

        WCClubMissionBean dynamicBean = null;
        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            dynamicBean = mNotifyService.getNotificationDetailById(dynamicId);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            dynamicBean = mMissionService.getMissionDetailById(dynamicId);
        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            dynamicBean = mMeetingService.getMeetingDetailById(dynamicId);
        }

        return dynamicBean;
    }

    public WCHttpStatus setDynamicStatus(long studentId, long dynamicId, String type, String... status) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;
        WCStudentMissionRelationBean relationBean = getDynamicStudentRelationByDynamicId(studentId, dynamicId, type);
        if (relationBean == null) {
            log.warn("setDynamicStatus：找不到【dynamicId = " + dynamicId + "】" +
                    "【studentId = " + studentId + "】【type = " + type + "】的动态");
            check.msg = "找不到该动态，请尝试重新提交状态";
            return check;
        }

        WCClubMissionBean missionBean = relationBean.getClubMissionBean();
        if (missionBean == null) {
            log.warn("setDynamicStatus：信息不完整，请检查");
            check.msg = "动态信息有误，请检查后重新提交";
            return check;
        }

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean == null) {
            log.warn("setDynamicStatus：找不到学生用户");
            check.msg = "动态信息有误，请检查后重新提交";
            return check;
        }

        if (status[0].equals("confirm")) {
            if (relationBean.getStatus() > 0) {
                log.warn("setDynamicStatus：动态status = " + relationBean.getStatus() + "，无法再次确认");
                check.msg = "该动态已无法确认，请检查后重新提交";
                return check;
            }

            relationBean.setStatus(1);
        } else if (status[0].equals("leave")) {

            if (missionBean.getType() != 2) {
                log.warn("setDynamicStatus：该动态的类型为 = " + missionBean.getType());
                check.msg = "该动态无法进行请假";
                return check;
            }

            if (relationBean.getStatus() > 0) {
                log.warn("setDynamicStatus：动态status = " + relationBean.getStatus() + "，无法再次请假");
                check.msg = "该动态已经请假，请检查后重新提交";
                return check;
            }

            relationBean.setStatus(3);
        } else if (status[0].equals("finish")) {

            if (missionBean.getType() != 2) {
                log.warn("setDynamicStatus：该动态的类型为 = " + missionBean.getType());
                check.msg = "该动态无法进行完成操作";
                return check;
            }

            if (relationBean.getStatus() == 2) {
                log.warn("setDynamicStatus：动态status = " + relationBean.getStatus() + "，无法再次完成");
                check.msg = "该动态已经完成，请不要重复提交";
                return check;
            } else if (relationBean.getStatus() == 0) {
                log.warn("setDynamicStatus：动态status = " + relationBean.getStatus() + "，无法完成操作");
                check.msg = "该动态尚未确认，请确认后再进行完成操作";
                return check;
            }

            relationBean.setStatus(2);
        } else {
            log.warn("setDynamicStatus：提交的状态有误：status = " + status[0]);
            check.msg = "设置的状态有误，请检查后重新提交";
            return check;
        }

        if (status.length > 1 && !StringUtils.isEmpty(status[1])) {
            relationBean.setComment(status[1]);
        }
        mDynamicMapper.updateDynamicStatus(relationBean);
        check = WCHttpStatus.SUCCESS;

        String activity = "";
        if (status[0].equals("confirm")) {
            activity += "确认";
        } else if (status[0].equals("leave")) {
            activity += "请假";
        } else if (status[0].equals("finish")) {
            activity += "完成";
        }

        String chineseType = "";
        if (type.equals(Constants.TODO_MEETING)) {
            chineseType = "会议";
        } else if (type.equals(Constants.TODO_MISSION)) {
            chineseType = "任务";
        } else if (type.equals(Constants.TODO_NOTIFY)) {
            chineseType = "通知";
        }

        mJiGuangPushService.pushDynamicStatusChange(activity, chineseType, studentBean, missionBean.getAttribution(),
                type, dynamicId, missionBean.getSponsorId());

        return check;
    }
}
