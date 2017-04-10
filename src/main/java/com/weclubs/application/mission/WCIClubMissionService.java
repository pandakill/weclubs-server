package com.weclubs.application.mission;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMissionBaseModel;

import java.util.List;

/**
 * 任务的服务类接口
 *
 * Created by fangzanpan on 2017/3/16.
 */
public interface WCIClubMissionService {

    void createMission(WCClubMissionBean clubMissionBean);

    void updateMission(WCClubMissionBean clubMissionBean);

    void deleteMission(long missionId);

    void changeMissionStatus(long missionId, int status);

    WCClubMissionBean getMissionDetailById(long missionId);

    List<WCClubMissionBean> getMissionsBySchoolId(long schoolId);

    List<WCClubMissionBean> getMissionsByClubId(long clubId);

    List<WCStudentMissionRelationBean> getMissionsByStudentId(long studentId, long clubId);

    List<WCStudentMissionRelationBean> getUnConfirmMissionByClubId(long studentId);

    List<WCClubMissionBean> getChildMissionByMissionId(long missionId);

    /**
     * 获取任务详情，包括任务发布者、子任务列表等信息
     *
     * @param missionId 任务id
     * @return  任务详情
     */
    WCClubMissionBean getMissionDetailWithChildById(long missionId);

    List<WCStudentBean> getRelatedStudentByMissionId(long missionId);

    List<WCMissionBaseModel> getChildMissionDetailByMissionIdWithStudent(long studentId, long missionId);
}
