package com.weclubs.mapper;

import com.weclubs.bean.WCStudentBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mybatis的学生映射接口
 *
 * Created by fangzanpan on 2017/3/4.
 */
public interface WCStudentMapper {

    /**
     * 获取同个学校的学生
     *
     * @param schoolId  学校id
     *
     * @return  该学校的学生列表
     */
    List<WCStudentBean> getStudentsBySchool(@Param("schoolId") long schoolId);

    /**
     * 获取当前届数的社团的所有学生
     *
     * @param clubId    社团id
     *
     * @return  同社团并且同届的学生列表
     */
    List<WCStudentBean> getStudentsByCurrentClub(@Param("clubId") long clubId);

    /**
     * 获取某一届社团的所有学生
     *
     * @param graduateId    社团届数id
     *
     * @return  某一届社团的所有学生列表
     */
    List<WCStudentBean> getStudentsByClubGraduate(@Param("graduateId") long graduateId);

    /**
     * 根据学生id获取学生个人信息
     *
     * @param studentId 学生id
     *
     * @return  该学生的实体类
     */
    WCStudentBean getStudentById(@Param("studentId") long studentId);

    /**
     * 根据手机号码获取学生个人信息
     *
     * @param mobile    学生手机号码
     *
     * @return  该学生的实体类
     */
    WCStudentBean getStudentByMobile(@Param("mobile") String mobile);

    /**
     * 创建一个新的学生实体
     *
     * @param studentBean 学生实体类
     *
     * @return  如果创建成功返回该学生的id，否则返回
     */
    void createStudent(WCStudentBean studentBean);

    /**
     * 更新学生信息实体
     *
     * @param studentBean   学生实体类
     */
    void updateStudent(WCStudentBean studentBean);
}
