package com.weclubs.mapper;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_mission 通知的映射接口
 * t_club_mission.type = 0
 *
 * Created by fangzanpan on 2017/3/9.
 */
public interface WCNotificationMapper {

    void createNotification(WCClubMissionBean missionBean);

    void updateNotification(WCClubMissionBean missionBean);

    void deleteNotificationById(@Param("notificationId") long notificationId);

    void changeNotificationStatus(@Param("notificationId") long notificationId, int status);

    WCClubMissionBean getNotificationById(@Param("notificationId") long notificationId);

    List<WCClubMissionBean> getNotificationsByClubId(@Param("clubId") long clubId);

    List<WCStudentMissionRelationBean> getNotificationsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getNotificationsBySchoolId(@Param("schoolId") long schoolId);

    List<WCClubMissionBean> getUnConfirmNotificationByClubId(@Param("clubId") long clubId);

    List<WCStudentMissionRelationBean> getUnConfirmNotificationsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getUnConfirmNotificationsBySchoolId(@Param("schoolId") long schoolId);
}
