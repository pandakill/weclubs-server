package com.weclubs.application.mission;

import com.weclubs.application.club.WCIClubGraduateService;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.jiguang_push.WCIJiGuangPushService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.mapper.WCClubMissionMapper;
import com.weclubs.model.WCMissionBaseModel;
import com.weclubs.model.WCSponsorMissionModel;
import com.weclubs.util.Constants;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 任务的服务类实现
 *
 * Created by fangzanpan on 2017/3/16.
 */
@Service("clubMissionService")
class WCClubMissionServiceImpl implements WCIClubMissionService {

    private Logger log = Logger.getLogger(WCClubMissionServiceImpl.class);

    private WCClubMissionMapper mClubMissionMapper;
    private WCIUserService mUserService;
    private WCIClubGraduateService mClubGraudateService;
    private WCIClubService mClubService;
    private WCIJiGuangPushService mJiGuangPushService;

    @Autowired
    public WCClubMissionServiceImpl(WCIUserService mUserService, WCClubMissionMapper mClubMissionMapper,
                                    WCIClubGraduateService clubGraudateService, WCIClubService clubService,
                                    WCIJiGuangPushService jiGuangPushService) {
        this.mUserService = mUserService;
        this.mClubMissionMapper = mClubMissionMapper;
        this.mClubGraudateService = clubGraudateService;
        this.mClubService = clubService;
        this.mJiGuangPushService = jiGuangPushService;
    }

    public void updateMission(WCClubMissionBean clubMissionBean) {

        if (clubMissionBean == null) {
            log.error("updateMission：更新任务详情失败，clubMissionBean不能为空");
            return;
        }

        if (clubMissionBean.getMissionId() <= 0) {
            log.error("updateMission：更新任务详情失败，clubMissionBean.getId 不能小于等于0");
            return;
        }

        mClubMissionMapper.updateClubMission(clubMissionBean);

    }

    public void deleteMission(long missionId) {

        if (missionId <= 0) {
            log.error("deleteMission：删除任务失败，missionId不能小于等于0");
            return;
        }

        mClubMissionMapper.deleteClubMissionById(missionId);
    }

    public void changeMissionStatus(long missionId, int status) {

        if (missionId <= 0) {
            log.error("changeMissionStatus：更新任务状态失败，missionId不能小于等于0。");
            return;
        }

        if (mClubMissionMapper.getClubMissionsByClubId(missionId) == null) {
            log.error("changeMissionStatus：更新任务状态失败，找不到该任务。");
            return;
        }

        mClubMissionMapper.changeClubMissionStatus(missionId, status);
    }

    public WCClubMissionBean getMissionDetailById(long missionId) {

        if (missionId <= 0) {
            log.error("getMissionDetailById：missionId不能小于等于0");
            return null;
        }

        WCClubMissionBean missionBean = mClubMissionMapper.getClubMissionById(missionId);

        if (missionBean == null) {
            log.error("getMissionDetailById：找不到对应的missionBean");
            return null;
        }

        if (missionBean.getSponsorStudentBean() == null && missionBean.getSponsorId() > 0) {
            WCStudentBean sponsor = mUserService.getUserInfoById(missionBean.getSponsorId());
            missionBean.setSponsorStudentBean(sponsor);
        }

        return missionBean;
    }

