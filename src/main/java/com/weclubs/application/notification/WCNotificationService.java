package com.weclubs.application.notification;

import com.weclubs.application.club.WCIClubGraduateService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.mapper.WCNotificationMapper;
import com.weclubs.model.WCSponsorNotifyModel;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知服务的实现类
 *
 * Created by fangzanpan on 2017/3/9.
 */
@Service("notificationService")
class WCNotificationService implements WCINotificationService {

    private Logger log = Logger.getLogger(WCNotificationService.class);

    private WCNotificationMapper mNotificationMapper;
    private WCIUserService mUserService;
    @Autowired
    private WCIClubGraduateService mClubGraduateService;

    @Autowired
    public WCNotificationService(WCIUserService mUserService, WCNotificationMapper mNotificationMapper) {
        this.mUserService = mUserService;
        this.mNotificationMapper = mNotificationMapper;
    }

    public void createNotification(WCClubMissionBean notificationBean) {

        if (notificationBean == null) {
            log.error("createNotification：创建通知失败，notificationBean不能为空。");
            return;
        }

        mNotificationMapper.createNotification(notificationBean);
    }

    public void updateNotification(WCClubMissionBean clubMissionBean) {

        if (clubMissionBean == null) {
            log.error("updateNotification：更新通知失败，notificationBean不能为空。");
            return;
        }

        if (clubMissionBean.getMissionId() <= 0) {
            log.error("updateNotification：更新通知失败，notificationBean.getId不能小于等于0。");
            return;
        }

        mNotificationMapper.updateNotification(clubMissionBean);
    }

    public void deleteNotification(long notificationId) {

        if (notificationId <= 0) {
            log.error("deleteNotification：删除通知失败，notificationId不能小于等于0。");
            return;
        }

        mNotificationMapper.deleteNotificationById(notificationId);
    }

    public void changeNotificationStatus(long notificationId, int status) {


        if (notificationId <= 0) {
            log.error("changeNotificationStatus：更新通知状态失败，notificationId不能小于等于0。");
            return;
        }

        if (mNotificationMapper.getNotificationById(notificationId) == null) {
            log.error("changeNotificationStatus：更新通知状态失败，找不到该通知。");
            return;
        }

        mNotificationMapper.changeNotificationStatus(notificationId, status);
    }

    public WCClubMissionBean getNotificationDetailById(long notificationId) {

        if (notificationId <= 0) {
            log.error("getNotificationDetailById：查找通知详情失败，notificationId不能小于等于0。");
            return null;
        }

        WCClubMissionBean notification = mNotificationMapper.getNotificationById(notificationId);

        if (notification == null) {
            log.error("getNotificationDetailById：找不到 notificationId = " + notificationId + "的通知。" );
            return null;
        }

        if (notification.getSponsorStudentBean() == null && notification.getSponsorId() > 0) {
            WCStudentBean sponsor = mUserService.getUserInfoById(notification.getSponsorId());
            notification.setSponsorStudentBean(sponsor);
        }

        return notification;
    }

