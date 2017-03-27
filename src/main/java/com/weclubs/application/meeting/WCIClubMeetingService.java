package com.weclubs.application.meeting;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;

import java.util.List;

/**
 * 会议服务的接口类
 *
 * Created by fangzanpan on 2017/3/9.
 */
public interface WCIClubMeetingService {

    void createMeeting(WCClubMissionBean clubMissionBean);

    void updateMeeting(WCClubMissionBean clubMissionBean);

    void deleteMeeting(long meetingId);

    void changeMeetingStatus(long meetingId, int status);

    WCClubMissionBean getMeetingDetailById(long meetingId);

    List<WCClubMissionBean> getMeetingsBySchoolId(long schoolId);

    List<WCClubMissionBean> getMeetingsByClubId(long clubId);

    List<WCStudentMissionRelationBean> getMeetingsByStudentId(long studentId);

    List<WCStudentMissionRelationBean> getUnConfirmMeetingByClubId(long studentId);

    List<WCStudentBean> getMeetingLeaderByMeetingId(long meetingId);
}
