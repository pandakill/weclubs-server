package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学校、院系、专业关系表，对应数据库 t_school 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubStudentBean extends WCStudentBean implements Serializable {

    private long departmentId;
    private long jobId;

    private WCClubDepartmentBean departmentBean;
    private WCClubJobBean jobBean;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public WCClubDepartmentBean getDepartmentBean() {
        return departmentBean;
    }

    public void setDepartmentBean(WCClubDepartmentBean departmentBean) {
        this.departmentBean = departmentBean;
    }

    public WCClubJobBean getJobBean() {
        return jobBean;
    }

    public void setJobBean(WCClubJobBean jobBean) {
        this.jobBean = jobBean;
    }

    @Override
    public String toString() {
        return "WCClubStudentBean{" +
                "departmentId=" + departmentId +
                ", jobId=" + jobId +
                ", departmentBean=" + departmentBean +
                ", jobBean=" + jobBean +
                '}';
    }
}