    public List<WCClubMissionBean> getMissionsBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getMissionsBySchoolId：schoolId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getClubMissionsBySchoolId(schoolId);
    }

    public List<WCClubMissionBean> getMissionsByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getMissionsByClubId：clubId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getClubMissionsByClubId(clubId);
    }

    public List<WCStudentMissionRelationBean> getMissionsByStudentId(long studentId, long clubId) {

        if (studentId <= 0) {
            log.error("getMissionsByStudentId：studentId不能小于等于0。");
            return null;
        }

        if (studentId <= 0) {
            log.error("getMissionsByStudentId：clubId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getClubMissionsByStudentId(studentId, clubId);
    }

    public List<WCStudentMissionRelationBean> getUnConfirmMissionByMissionId(long missionId) {

        if (missionId <= 0) {
            log.error("getUnConfirmMissionByClubId：missionId 不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getUnConfirmClubMissionsByStudentId(missionId);
    }

    public List<WCClubMissionBean> getChildMissionByMissionId(long missionId) {

        if (missionId <= 0) {
            log.error("getChildMissionByMissionId：missionId不能小于等于0");
        }

        return mClubMissionMapper.getChildMissionsByMissionId(missionId);
    }

    public WCClubMissionBean getMissionDetailWithChildById(long missionId) {

        if (missionId <= 0) {
            log.error("getMissionDetailById：missionId不能小于等于0。");
        }

        WCClubMissionBean result = mClubMissionMapper.getClubMissionById(missionId);
        if (result != null && result.getMissionId() > 0 && result.getIsDel() != 1) {
            result.setChildMissions(getChildMissionByMissionId(missionId));

            if (result.getSponsorStudentBean() == null && result.getSponsorId()  > 0) {
                WCStudentBean sponsor = mUserService.getUserInfoById(result.getSponsorId());
                result.setSponsorStudentBean(sponsor);
            }
        }

        return result;
    }

    public List<WCStudentBean> getRelatedStudentByMissionId(long missionId) {

        if (missionId <= 0) {
            log.error("getRelatedStudentByMissionId：missionId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getClubMissionParticipantByMissionId(missionId);
    }

    public List<WCMissionBaseModel> getChildMissionDetailByMissionIdWithStudent(long studentId, long missionId) {

        if (missionId <= 0) {
            log.error("getChildMissionDetailByMissionIdWithStudent：missionId不能小于等于0。");
            return null;
        }

        if (studentId <= 0) {
            log.error("getChildMissionDetailByMissionIdWithStudent：studentId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getChildMissionDetailListByMissionIdWithStudent(studentId, missionId);
    }

    public List<WCSponsorMissionModel> getMissionBySponsorId(long sponsorId) {

        if (sponsorId <= 0) {
            log.error("getMissionBySponsorId：sponsorId 不能小于等于0");
            return null;
        }

        return mClubMissionMapper.getMissionsBySponsorId(sponsorId);
    }

    public WCHttpStatus publicMission(HashMap<String, Object> requestData) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        String content = (String) requestData.get("content");
        long clubId = WCCommonUtil.getLongData(requestData.get("club_id"));
        long sponsorId = WCCommonUtil.getLongData(requestData.get("sponsor_id"));
        long deadline = WCCommonUtil.getLongData(requestData.get("deadline"));
        long parentId = 0;

        if (requestData.containsKey("parent_id")) {
            parentId = WCCommonUtil.getLongData(requestData.get("parent_id"));
        }

        if (StringUtils.isEmpty(content)) {
            check.msg = "任务描述内容不能为空";
            log.error("publicMission：" + check.msg);
            return check;
        }

        if (clubId <= 0) {
            check.msg = "club_id 不能小于等于0";
            log.error("publicMission：" + check.msg);
            return check;
        }

        WCClubGraduateBean clubGraduateBean = mClubGraudateService.getCurrentClubGraduate(clubId);
        if (clubGraduateBean == null) {
            check.msg = "找不到该社团";
            log.error("publicMission：" + check.msg);
            return check;
        }

        if (deadline <= 0 || String.valueOf(deadline).length() != 13) {
            check.msg = "任务截止时间格式不对";
            log.error("publicMission：" + check.msg);
            return check;
        }

        WCClubMissionBean missionBean = new WCClubMissionBean();
        missionBean.setAttribution(content);
        missionBean.setDeadline(deadline);
        missionBean.setClubId(clubId);
        missionBean.setCreateDate(System.currentTimeMillis());
        missionBean.setType(WCClubMissionBean.TYPE_MISSION);
        missionBean.setSponsorId(sponsorId);
        missionBean.setParentId(parentId);
        missionBean.setGraduateId(clubGraduateBean.getClubGraduateId());

        List<WCClubMissionBean> missionList1 = new ArrayList<WCClubMissionBean>();
        missionList1.add(missionBean);

        List<HashMap<String, Object>> childHash = null;
        String participation = null;
        if (requestData.containsKey("child")) { // 直接填任务项的
            childHash = (List<HashMap<String, Object>>) requestData.get("child");
            if (childHash == null || childHash.size() == 0) {
                check.msg = "任务项不能为空";
                log.error("publicMission：" + check.msg);
                return check;
            }

            for (HashMap<String, Object> result : childHash) {
                String childContent = (String) result.get("content");
                String childParticipation = (String) result.get("participation");

                if (StringUtils.isEmpty(childContent)) {
                    check.msg = "任务项描述内容不能为空";
                    log.error("publicMission：" + check.msg);
                    return check;
                }

                if (StringUtils.isEmpty(childParticipation)) {
                    check.msg = "任务项参与者不能为空";
                    log.error("publicMission：" + check.msg);
                    return check;
                }

                String[] ids = childParticipation.split(",");

                if (ids.length <= 0) {
                    check.msg = "任务参与者不能为空";
                    log.error("publicMission：" + check.msg);
                    return check;
                }
            }

            mClubMissionMapper.createClubMission(missionList1);

            for (HashMap<String, Object> result : childHash) {
                String childContent = (String) result.get("content");
                String childParticipation = (String) result.get("participation");

                WCClubMissionBean childMission = new WCClubMissionBean();
                childMission.setParentId(missionList1.get(0).getMissionId());
                childMission.setAttribution(childContent);
                childMission.setSponsorId(sponsorId);
                childMission.setClubId(clubId);
                childMission.setCreateDate(missionList1.get(0).getCreateDate());
                childMission.setDeadline(missionList1.get(0).getDeadline());
                childMission.setGraduateId(clubGraduateBean.getClubGraduateId());

                List<WCClubMissionBean> childMissionList = new ArrayList<WCClubMissionBean>();
                childMissionList.add(childMission);

                String[] ids = childParticipation.split(",");

                mClubMissionMapper.createClubMission(childMissionList);

                List<WCStudentMissionRelationBean> relationBeanList = new ArrayList<WCStudentMissionRelationBean>();
                for (String id : ids) {
                    WCStudentMissionRelationBean relationBean = new WCStudentMissionRelationBean();
                    relationBean.setStatus(0);
                    relationBean.setStudentId(Long.parseLong(id));
                    relationBean.setMissionId(childMissionList.get(0).getMissionId());
                    relationBean.setCreateDate(childMissionList.get(0).getCreateDate());

                    relationBeanList.add(relationBean);
                }

                mClubMissionMapper.createStudentRelation(relationBeanList);
            }

            check = WCHttpStatus.SUCCESS;


        } else if (requestData.containsKey("participation")){   // 直接填任务参与者的
            participation = (String) requestData.get("participation");

            if (StringUtils.isEmpty(participation)) {
                check.msg = "任务参与者不能为空";
                log.error("publicMission：" + check.msg);
                return check;
            }

            String[] ids = participation.split(",");

            if (ids.length <= 0) {
                check.msg = "任务参与者不能为空";
                log.error("publicMission：" + check.msg);
                return check;
            }

            mClubMissionMapper.createClubMission(missionList1);

            List<WCStudentMissionRelationBean> relationBeanList = new ArrayList<WCStudentMissionRelationBean>();
            for (String id : ids) {
                WCStudentMissionRelationBean relationBean = new WCStudentMissionRelationBean();
                relationBean.setStatus(0);
                relationBean.setStudentId(Long.parseLong(id));
                relationBean.setMissionId(missionList1.get(0).getMissionId());
                relationBean.setCreateDate(missionList1.get(0).getCreateDate());

                relationBeanList.add(relationBean);
            }

            mClubMissionMapper.createStudentRelation(relationBeanList);

            long[] receiveIds = new long[relationBeanList.size()];
            for (int i = 0; i < relationBeanList.size(); i++) {
                receiveIds[i] = relationBeanList.get(i).getStudentId();
            }
            WCClubBean clubBean = mClubService.getClubInfoById(clubId);
            mJiGuangPushService.pushNewMissionCreate(clubBean, content, missionBean.getMissionId(), receiveIds);

            check = WCHttpStatus.SUCCESS;
        }

        return check;
    }

    public List<WCStudentMissionRelationBean> getMissionRelationsByMissionId(long missionId) {

        if (missionId == 0) {
            log.error("getMissionRelationsByMissionId：missionId 不能小于等于0");
            return null;
        }

        return mClubMissionMapper.getMissionRelationsByMissionId(missionId);
    }

    public WCHttpStatus remindToUnConfirm(long missionId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (missionId <= 0) {
            check.msg = "mission_id 不能小于等于0";
            log.error("remindToUnConfirm：" + check.msg);
            return check;
        }

        WCClubMissionBean meetingBean = getMissionDetailById(missionId);
        if (meetingBean == null) {
            log.warn("remindToUnConfirm：找不到对应的任务内容");
            check.msg = "找不到该任务，请检查后重新操作";
            return check;
        }

        WCClubBean clubBean = mClubService.getClubInfoById(meetingBean.getClubId());
        if (clubBean == null) {
            log.warn("remindToUnConfirm：找不到对应的社团");
            check = WCHttpStatus.FAIL_APPLICATION_ERROR;
            return check;
        }

        List<WCStudentMissionRelationBean> unConfirmStudent =
                mClubMissionMapper.getUnConfirmClubMissionsByMissionId(meetingBean.getMissionId());

        if (unConfirmStudent == null || unConfirmStudent.size() == 0) {
            log.warn("remindToUnConfirm：该任务已经全部确认完毕");
            check.msg = "该任务已经全部确认完毕";
            return check;
        }

        long[] userIds = new long[unConfirmStudent.size()];
        for (int i = 0; i < unConfirmStudent.size(); i++) {
            userIds[i] = unConfirmStudent.get(i).getStudentId();
        }

        mJiGuangPushService.pushUnConfirmDynamic(clubBean.getName(),
                Constants.TODO_MISSION, meetingBean.getAttribution(), missionId, userIds);

        check = WCHttpStatus.SUCCESS;

        return check;
    }

    public WCHttpStatus remindToUnFinish(long missionId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (missionId <= 0) {
            check.msg = "mission_id 不能小于等于0";
            log.error("remindToUnConfirm：" + check.msg);
            return check;
        }

        WCClubMissionBean meetingBean = getMissionDetailById(missionId);
        if (meetingBean == null) {
            log.warn("remindToUnConfirm：找不到对应的任务内容");
            check.msg = "找不到该任务，请检查后重新操作";
            return check;
        }

        WCClubBean clubBean = mClubService.getClubInfoById(meetingBean.getClubId());
        if (clubBean == null) {
            log.warn("remindToUnConfirm：找不到对应的社团");
            check = WCHttpStatus.FAIL_APPLICATION_ERROR;
            return check;
        }

        List<WCStudentMissionRelationBean> unConfirmStudent =
                mClubMissionMapper.getUnFinishClubMissionsByMissionId(meetingBean.getMissionId());

        if (unConfirmStudent == null || unConfirmStudent.size() == 0) {
            log.warn("remindToUnConfirm：该任务已经全部完成");
            check.msg = "该任务已经全部完成";
            return check;
        }

        long[] userIds = new long[unConfirmStudent.size()];
        for (int i = 0; i < unConfirmStudent.size(); i++) {
            userIds[i] = unConfirmStudent.get(i).getStudentId();
        }

        mJiGuangPushService.pushUnFinishMission(clubBean.getName(), meetingBean.getAttribution(), missionId, userIds);

        check = WCHttpStatus.SUCCESS;

        return check;
    }

    @Override
    public WCHttpStatus revertMission(long missionId, long userId) {
        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (missionId <= 0) {
            check.msg = "mission_id 不能小于等于0";
            log.error("revertMission：" + check.msg);
            return check;
        }

        WCClubMissionBean missionBean = getMissionDetailById(missionId);
        if (missionBean == null) {
            log.warn("revertMission：找不到对应的任务内容");
            check.msg = "找不到该任务，请检查后重新操作";
            return check;
        }

        // 只有任务发起人有权力撤销会议
        if (missionBean.getSponsorId() != userId) {
            check.msg = "您没有权限撤销该任务";
            return check;
        }

        missionBean.setIsDel(1);
        mClubMissionMapper.updateClubMission(missionBean);

        check = WCHttpStatus.SUCCESS;

        return check;
    }
}
