package com.weclubs.mapper;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCSponsorNotifyModel;
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

    List<WCStudentMissionRelationBean> getNotificationsByStudentId(@Param("studentId") long studentId,
                                                                   @Param("clubId") long clubId);

    List<WCClubMissionBean> getNotificationsBySchoolId(@Param("schoolId") long schoolId);

    List<WCClubMissionBean> getUnConfirmNotificationByClubId(@Param("clubId") long clubId);

    List<WCStudentMissionRelationBean> getUnConfirmNotificationsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getUnConfirmNotificationsBySchoolId(@Param("schoolId") long schoolId);

    /**
     * 根据发布者id获取该发布者的所有通知
     *
     * @param sponsorId 发布者id（即学生id）
     *
     * @return  通知列表
     */
    List<WCSponsorNotifyModel> getNotifyBySponsor(@Param("sponsorId") long sponsorId);

    /**
     * 获取该通知的所有学生关系
     *
     * @param notifyId  通知id
     * @return  学生关系列表
     */
    List<WCStudentMissionRelationBean> getRelationByNotifyId(@Param("notifyId") long notifyId);

    /**
     * 获取该通知的所有未确认的学生关系
     *
     * @param notifyId  通知id
     * @return  学生关系列表
     */
    List<WCStudentMissionRelationBean> getUnConfirmRelationByNotifyId(@Param("notifyId") long notifyId);

    /**
     * 批量添加学生对应关系
     *
     * @param relations 学生、通知之间的关系列表
     */
    void createStudentRelation(@Param("relations") List<WCStudentMissionRelationBean> relations);
}
