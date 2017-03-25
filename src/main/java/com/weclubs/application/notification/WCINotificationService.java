package com.weclubs.application.notification;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;

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

    List<WCStudentMissionRelationBean> getNotificationsByStudentId(long studentId);

    List<WCClubMissionBean> getUnConfirmNotificationByClubId(long studentId);
}
