package com.weclubs.application.meeting;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMeetingParticipationModel;
import com.weclubs.model.WCSponsorMeetingModel;
import com.weclubs.util.WCHttpStatus;

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

    /**
     * 获取签到的详情（发布者角度的详情）
     *
     * @param meetingId 会议id
     *
     * @return  会议详情
     */
    WCSponsorMeetingModel getSponsorMeetingDetail(long meetingId);

    /**
     * 发布新的会议
     *
     * @param sponsorId 发起者id
     * @param content   会议通知内容
     * @param address   会议举办地儿
     * @param deadline  会议开始时间
     * @param needSign  是否需要签到
     * @param leaders   签到负责人id，例如：1,4
     * @param participation 会议参与人员id，例如：1,2,34,5
     * @param clubId    发布的社团id
     *
     * @return  发布成功则返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus publicMeeting(long sponsorId, String content, String address, long deadline, int needSign,
                               String leaders, String participation, long clubId);

    /**
     * 发布新的会议
     *
     * @param content   会议通知内容
     * @param address   会议举办地儿
     * @param deadline  会议开始时间
     * @param needSign  是否需要签到
     * @param leaders   签到负责人id，例如：1,4
     * @param clubId    发布的社团id
     * @param meetingId 会议id
     *
     * @return  发布成功则返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus editMeeting(String content, String address, long deadline, int needSign,
                               String leaders, long clubId, long meetingId);

    /**
     * 向尚未确认的与会人员发送提醒通知
     *
     * @param meetingId 会议 id
     * @return  发送的状态
     */
    WCHttpStatus remindToUnConfirm(long meetingId);

    /**
     * 撤销会议
     *
     * @param meetingId 会议id
     * @param userId    撤销人id
     * @return  如果撤销成功返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus revertMeeting(long meetingId, long userId);

    /**
     * 结束会议
     *
     * @param meetingId 会议id
     * @param userId    结束会议操作人id
     * @return  如果结束会议成功返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus endMeeting(long meetingId, long userId);
}
