package com.weclubs.application.club;

import com.weclubs.application.club_responsibility.WCIClubResponsibilityService;
import com.weclubs.bean.WCClubDepartmentBean;
import com.weclubs.bean.WCClubGraduateBean;
import com.weclubs.bean.WCClubJobBean;
import com.weclubs.bean.WCStudentClubGraduateRelationBean;
import com.weclubs.mapper.WCClubGraduateMapper;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社团届数服务的实现类
 *
 * Created by fangzanpan on 2017/3/8.
 */
@Service(value = "clubGraduateService")
class WCClubGraduateServiceImpl implements WCIClubGraduateService {

    private Logger log = Logger.getLogger(WCClubGraduateServiceImpl.class);

    private WCIClubResponsibilityService mClubResponsibilityService;
    private WCClubGraduateMapper mClubGraduateMapper;

    @Autowired
    public WCClubGraduateServiceImpl(WCIClubResponsibilityService mClubResponsibilityService,
                                     WCClubGraduateMapper mClubGraduateMapper) {
        this.mClubResponsibilityService = mClubResponsibilityService;
        this.mClubGraduateMapper = mClubGraduateMapper;
    }

    public void createClubGraduate(WCClubGraduateBean clubGraduateBean) {

        if (clubGraduateBean == null) {
            log.error("createClubGraduate：创建社团届数失败，clubGraduate对象不能为空。");
            return;
        }

        mClubGraduateMapper.createClubGraduate(clubGraduateBean);
    }

    public void updateClubGraduate(WCClubGraduateBean clubGraduateBean) {

        if (clubGraduateBean == null) {
            log.error("updateClubGraduate：更新社团届数失败，clubGraduate对象不能为空。");
            return;
        }

        mClubGraduateMapper.updateClubGraduate(clubGraduateBean);
    }

    public void deleteClubGraduateById(long clubGraduateId) {

    }

    public void setClubGraduateSelected(long clubGraduateId) {

        WCClubGraduateBean willSelect = mClubGraduateMapper.getClubGraduateByClubGraduateId(clubGraduateId);

        if (willSelect == null) {
            log.error("setClubGraduateSelected：根据 clubGraduateId 查找不到对象。");
            return;
        }

        WCClubGraduateBean oldSelect = mClubGraduateMapper.getCurrentClubGraduateByClubId(willSelect.getClubId());

        willSelect.setIsCurrent(0);
        oldSelect.setIsCurrent(1);

        mClubGraduateMapper.updateClubGraduate(willSelect);
        mClubGraduateMapper.updateClubGraduate(oldSelect);
    }

    public WCClubGraduateBean getCurrentClubGraduate(long clubId) {

        if (clubId <= 0) {
            log.error("getCurrentClubGraduate：clubId 不能小于等于0");
            return null;
        }

        return mClubGraduateMapper.getCurrentClubGraduateByClubId(clubId);
    }

    public WCStudentClubGraduateRelationBean getStudentClubGraduationRelationByGraduateId(long studentId, long graduateId) {

        if (graduateId <= 0) {
            log.error("getStudentClubGraduationRelationByGraduateId：graduateId不能小于等于0");
            return null;
        }

        return mClubGraduateMapper.getStudentGraduateRelation(studentId, graduateId);
    }

    public void createStuCluGraduateRelation(WCStudentClubGraduateRelationBean relationBean) {

        if (relationBean == null) {
            log.error("createStuCluGraduateRelation：创建失败，relationBean不能为空");
            return;
        }
        mClubGraduateMapper.createStuCluGraRelation(relationBean);
    }

    @Override
    public WCHttpStatus updateClubCurrentGraduateStudentDepartment(long clubId, long studentId, long departmentId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (clubId <= 0) {
            log.error("updateCurrentClubGraduateStudent：clubId不能小于等于0");
            check.msg = "club_id 不能小于等于0";
            return check;
        }

        if (studentId <= 0) {
            log.error("updateCurrentClubGraduateStudent：studentId不能小于等于0");
            check.msg = "student_id 不能小于等于0";
            return check;
        }

        if (departmentId <= 0) {
            log.error("updateCurrentClubGraduateStudent：departmentId不能小于等于0");
            check.msg = "department_id 不能小于等于0";
            return check;
        }

        List<WCClubDepartmentBean> departments = mClubResponsibilityService.getDepartmentsByClubId(clubId, true);
        if (departments == null || departments.size() == 0) {
            log.error("updateClubCurrentGraduateStudentDepartment：该社团尚未设置部门");
            check.msg = "该社团尚未设置部门";
            return check;
        }

        boolean isIncluded = false;
        for (WCClubDepartmentBean department : departments) {
            if (department.getDepartmentId() == departmentId) {
                isIncluded = true;
                break;
            }
        }

        if (!isIncluded) {
            log.error("updateClubCurrentGraduateStudentDepartment：该社团尚未设置部门");
            check.msg = "该社团尚未设置部门";
            return check;
        }

        WCStudentClubGraduateRelationBean graduateRelationBean = mClubGraduateMapper.getStudentGraduateRelationByCurrentClubId(studentId, clubId);
        graduateRelationBean.setDepartmentId(departmentId);

        mClubGraduateMapper.updateStudentClubGraduateRelation(graduateRelationBean);
        check = WCHttpStatus.SUCCESS;
        return check;
    }

    @Override
    public WCHttpStatus updateClubCurrentGraduateStudentJob(long clubId, long studentId, long jobId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (clubId <= 0) {
            log.error("updateClubCurrentGraduateStudentJob：club_id 不能小于等于0");
            check.msg = "club_id 不能小于等于0";
            return check;
        }

        if (studentId <= 0) {
            log.error("updateClubCurrentGraduateStudentJob：student_id 不能小于等于0");
            check.msg = "student_id 不能小于等于0";
            return check;
        }

        if (jobId <= 0) {
            log.error("updateClubCurrentGraduateStudentJob：job_id 不能小于等于0");
            check.msg = "job_id 不能小于等于0";
            return check;
        }

        List<WCClubJobBean> jobs = mClubResponsibilityService.getJobsByClubId(clubId);
        if (jobs == null || jobs.size() == 0) {
            log.error("updateClubCurrentGraduateStudentJob：该社团尚未设置职位");
            check.msg = "该社团尚未设置职位";
            return check;
        }

        boolean isIncluded = false;
        for (WCClubJobBean job : jobs) {
            if (job.getJobId() == jobId) {
                isIncluded = true;
                break;
            }
        }

        if (!isIncluded) {
            log.error("updateClubCurrentGraduateStudentJob：该社团尚未设置职位");
            check.msg = "该社团尚未设置职位";
            return check;
        }

        WCStudentClubGraduateRelationBean graduateRelationBean = mClubGraduateMapper.getStudentGraduateRelationByCurrentClubId(studentId, clubId);
        graduateRelationBean.setJobId(jobId);

        mClubGraduateMapper.updateStudentClubGraduateRelation(graduateRelationBean);
        check = WCHttpStatus.SUCCESS;
        return check;
    }
}
