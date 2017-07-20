package com.weclubs.application.club;

import com.weclubs.bean.WCClubGraduateBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentClubGraduateRelationBean;
import com.weclubs.util.WCHttpStatus;

/**
 * 社团届数服务类的接口层
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCIClubGraduateService {

    void createClubGraduate(WCClubGraduateBean clubGraduateBean);

    void updateClubGraduate(WCClubGraduateBean clubGraduateBean);

    void deleteClubGraduateById(long clubGraduateId);

    void setClubGraduateSelected(long clubGraduateId);

    WCClubGraduateBean getCurrentClubGraduate(long clubId);

    WCStudentClubGraduateRelationBean getStudentClubGraduationRelationByGraduateId(long studentId, long graduateId);

    void createStuCluGraduateRelation(WCStudentClubGraduateRelationBean relationBean);

    /**
     * 设置某学生当前届社团的部门
     *
     * @param clubId    社团id
     * @param studentId 学生id
     * @param departmentId  部门id
     */
    WCHttpStatus updateClubCurrentGraduateStudentDepartment(long clubId, long studentId, long departmentId);

    /**
     * 设置某学生当前届舒坦的职位
     *
     * @param clubId    社团id
     * @param studentId 学生id
     * @param jobId 职位id
     */
    WCHttpStatus updateClubCurrentGraduateStudentJob(long clubId, long studentId, long jobId);

    /**
     * 删除学生，从某个社团的当前届中
     *
     * @param clubId    社团id
     * @param studentId 学生id
     * @return  删除结果
     */
    WCHttpStatus deleteStudentFromClubCurrentGraduate(long clubId, long studentId);
}
