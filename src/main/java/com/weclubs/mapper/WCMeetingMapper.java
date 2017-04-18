package com.weclubs.mapper;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMeetingParticipationModel;
import com.weclubs.model.WCSponsorMeetingModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_mission 会议的映射接口
 * t_club_mission.type = 2
 *
 * Created by fangzanpan on 2017/3/9.
 */
public interface WCMeetingMapper {

    void createMeeting(WCClubMissionBean missionBean);

    void updateMeeting(WCClubMissionBean missionBean);

    void deleteMeetingById(@Param("meetingId") long meetingId);

    void changeMeetingStatus(@Param("meetingId") long meetingId, int status);

    WCClubMissionBean getMeetingById(@Param("meetingId") long neetingId);

    List<WCClubMissionBean> getMeetingsByClubId(@Param("clubId") long clubId);

    List<WCStudentMissionRelationBean> getMeetingsByStudentId(@Param("studentId") long studentId,
                                                              @Param("clubId") long clubId);

    List<WCClubMissionBean> getMeetingsBySchoolId(@Param("schoolId") long schoolId);

    List<WCClubMissionBean> getUnConfirmMeetingByClubId(@Param("clubId") long clubId);

    List<WCStudentMissionRelationBean> getUnConfirmMeetingsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getUnConfirmMeetingsBySchoolId(@Param("schoolId") long schoolId);

    List<WCClubMissionBean> getUnSignMeetingByClubId(@Param("clubId") long clubId);

    List<WCClubMissionBean> getUnSignMeetingsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getUnSignMeetingsBySchoolId(@Param("schoolId") long schoolId);

    List<WCStudentMissionRelationBean> getMeetingLeader(@Param("meetingId") long meetingId);

    List<WCMeetingParticipationModel> getMeetingParticipation(@Param("meetingId") long meetingId);

    /**
     * 获取发布者的会议列表
     *
     * @param sponsorId 发布者id
     * @return  会议列表
     */
    List<WCSponsorMeetingModel> getMeetingBySponsor(@Param("sponsorId") long sponsorId);

    /**
     * 获取未确认的会议列表关系
     *
     * @param meetingId 会议id
     *
     * @return  未确认的会议列表关系
     */
    List<WCStudentMissionRelationBean> getUnConfirmMeetingRelationByMeetingId(@Param("meetingId") long meetingId);

    /**
     * 获取某个会议所有的会议列表关系
     *
     * @param meetingId 会议Id
     *
     * @return  所有的会议列表关系
     */
    List<WCStudentMissionRelationBean> getMeetingRelationByMeetingId(@Param("meetingId") long meetingId);

    /**
     * 获取某个会议已经签到的会议关系列表
     *
     * @param meetingId 会议id
     *
     * @return  已经签到的会议关系列表
     */
    List<WCStudentMissionRelationBean> getSignRelationByMeetingId(@Param("meetingId") long meetingId);
}
