package com.weclubs.application.meeting;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMeetingParticipationModel;
import com.weclubs.model.WCSponsorMeetingModel;

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

    List<WCStudentMissionRelationBean> getMeetingsByStudentId(long studentId, long clubId);

    List<WCStudentMissionRelationBean> getUnConfirmMeetingByClubId(long studentId);

    List<WCStudentBean> getMeetingLeaderByMeetingId(long meetingId);

    /**
     * 获取会议参与情况
     *
     * @param meetingId 会议id
     * @return  返回当前会议的参与情况
     */
    List<WCMeetingParticipationModel> getMeetingParticipation(long meetingId);

    /**
     * 根据发布者id，获取发布者的会议列表
     *
     * @param sponsorId 发布者id
     *
     * @return  会议列表
     */
    List<WCSponsorMeetingModel> getMeetingBySponsor(long sponsorId);

    /**
     * 根据会议id获取尚未确认的学生关系
     *
     * @param meetingId  会议id
     *
     * @return  尚未确认的学生关系列表
     */
    List<WCStudentMissionRelationBean> getUnConfirmMeetingRelationByMeetingId(long meetingId);

    /**
     * 根据会议id获取所有的学生关系
     *
     * @param meetingId  会议id
     *
     * @return  所有的学生关系列表
     */
    List<WCStudentMissionRelationBean> getMeetingRelationByMeetingId(long meetingId);

    /**
     * 获取已经签到了的学生关系列表
     *
     * @param meetingId 会议id
     *
     * @return  学生关系列表
     */
    List<WCStudentMissionRelationBean> getAlreadySignRelationByMeetingId(long meetingId);
}