    public List<WCClubMissionBean> getNotificationsBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getNotificationsBySchoolId：查找学校所有通知失败，schoolId不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getNotificationsBySchoolId(schoolId);
    }

    public List<WCClubMissionBean> getNotificationsByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getNotificationsByClubId：查找社团所有通知失败，clubId不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getNotificationsByClubId(clubId);
    }

    public List<WCStudentMissionRelationBean> getNotificationsByStudentId(long studentId, long clubId) {

        if (studentId <= 0) {
            log.error("getNotificationsByStudentId：查找学生所有通知失败，studentId不能小于等于0。");
            return null;
        }

        if (clubId <= 0) {
            log.error("getNotificationsByStudentId：查找学生所有通知失败，clubId不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getNotificationsByStudentId(studentId, clubId);
    }

    public List<WCClubMissionBean> getUnConfirmNotificationByClubId(long studentId) {

        if (studentId <= 0) {
            log.error("getUnConfirmNotificationByClubId：查找学生所有未确认通知失败，studentId不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getUnConfirmNotificationByClubId(studentId);
    }

    @Override
    public List<WCSponsorNotifyModel> getNotifyBySponsor(long sponsorId) {

        if (sponsorId <= 0) {
            log.error("getNotifyBySponsor：sponsorId 不能小于等于0。");
            return null;
        }

        List<WCSponsorNotifyModel> notifyModelList = mNotificationMapper.getNotifyBySponsor(sponsorId);
        if (notifyModelList != null && notifyModelList.size() > 0) {
            for (WCSponsorNotifyModel sponsorNotifyModel : notifyModelList) {
                List<WCStudentMissionRelationBean> total = getNotifyRelationByNotifyId(sponsorNotifyModel.getMissionId());
                List<WCStudentMissionRelationBean> unread = getUnConfirmNotifyRelationByNotifyId(sponsorNotifyModel.getMissionId());
                sponsorNotifyModel.setTotalCount(total == null ? 0 : total.size());
                sponsorNotifyModel.setUnreadCount(unread == null ? 0 : unread.size());
            }
        }

        return notifyModelList;
    }

    @Override
    public List<WCStudentMissionRelationBean> getUnConfirmNotifyRelationByNotifyId(long notifyId) {

        if (notifyId <= 0) {
            log.error("getUnConfirmNotifyRelationByNotifyId：notifyId 不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getUnConfirmRelationByNotifyId(notifyId);
    }

    @Override
    public List<WCStudentMissionRelationBean> getNotifyRelationByNotifyId(long notifyId) {

        if (notifyId <= 0) {
            log.error("getNotifyRelationByNotifyId：notifyId 不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getRelationByNotifyId(notifyId);
    }

    @Override
    public WCHttpStatus publicNotify(long sponsorId, String content, long clubId, String students) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (sponsorId <= 0) {
            log.error("publicNotify：sponsorId 不能小于等于0");
            check.msg = "sponsorId 不能小于等于0";
            return check;
        }

        if (clubId <= 0) {
            log.error("publicNotify：clubId 不能小于等于0");
            check.msg = "clubId 不能小于等于0";
            return check;
        }

        if (StringUtils.isEmpty(students)) {
            log.error("publicNotify：students 不能为空");
            check.msg = "students 不能为空";
            return check;
        }

        String[] ids = students.split(",");
        if (ids.length < 1) {
            log.error("publicNotify：至少需要有一个通知学生");
            check.msg = "至少需要有一个通知学生";
            return check;
        }

        WCClubGraduateBean graduateBean = mClubGraduateService.getCurrentClubGraduate(clubId);
        if (graduateBean == null) {
            log.error("publicNotify：找不到该社团");
            check.msg = "找不到该社团";
            return check;
        }

        WCStudentClubGraduateRelationBean graduateRelationBean
                = mClubGraduateService.getStudentClubGraduationRelationByGraduateId(sponsorId, graduateBean.getClubGraduateId());
        if (graduateRelationBean == null) {
            log.error("publicNotify：发布者不属于该社团");
            check.msg = "发布者不属于该社团";
            return check;
        }

        WCClubMissionBean notifyBean = new WCClubMissionBean();
        notifyBean.setAttribution(content);
        notifyBean.setSponsorId(sponsorId);
        notifyBean.setClubId(clubId);
        notifyBean.setCreateDate(System.currentTimeMillis());
        notifyBean.setType(WCClubMissionBean.TYPE_NOTIFY);
        notifyBean.setParentId(0);
        notifyBean.setIsDel(0);
        notifyBean.setGraduateId(graduateBean.getClubGraduateId());

        mNotificationMapper.createNotification(notifyBean);

        List<WCStudentMissionRelationBean> relations = new ArrayList<>();
        for (String id : ids) {
            WCStudentMissionRelationBean relationBean = new WCStudentMissionRelationBean();
            relationBean.setMissionId(notifyBean.getMissionId());
            relationBean.setStudentId(Long.parseLong(id));
            relationBean.setCreateDate(notifyBean.getCreateDate());

            relations.add(relationBean);
        }

        mNotificationMapper.createStudentRelation(relations);

        check = WCHttpStatus.SUCCESS;
        return check;
    }
}
