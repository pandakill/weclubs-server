package com.weclubs.mapper;

import com.weclubs.bean.WCClubDepartmentBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_department 部门表的映射接口
 *
 * Created by fangzanpan on 2017/3/10.
 */
public interface WCClubDepartmentMapper {

    void createClubDepartment(WCClubDepartmentBean departmentBean);

    void updateClubDepartment(WCClubDepartmentBean departmentBean);

    void deleteClubDepartment(@Param("clubDepartmentId") long clubDepartmentId);

    WCClubDepartmentBean getClubDepartmentById(@Param("clubDepartmentId") long clubDepartmentId);

    List<WCClubDepartmentBean> getClubDepartmentBySuggest();
}
