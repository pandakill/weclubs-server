package com.weclubs.application.mission;

import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.mapper.WCClubMissionMapper;
import com.weclubs.model.WCMissionBaseModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务的服务类实现
 *
 * Created by fangzanpan on 2017/3/16.
 */
@Service("clubMissionService")
public class WCClubMissionServiceImpl implements WCIClubMissionService {

    private Logger log = Logger.getLogger(WCClubMissionServiceImpl.class);

    @Autowired
    private WCClubMissionMapper mClubMissionMapper;
    @Autowired
    private WCIUserService mUserService;

    public void createMission(WCClubMissionBean clubMissionBean) {
        if (clubMissionBean == null) {
            log.error("createMission：创建任务失败，clubMissionBean不能为空");
            return;
        }

        mClubMissionMapper.createClubMission(clubMissionBean);
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

    public List<WCStudentMissionRelationBean> getMissionsByStudentId(long studentId) {

        if (studentId <= 0) {
            log.error("getMissionsByStudentId：studentId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getClubMissionsByStudentId(studentId);
    }

    public List<WCStudentMissionRelationBean> getUnConfirmMissionByClubId(long studentId) {

        if (studentId <= 0) {
            log.error("getUnConfirmMissionByClubId：studentId不能小于等于0。");
            return null;
        }

        return mClubMissionMapper.getUnConfirmClubMissionsByStudentId(studentId);
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
}
