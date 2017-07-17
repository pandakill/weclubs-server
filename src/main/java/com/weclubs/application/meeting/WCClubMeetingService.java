package com.weclubs.application.meeting;

import com.weclubs.application.club.WCIClubGraduateService;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.jiguang_push.WCIJiGuangPushService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.mapper.WCMeetingMapper;
import com.weclubs.model.WCMeetingParticipationModel;
import com.weclubs.model.WCSponsorMeetingModel;
import com.weclubs.util.Constants;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议服务的实现类
 *
 * Created by fangzanpan on 2017/3/9.
 */
@Service("clubMeetingService")
class WCClubMeetingService implements WCIClubMeetingService {

    private Logger log = Logger.getLogger(WCClubMeetingService.class);

    private WCMeetingMapper mMeetingMapper;
    private WCIUserService mUserService;
    private WCIClubGraduateService mClubGraduateService;
    private WCIJiGuangPushService mJiGuangPushService;
    private WCIClubService mClubService;

    @Autowired
    public WCClubMeetingService(WCIUserService mUserService, WCMeetingMapper mMeetingMapper,
                                WCIClubGraduateService graduateService, WCIJiGuangPushService jiGuangPushService,
                                WCIClubService clubService) {
        this.mUserService = mUserService;
        this.mMeetingMapper = mMeetingMapper;
        this.mClubGraduateService = graduateService;
        this.mJiGuangPushService = jiGuangPushService;
        this.mClubService = clubService;
    }

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

