package com.weclubs.mapper;

import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubStudentBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club 社团\组织的映射接口
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCClubMapper {

    /**
     * 创建一个社团实体存进数据库
     *
     * @param clubBean  社团实体，创建完成之后会更新实体的id
     */
    void createClub(WCClubBean clubBean);

    /**
     * 更新社团实体
     *
     * @param clubBean  社团实体
     */
    void updateClub(WCClubBean clubBean);

    /**
     * 通过社团 id 获取社团实体内容
     *
     * @param clubId    社团唯一id
     *
     * @return  社团的实体内容，不存在则返回 null
     */
    WCClubBean getClubById(@Param("clubId") long clubId);

    /**
     * 通过学校 id 获取该校的所有社团
     *
     * @param schoolId  学校id
     *
     * @return  该校的社团实体列表
     */
    List<WCClubBean> getClubsBySchoolId(@Param("schoolId") long schoolId);

    /**
     * 通过学生 id 获取该学生加入的所有社团
     *
     * @param studentId 学生id
     *
     * @return  该生的社团实体列表
     */
    List<WCClubBean> getClubsByStudentId(@Param("studentId") long studentId);

    /**
     * 获取某个社团当前届的所有学生
     *
     * @param clubId    社团id
     *
     * @return  返回此社团当前届的所有学生
     */
    List<WCClubStudentBean> getCurrentGraduateStudents(@Param("clubId") long clubId);

    /**
     * 根据学生的姓名进行首字母拼音排序
     *
     * @param clubId    社团id
     * @return  返回此社团当前届的所有学生
     */
    List<WCClubStudentBean> getCurrentGraduateStudentsBySortStuName(@Param("clubId") long clubId);

    long getClubMemberCount(@Param("clubId") long clubId);

    long getClubActivityCount(@Param("clubId") long clubId);
}
