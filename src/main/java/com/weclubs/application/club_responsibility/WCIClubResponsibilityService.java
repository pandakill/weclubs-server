package com.weclubs.application.club_responsibility;

import com.weclubs.bean.WCClubDepartmentBean;
import com.weclubs.bean.WCClubJobBean;

import java.util.List;

/**
 * 部门职能的服务类接口
 *
 * Created by fangzanpan on 2017/3/10.
 */
public interface WCIClubResponsibilityService {

    void createDepartment(WCClubDepartmentBean departmentBean);

    void createJob(WCClubJobBean jobBean);

    void setDepartmentsByClubId(long clubId, List<WCClubDepartmentBean> departments);

    void setJobsByClubId(long clubId, List<WCClubJobBean> jobs);

    List<WCClubDepartmentBean> getDepartmentsBySuggest();

    List<WCClubJobBean> getJobsBySuggest();

    List<WCClubDepartmentBean> getDepartmentsByClubId(long clubId);

    List<WCClubJobBean> getJobsByClubId(long clubId);
}
