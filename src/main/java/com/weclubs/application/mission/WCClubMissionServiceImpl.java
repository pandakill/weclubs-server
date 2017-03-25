package com.weclubs.application.mission;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.mapper.WCClubMissionMapper;
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
            log.error("getMissionDetailById：missionId不能小于等于0。");
        }

        return mClubMissionMapper.getClubMissionById(missionId);
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
}
