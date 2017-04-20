package com.weclubs.application.mission;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.model.WCMissionBaseModel;
import com.weclubs.model.WCSponsorMissionModel;
import com.weclubs.util.WCHttpStatus;

import java.util.HashMap;
import java.util.List;

/**
 * 任务的服务类接口
 *
 * Created by fangzanpan on 2017/3/16.
 */
public interface WCIClubMissionService {

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

    /**
     * 根据发布者 id 获取该发布者的任务列表
     *
     * @param sponsorId 发布者id
     *
     * @return  该发布者的任务列表
     */
    List<WCSponsorMissionModel> getMissionBySponsorId(long sponsorId);

    /**
     * 发布一条新的任务
     *
     * @param requestData   请求参数
     * @return  如果发布成功，返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus publicMission(HashMap<String, Object> requestData);

    /**
     * 根据任务 id 获取学生任务关系列表
     * 【包括学生实体、是否有任务项、学生任务关系】
     *
     * @param missionId 任务id
     *
     * @return  某个任务关系列表
     */
    List<WCStudentMissionRelationBean> getMissionRelationsByMissionId(long missionId);
}
