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

    void createClubMission(@Param("list") List<WCClubMissionBean> missions);

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

    /**
     * 批量添加学生对应关系
     *
     * @param relations 学生、通知之间的关系列表
     */
    void createStudentRelation(@Param("relations") List<WCStudentMissionRelationBean> relations);

    /**
     * 根据任务 id 获取学生任务关系列表
     * 【包括学生实体、是否有任务项、学生任务关系】
     *
     * @param missionId 任务id
     *
     * @return  某个任务关系列表
     */
    List<WCStudentMissionRelationBean> getMissionRelationsByMissionId(@Param("missionId") long missionId);

    /**
     * 根据任务 ID 获取该任务尚未确认的人员列表
     *
     * @param missionId 任务id
     *
     * @return  人员列表
     */
    List<WCStudentMissionRelationBean> getUnConfirmClubMissionsByMissionId(@Param("missionId") long missionId);

    /**
     * 根据任务 ID 获取该任务尚未确认的人员列表
     *
     * @param missionId 任务id
     *
     * @return  人员列表
     */
    List<WCStudentMissionRelationBean> getUnFinishClubMissionsByMissionId(@Param("missionId") long missionId);
}
