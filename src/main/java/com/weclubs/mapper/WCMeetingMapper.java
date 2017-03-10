package com.weclubs.mapper;

import com.weclubs.bean.WCClubMissionBean;
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

    void deleteMeetingById(@Param("meetingId") long MeetingId);

    void changeMeetingStatus(@Param("meetingId") long MeetingId, int status);

    WCClubMissionBean getMeetingById(@Param("meetingId") long MeetingId);

    List<WCClubMissionBean> getMeetingsByClubId(@Param("clubId") long clubId);

    List<WCClubMissionBean> getMeetingsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getMeetingsBySchoolId(@Param("schoolId") long schoolId);

    List<WCClubMissionBean> getUnConfirmMeetingByClubId(@Param("clubId") long clubId);

    List<WCClubMissionBean> getUnConfirmMeetingsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getUnConfirmMeetingsBySchoolId(@Param("schoolId") long schoolId);

    List<WCClubMissionBean> getUnSignMeetingByClubId(@Param("clubId") long clubId);

    List<WCClubMissionBean> getUnSignMeetingsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getUnSignMeetingsBySchoolId(@Param("schoolId") long schoolId);
}
