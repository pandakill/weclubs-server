package com.weclubs.application.mission;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;

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

    List<WCStudentMissionRelationBean> getMissionsByStudentId(long studentId);

    List<WCStudentMissionRelationBean> getUnConfirmMissionByClubId(long studentId);

}
