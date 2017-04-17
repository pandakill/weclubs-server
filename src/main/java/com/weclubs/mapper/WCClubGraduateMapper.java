package com.weclubs.mapper;

import com.weclubs.bean.WCClubGraduateBean;
import com.weclubs.bean.WCStudentClubGraduateRelationBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_graduate 社团届数的映射接口
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCClubGraduateMapper {

    void createClubGraduate(WCClubGraduateBean clubGraduateBean);

    void updateClubGraduate(WCClubGraduateBean clubGraduateBean);

    void deleteClubGraduateById(@Param("clubGraduateId") long clubGraduateId);

    List<WCClubGraduateBean> getClubGraduatesByClubId(@Param("clubId") long clubId);

    WCClubGraduateBean getClubGraduateByClubGraduateId(@Param("clubGraduateId") long clubGraduateId);

    WCClubGraduateBean getCurrentClubGraduateByClubId(@Param("clubId") long clubId);

    WCStudentClubGraduateRelationBean getStudentGraduateRelation(@Param("studentId") long studentId,
                                                                 @Param("graduateId") long graduateId);

    void createStuCluGraRelation(WCStudentClubGraduateRelationBean relationBean);

    /**
     * 根据学生 id 和 社团 id 获取当前的社团届数关系
     *
     * @param studentId 学生id
     * @param clubId    社团id
     * @return  返回社团届数关系
     */
    WCStudentClubGraduateRelationBean getStudentGraduateRelationByCurrentClubId(@Param("studentId") long studentId,
                                                                 @Param("clubId") long clubId);

    /**
     * 更新学生社团届数的关系
     *
     * @param relationBean  关系实体
     */
    void updateStudentClubGraduateRelation(WCStudentClubGraduateRelationBean relationBean);
}
