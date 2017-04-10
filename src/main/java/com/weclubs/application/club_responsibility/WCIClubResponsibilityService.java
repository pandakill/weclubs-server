package com.weclubs.application.club_responsibility;

import com.weclubs.bean.WCClubAuthorityBean;
import com.weclubs.bean.WCClubDepartmentBean;
import com.weclubs.bean.WCClubJobBean;
import org.json.JSONObject;

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

    void setDepartmentsByClubId(long clubId, String ids);

    void setNewDepartmentsByClubId(long clubId, String ids, String departments);

    void setJobsByClubId(long clubId, List<WCClubJobBean> jobs);

    void setNewJobByClubId(long clubId, JSONObject jobAuth);

    List<WCClubDepartmentBean> getDepartmentsBySuggest();

    List<WCClubJobBean> getJobsBySuggest();

    List<WCClubDepartmentBean> getDepartmentsByClubId(long clubId, boolean pureSelected);

    List<WCClubJobBean> getJobsByClubId(long clubId);

    WCClubDepartmentBean getClubDepartmentById(long departmentId);

    WCClubJobBean getClubJobById(long jobId);

    List<WCClubAuthorityBean> getAllAuthority();
}
