package com.weclubs.application.notification;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCSponsorNotifyModel;

import java.util.List;

/**
 * 通知服务的接口类
 *
 * Created by fangzanpan on 2017/3/9.
 */
public interface WCINotificationService {

    void createNotification(WCClubMissionBean clubMissionBean);

    void updateNotification(WCClubMissionBean clubMissionBean);

    void deleteNotification(long notificationId);

    void changeNotificationStatus(long notificationId, int status);

    WCClubMissionBean getNotificationDetailById(long notificationId);

    List<WCClubMissionBean> getNotificationsBySchoolId(long schoolId);

    List<WCClubMissionBean> getNotificationsByClubId(long clubId);

    List<WCStudentMissionRelationBean> getNotificationsByStudentId(long studentId, long clubId);

    List<WCClubMissionBean> getUnConfirmNotificationByClubId(long studentId);

    /**
     * 根据学生id，获取由该学生发布的所有通知列表
     *
     * @param sponsorId 学生id
     *
     * @return  通知列表
     */
    List<WCSponsorNotifyModel> getNotifyBySponsor(long sponsorId);

    /**
     * 根据通知id获取尚未确认的学生关系
     *
     * @param notifyId  通知id
     *
     * @return  尚未确认的学生关系列表
     */
    List<WCStudentMissionRelationBean> getUnConfirmNotifyRelationByNotifyId(long notifyId);

    /**
     * 根据通知id获取所有的学生关系
     *
     * @param notifyId  通知id
     *
     * @return  所有的学生关系列表
     */
    List<WCStudentMissionRelationBean> getNotifyRelationByNotifyId(long notifyId);
}
