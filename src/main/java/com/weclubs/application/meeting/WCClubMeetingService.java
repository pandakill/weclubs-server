package com.weclubs.application.meeting;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.mapper.WCMeetingMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会议服务的实现类
 *
 * Created by fangzanpan on 2017/3/9.
 */
@Service("clubMeetingService")
public class WCClubMeetingService implements WCIClubMeetingService {

    private Logger log = Logger.getLogger(WCClubMeetingService.class);

    @Autowired
    private WCMeetingMapper mMeetingMapper;

    public void createMeeting(WCClubMissionBean meetingBean) {

        if (meetingBean == null) {
            log.error("createMeeting：创建会议失败，meetingBean不能为空。");
            return;
        }

        mMeetingMapper.createMeeting(meetingBean);
    }

    public void updateMeeting(WCClubMissionBean clubMissionBean) {

        if (clubMissionBean == null) {
            log.error("updateMeeting：更新会议失败，meetingBean不能为空。");
            return;
        }

        if (clubMissionBean.getId() <= 0) {
            log.error("updateMeeting：更新会议失败，meetingBean.getId不能小于等于0。");
            return;
        }

        mMeetingMapper.updateMeeting(clubMissionBean);
    }

    public void deleteMeeting(long meetingId) {

        if (meetingId <= 0) {
            log.error("deleteMeeting：删除会议失败，meetingId不能小于等于0。");
            return;
        }

        mMeetingMapper.deleteMeetingById(meetingId);
    }

    public void changeMeetingStatus(long meetingId, int status) {


        if (meetingId <= 0) {
            log.error("changeMeetingStatus：更新会议状态失败，meetingId不能小于等于0。");
            return;
        }

        if (mMeetingMapper.getMeetingById(meetingId) == null) {
            log.error("changeMeetingStatus：更新会议状态失败，找不到该会议。");
            return;
        }

        mMeetingMapper.changeMeetingStatus(meetingId, status);
    }

    public WCClubMissionBean getMeetingDetailById(long meetingId) {

        if (meetingId <= 0) {
            log.error("getMeetingDetailById：查找会议详情失败，meetingId不能小于等于0。");
            return null;
        }

        WCClubMissionBean meeting = mMeetingMapper.getMeetingById(meetingId);

        if (meeting == null) {
            log.error("getMeetingDetailById：找不到 meetingId = " + meetingId + "的会议。" );
            return null;
        }

        return meeting;
    }

    public List<WCClubMissionBean> getMeetingsBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getMeetingsBySchoolId：查找学校所有会议失败，schoolId不能小于等于0。");
            return null;
        }

        return mMeetingMapper.getMeetingsBySchoolId(schoolId);
    }

    public List<WCClubMissionBean> getMeetingsByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getMeetingsByClubId：查找社团所有会议失败，clubId不能小于等于0。");
            return null;
        }

        return mMeetingMapper.getMeetingsByClubId(clubId);
    }

    public List<WCClubMissionBean> getMeetingsByStudentId(long studentId) {

        if (studentId <= 0) {
            log.error("getMeetingsByStudentId：查找学生所有会议失败，studentId不能小于等于0。");
            return null;
        }

        return mMeetingMapper.getMeetingsByStudentId(studentId);
    }

    public List<WCClubMissionBean> getUnConfirmMeetingByClubId(long studentId) {

        if (studentId <= 0) {
            log.error("getUnConfirmMeetingByClubId：查找学生所有未确认会议失败，studentId不能小于等于0。");
            return null;
        }

        return mMeetingMapper.getUnConfirmMeetingsByStudentId(studentId);
    }
}