        if (clubMissionBean.getMissionId() <= 0) {
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

        if (meeting.getSponsorStudentBean() == null && meeting.getSponsorId() > 0) {
            WCStudentBean sponsor = mUserService.getUserInfoById(meeting.getSponsorId());
            meeting.setSponsorStudentBean(sponsor);
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

    public List<WCStudentMissionRelationBean> getMeetingsByStudentId(long studentId, long clubId) {

        if (studentId <= 0) {
            log.error("getMeetingsByStudentId：查找学生所有会议失败，studentId不能小于等于0。");
            return null;
        }

        if (clubId <= 0) {
            log.error("getMeetingsByStudentId：查找学生所有会议失败，clubId不能小于等于0。");
            return null;
        }

        return mMeetingMapper.getMeetingsByStudentId(studentId, clubId);
    }

    public List<WCStudentMissionRelationBean> getUnConfirmMeetingByClubId(long studentId) {

        if (studentId <= 0) {
            log.error("getUnConfirmMeetingByClubId：查找学生所有未确认会议失败，studentId不能小于等于0。");
            return null;
        }

        return mMeetingMapper.getUnConfirmMeetingsByStudentId(studentId);
    }

    public List<WCStudentBean> getMeetingLeaderByMeetingId(long meetingId) {

        if (meetingId <= 0) {
            log.error("getMeetingLeaderByMeetingId：查找会议详情失败，meetingId不能小于等于0");
            return null;
        }

        if (mMeetingMapper.getMeetingById(meetingId) == null) {
            log.error("getMeetingLeaderByMeetingId：找不到 meetingId = " + meetingId + "的会议" );
            return null;
        }

        List<WCStudentMissionRelationBean> leaderRelations = mMeetingMapper.getMeetingLeader(meetingId);
        List<WCStudentBean> leaders = new ArrayList<WCStudentBean>();
        if (leaderRelations != null && leaderRelations.size() > 0) {
            for (WCStudentMissionRelationBean leaderRelation : leaderRelations) {
                WCStudentBean studentBean = mUserService.getUserInfoById(leaderRelation.getStudentId());
                leaders.add(studentBean);
            }
        }

        return leaders;
    }

    public List<WCMeetingParticipationModel> getMeetingParticipation(long meetingId) {

        if (meetingId <= 0) {
            log.error("getMeetingParticipation：meetingId不能小于等于0");
            return null;
        }

        return mMeetingMapper.getMeetingParticipation(meetingId);
    }

    public List<WCSponsorMeetingModel> getMeetingBySponsor(long sponsorId) {

        if (sponsorId <= 0) {
            log.error("getNotifyBySponsor：sponsorId 不能小于等于0。");
            return null;
        }

        List<WCSponsorMeetingModel> meetingModelList = mMeetingMapper.getMeetingBySponsor(sponsorId);
        if (meetingModelList != null && meetingModelList.size() > 0) {
            for (WCSponsorMeetingModel meetingModel : meetingModelList) {
                List<WCStudentMissionRelationBean> total = getMeetingRelationByMeetingId(meetingModel.getMissionId());
                List<WCStudentMissionRelationBean> unConfirm = getUnConfirmMeetingRelationByMeetingId(meetingModel.getMissionId());
                List<WCStudentMissionRelationBean> alreadySign = getAlreadySignRelationByMeetingId(meetingModel.getMissionId());

                meetingModel.setTotalCount(total == null ? 0 : total.size());
                meetingModel.setUnConfirmCount(unConfirm == null ? 0 : unConfirm.size());
                meetingModel.setSignCount(alreadySign == null ? 0 : alreadySign.size());
            }
        }

        return meetingModelList;
    }

    public List<WCStudentMissionRelationBean> getUnConfirmMeetingRelationByMeetingId(long meetingId) {

        if (meetingId <= 0) {
            log.error("getUnConfirmMeetingRelationByMeetingId：meetingId 不能小于等于0");
            return null;
        }

        return mMeetingMapper.getUnConfirmMeetingRelationByMeetingId(meetingId);
    }

    public List<WCStudentMissionRelationBean> getMeetingRelationByMeetingId(long meetingId) {

        if (meetingId <= 0) {
            log.error("getMeetingRelationByMeetingId：meetingId 不能小于等于0");
            return null;
        }

        return mMeetingMapper.getMeetingRelationByMeetingId(meetingId);
    }

    public List<WCStudentMissionRelationBean> getAlreadySignRelationByMeetingId(long meetingId) {

        if (meetingId <= 0) {
            log.error("getAlreadySignRelationByMeetingId：meetingId 不能小于等于0");
            return null;
        }

        return mMeetingMapper.getSignRelationByMeetingId(meetingId);
    }

    public WCSponsorMeetingModel getSponsorMeetingDetail(long meetingId) {

        if (meetingId <= 0) {
            log.error("getSponsorMeetingDetail：meetingId 不能小于等于0");
            return null;
        }

        WCClubMissionBean missionBean = getMeetingDetailById(meetingId);
        if (missionBean == null) {
            log.error("getSponsorMeetingDetail：找不到meetingId = 【" + meetingId + "】的会议");
            return null;
        }

        WCSponsorMeetingModel meetingModel = new WCSponsorMeetingModel(missionBean);

        List<WCStudentMissionRelationBean> total = getMeetingRelationByMeetingId(meetingModel.getMissionId());
        List<WCStudentMissionRelationBean> unConfirm = getUnConfirmMeetingRelationByMeetingId(meetingModel.getMissionId());
        List<WCStudentMissionRelationBean> alreadySign = getAlreadySignRelationByMeetingId(meetingModel.getMissionId());

        meetingModel.setTotalCount(total == null ? 0 : total.size());
        meetingModel.setUnConfirmCount(unConfirm == null ? 0 : unConfirm.size());
        meetingModel.setSignCount(alreadySign == null ? 0 : alreadySign.size());

        return meetingModel;
    }

    public WCHttpStatus publicMeeting(long sponsorId, String content, String address, long deadline, int needSign,
                                      String leaders, String participation, long clubId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (sponsorId <= 0) {
            check.msg = "sponsor_id 不能小于等于0";
            log.error("publicMeeting：" + check.msg);
            return check;
        }

        if (StringUtils.isEmpty(content)) {
            check.msg = "会议简介内容不能为空";
            log.error("publicMeeting：" + check.msg);
            return check;
        }

        if (StringUtils.isEmpty(address)) {
            check.msg = "会议举办地点不能为空";
            log.error("publicMeeting：" + check.msg);
            return check;
        }

        if (clubId <= 0) {
            check.msg = "clubId 不能小于等于0";
            log.error("publicMeeting：" + check.msg);
            return check;
        }

        if (deadline <= 0 || String.valueOf(deadline).length() != 13) {
            check.msg = "会议举办时间格式不对";
            log.error("publicMeeting：" + check.msg);
            return check;
        }

        String[] leadersId = null;
        if (!StringUtils.isEmpty(leaders)) {
            leadersId = leaders.split(",");
        }

        if (needSign == 1 && (leadersId == null || leadersId.length <= 0)) {
            check.msg = "签到负责人不能为空";
            log.error("publicMeeting：" + check.msg);
            return check;
        } else if (needSign == 1 && leadersId.length >= 5) {
            check.msg = "签到负责人只能小于等于5位";
            log.error("publicMeeting：" + check.msg + ";当前签到负责人为：" + leaders);
            return check;
        }

        String[] participationId = null;
        if (!StringUtils.isEmpty(participation)) {
            participationId = participation.split(",");
        }

        if (participationId == null || participationId.length <= 0) {
            check.msg = "会议参与人员不能为空";
            log.error("publicMeeting：" + check.msg);
            return check;
        }

        WCClubGraduateBean graduateBean = mClubGraduateService.getCurrentClubGraduate(clubId);
        if (graduateBean == null) {
            check.msg = "找不到该社团";
            log.error("publicMeeting：" + check.msg + "-【" + clubId + "】");
            return check;
        }

        WCClubMissionBean meeting = new WCClubMissionBean();
        meeting.setClubId(clubId);
        meeting.setGraduateId(graduateBean.getClubGraduateId());
        meeting.setSponsorId(sponsorId);
        meeting.setDeadline(deadline);
        meeting.setSignType(needSign == 0 ? 0 : 1);
        meeting.setCreateDate(System.currentTimeMillis());
        meeting.setAddress(address);
        meeting.setAttribution(content);
        meeting.setIsDel(0);
        meeting.setType(2);

        mMeetingMapper.createMeeting(meeting);

        List<WCStudentMissionRelationBean> relationBeanList = new ArrayList<WCStudentMissionRelationBean>();
        for (String s : participationId) {
            WCStudentMissionRelationBean relationBean = new WCStudentMissionRelationBean();

            relationBean.setStudentId(WCCommonUtil.getLongData(s));
            relationBean.setCreateDate(meeting.getCreateDate());
            relationBean.setMissionId(meeting.getMissionId());

            relationBean.setIsLeader(0);
            if (needSign == 1) {
                for (String leader : leadersId) {
                    if (s.equals(leader)) {
                        relationBean.setIsLeader(1);
                        break;
                    }
                }
            }

            relationBean.setStatus(0);
            relationBean.setIsSign(0);
            relationBean.setIsDel(0);

            relationBeanList.add(relationBean);
        }

        mMeetingMapper.createStudentRelation(relationBeanList);

        long[] participationArray = new long[relationBeanList.size()];
        for (int i = 0; i < relationBeanList.size(); i++) {
            participationArray[i] = relationBeanList.get(i).getStudentId();
        }

        WCClubBean clubBean = mClubService.getClubInfoById(clubId);
        mJiGuangPushService.pushNewMeetingCreate(deadline, meeting.getAddress(), clubBean,
                meeting.getAttribution(), meeting.getMissionId(), participationArray);

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    public WCHttpStatus editMeeting(String content, String address, long deadline, int needSign,
                                    String leaders, long clubId, long meetingId) {


        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (meetingId <= 0) {
            check.msg = "meeting_id 不能小于等于0";
            log.error("editMeeting：" + check.msg);
            return check;
        }

        if (StringUtils.isEmpty(content)) {
            check.msg = "会议简介内容不能为空";
            log.error("editMeeting：" + check.msg);
            return check;
        }

        if (StringUtils.isEmpty(address)) {
            check.msg = "会议举办地点不能为空";
            log.error("editMeeting：" + check.msg);
            return check;
        }

        if (clubId <= 0) {
            check.msg = "clubId 不能小于等于0";
            log.error("editMeeting：" + check.msg);
            return check;
        }

        if (deadline <= 0 || String.valueOf(deadline).length() != 13) {
            check.msg = "会议举办时间格式不对";
            log.error("editMeeting：" + check.msg);
            return check;
        }

        String[] leadersId = null;
        if (!StringUtils.isEmpty(leaders)) {
            leadersId = leaders.split(",");
        }

        if (needSign == 1 && (leadersId == null || leadersId.length <= 0)) {
            check.msg = "签到负责人不能为空";
            log.error("editMeeting：" + check.msg);
            return check;
        } else if (needSign == 1 && leadersId.length >= 5) {
            check.msg = "签到负责人只能小于等于5位";
            log.error("editMeeting：" + check.msg + ";当前签到负责人为：" + leaders);
            return check;
        }

        WCClubGraduateBean graduateBean = mClubGraduateService.getCurrentClubGraduate(clubId);
        if (graduateBean == null) {
            check.msg = "找不到该社团";
            log.error("editMeeting：" + check.msg + "-【" + clubId + "】");
            return check;
        }

        WCClubMissionBean meeting = getMeetingDetailById(meetingId);
        if (meeting == null) {
            check.msg = "找不到该会议【" + meetingId + "】";
            log.error("editMeeting：" + check.msg);
            return check;
        }

        meeting.setAttribution(content);
        meeting.setAddress(address);
        meeting.setDeadline(deadline);
        meeting.setSignType(needSign == 0 ? 0 : 1);
        meeting.setClubId(clubId);

        mMeetingMapper.updateMeeting(meeting);

        List<WCStudentMissionRelationBean> relationBeanList = mMeetingMapper.getMeetingRelationByMeetingId(meetingId);

        for (WCStudentMissionRelationBean relationBean : relationBeanList) {
            relationBean.setIsLeader(0);
        }

        if (needSign == 1) {
            for (WCStudentMissionRelationBean relationBean : relationBeanList) {
                for (String id : leadersId) {
                    if (relationBean.getStudentId() == WCCommonUtil.getLongData(id)) {
                        relationBean.setIsLeader(1);
                        break;
                    }
                }
                mMeetingMapper.updateStudentRelation(relationBean);
            }
        }

        check = WCHttpStatus.SUCCESS;

        return check;
    }

    public WCHttpStatus remindToUnConfirm(long meetingId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (meetingId <= 0) {
            check.msg = "meeting_id 不能小于等于0";
            log.error("remindToUnConfirm：" + check.msg);
            return check;
        }

        WCClubMissionBean meetingBean = getMeetingDetailById(meetingId);
        if (meetingBean == null) {
            log.warn("remindToUnConfirm：找不到对应的会议内容");
            check.msg = "找不到该会议，请检查后重新操作";
            return check;
        }

        WCClubBean clubBean = mClubService.getClubInfoById(meetingBean.getClubId());
        if (clubBean == null) {
            log.warn("remindToUnConfirm：找不到对应的社团");
            check = WCHttpStatus.FAIL_APPLICATION_ERROR;
            return check;
        }

        List<WCStudentMissionRelationBean> unConfirmStudent = getUnConfirmMeetingRelationByMeetingId(meetingBean.getMissionId());
        if (unConfirmStudent == null || unConfirmStudent.size() == 0) {
            log.warn("remindToUnConfirm：该会议已经全部确认完毕");
            check.msg = "该会议已经全部确认完毕";
            return check;
        }

        long[] userIds = new long[unConfirmStudent.size()];
        for (int i = 0; i < unConfirmStudent.size(); i++) {
            userIds[i] = unConfirmStudent.get(i).getStudentId();
        }

        mJiGuangPushService.pushUnConfirmDynamic(clubBean.getName(),
                Constants.TODO_MEETING, meetingBean.getAttribution(), meetingId, userIds);

        check = WCHttpStatus.SUCCESS;

        return check;
    }

    @Override
    public WCHttpStatus revertMeeting(long meetingId, long userId) {
        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (meetingId <= 0) {
            check.msg = "meeting_id 不能小于等于0";
            log.error("revertMeeting：" + check.msg);
            return check;
        }

        WCClubMissionBean meetingBean = getMeetingDetailById(meetingId);
        if (meetingBean == null) {
            log.warn("revertMeeting：找不到对应的会议内容");
            check.msg = "找不到该会议，请检查后重新操作";
            return check;
        }

        // 只有会议发起人有权力撤销会议
        if (meetingBean.getSponsorId() != userId) {
            check.msg = "您没有权限撤销该会议";
            return check;
        }

        meetingBean.setIsDel(1);
        mMeetingMapper.updateMeeting(meetingBean);

        check = WCHttpStatus.SUCCESS;

        return check;
    }

    @Override
    public WCHttpStatus endMeeting(long meetingId, long userId) {
        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (meetingId <= 0) {
            check.msg = "meeting_id 不能小于等于0";
            log.error("revertMeeting：" + check.msg);
            return check;
        }

        WCClubMissionBean meetingBean = getMeetingDetailById(meetingId);
        if (meetingBean == null) {
            log.warn("revertMeeting：找不到对应的会议内容");
            check.msg = "找不到该会议，请检查后重新操作";
            return check;
        }

        boolean canRevert = false;

        if (meetingBean.getSponsorId() == userId) {
            canRevert = true;
        }

        if (meetingBean.getSignType() == 1) {   // 如果是需要签到的，则签到负责人也有权力结束会议
            List<WCStudentBean> leader = getMeetingLeaderByMeetingId(meetingId);

            if (leader != null) {
                for (WCStudentBean studentBean : leader) {
                    if (studentBean.getStudentId() == userId) {
                        canRevert = true;
                        break;
                    }
                }
            }
        }


        if (!canRevert) {
            check.msg = "您没有权限撤销该会议";
            return check;
        }

        meetingBean.setIsDel(2);
        mMeetingMapper.updateMeeting(meetingBean);

        check = WCHttpStatus.SUCCESS;
        return check;
    }
}
