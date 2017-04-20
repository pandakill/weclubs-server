package com.weclubs.mapper;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMissionBaseModel;
import com.weclubs.model.WCSponsorMissionModel;
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

    List<WCStudentMissionRelationBean> getClubMissionsByStudentId(@Param("studentId") long studentId,
                                                                  @Param("clubId") long clubId);

    List<WCClubMissionBean> getClubMissionsBySchoolId(@Param("schoolId") long schoolId);

    void changeClubMissionStatus(@Param("clubMissionId") long clubMissionId, int status);

    List<WCStudentMissionRelationBean> getUnConfirmClubMissionsByStudentId(@Param("studentId") long studentId);

    List<WCClubMissionBean> getChildMissionsByMissionId(@Param("missionId") long missionId);

    List<WCStudentBean> getClubMissionParticipantByMissionId(@Param("missionId") long missionId);

    List<WCMissionBaseModel> getChildMissionDetailListByMissionIdWithStudent(@Param("studentId") long studentId,
                                                                             @Param("missionId") long missionId);

    /**
     * 根据发布者 id 获取该发布者的所有父级任务
     *
     * @param sponsorId 发布者id
     *
     * @return  该发布者的父级任务
     */
    List<WCSponsorMissionModel> getMissionsBySponsorId(@Param("sponsorId") long sponsorId);
}
