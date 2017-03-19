package com.weclubs.mapper;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_mission 社团任务的映射接口
 * t_club_mission.type = 1
 *
 * Created by fangzanpan on 2017/3/9.
 */
public interface WCClubMissionMapper {

    void createClubMission(WCClubMissionBean missionBean);

    void updateClubMission(WCClubMissionBean missionBean);

    void deleteClubMissionById(@Param("clubMissionId") long missionId);

    WCClubMissionBean getClubMissionById(@Param("clubMissionId") long missionId);

    List<WCClubMissionBean> getClubMissionsByClubId(@Param("clubId") long clubId);

    List<WCStudentMissionRelationBean> getClubMissionsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getClubMissionsBySchoolId(@Param("schoolId") long schoolId);

    void changeClubMissionStatus(@Param("clubMissionId") long clubMissionId, int status);

    List<WCStudentMissionRelationBean> getUnConfirmClubMissionsByStudentId(@Param("studentId") long studentId);
}
