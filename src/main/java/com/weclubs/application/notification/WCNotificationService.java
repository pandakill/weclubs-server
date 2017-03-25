package com.weclubs.application.notification;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.mapper.WCNotificationMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知服务的实现类
 *
 * Created by fangzanpan on 2017/3/9.
 */
@Service("notificationService")
public class WCNotificationService implements WCINotificationService {

    private Logger log = Logger.getLogger(WCNotificationService.class);

    @Autowired
    private WCNotificationMapper mNotificationMapper;

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

    public List<WCStudentMissionRelationBean> getNotificationsByStudentId(long studentId) {

        if (studentId <= 0) {
            log.error("getNotificationsByStudentId：查找学生所有通知失败，studentId不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getNotificationsByStudentId(studentId);
    }

    public List<WCClubMissionBean> getUnConfirmNotificationByClubId(long studentId) {

        if (studentId <= 0) {
            log.error("getUnConfirmNotificationByClubId：查找学生所有未确认通知失败，studentId不能小于等于0。");
            return null;
        }

        return mNotificationMapper.getUnConfirmNotificationByClubId(studentId);
    }
}
