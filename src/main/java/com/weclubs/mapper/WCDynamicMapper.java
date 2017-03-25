package com.weclubs.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 动态相关的数据库mapper接口
 *
 * Created by fangzanpan on 2017/3/26.
 */
public interface WCDynamicMapper {

    /**
     * 根据学生 id 和 社团 id 查找该学生在这个社团的待办的数量
     *
     * @param studentId 学生id
     * @param clubId    社团id
     * @return  该学生在某个社团的待办数量
     */
    long getStudentTodoCountOneClub(@Param("studentId") long studentId, @Param("clubId") long clubId);

    /**
     * 查找某学生在某社团的未查看的社团数量
     *
     * @param studentId 学生id
     * @param clubId    社团id
     * @return  该学生在某个社团待查看的数量
     */
    long getStudentTodoCountNew(@Param("studentId") long studentId, @Param("clubId") long clubId);

}
