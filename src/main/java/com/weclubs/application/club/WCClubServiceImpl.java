package com.weclubs.application.club;

import com.weclubs.application.club_responsibility.WCIClubResponsibilityService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.mapper.WCClubHonorMapper;
import com.weclubs.mapper.WCClubMapper;
import com.weclubs.mapper.WCDynamicMapper;
import com.weclubs.model.WCManageClubModel;
import com.weclubs.model.WCMyClubModel;
import com.weclubs.model.WCStudentBaseInfoModel;
import com.weclubs.model.WCStudentForClubModel;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 社团服务层实现类
 *
 * Created by fangzanpan on 2017/3/8.
 */
@Service(value = "clubService")
public class WCClubServiceImpl implements WCIClubService {

    public final static int SORT_BY_REAL_NAME = 0;
    public final static int SORT_BY_GRADUATE = 1;
    public final static int SORT_BY_DEPARTMENT = 2;
    public final static int SORT_BY_JOB = 3;

    private Logger log = Logger.getLogger(WCClubServiceImpl.class);

    @Autowired
    private WCClubMapper mClubMapper;
    @Autowired
    private WCClubHonorMapper mClubHonorMapper;
    @Autowired
    private WCDynamicMapper mDynamicMapper;

    @Autowired
    private WCIClubGraduateService mClubGraduateService;
    @Autowired
    private WCIUserService mUserService;
    @Autowired
    private WCIClubResponsibilityService mClubResponsibilityService;

    public WCClubBean getClubInfoById(long clubId) {

        if (clubId <= 0) {
            log.error("getClubInfoById：查找社团失败，clubId");
            return null;
        }

        return mClubMapper.getClubById(clubId);
    }

    public void createClub(WCClubBean clubBean) {

        if (clubBean == null) {
            log.error("createClub：创建 club 对象失败，club 不能为 null。");
            return;
        }

        mClubMapper.createClub(clubBean);
    }

    public void updateClub(WCClubBean clubBean) {

        if (clubBean == null) {
            log.error("updateClub：更新失败，club 对象不能为空");
            return;
        }

        if (clubBean.getClubId() <= 0) {
            log.error("updateClub：更新失败，club.id 不能为小于等于 0。");
        }

        mClubMapper.updateClub(clubBean);
    }

    public List<WCClubBean> getClubsBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getClubsBySchoolId：schoolId 不能小于等于 0。");
            return null;
        }

        return mClubMapper.getClubsBySchoolId(schoolId);
    }

    public List<WCClubBean> getClubsByStudentId(long studentId) {

        if (studentId <= 0) {
            log.error("getClubsByStudentId：studentId 不能小于等于 0。");
            return null;
        }

        return mClubMapper.getClubsByStudentId(studentId);
    }

    @Cacheable(value = "getClubHonorByClubId")
    public List<WCClubHonorBean> getClubHonorByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getClubHonorByClubId：clubId 不能小于等于 0。");
            return null;
        }

        return mClubHonorMapper.getClubHonorsByClubId(clubId);
    }

    public List<WCClubStudentBean> getStudentsByCurrentGraduate(long clubId, int sortType) {

        if (clubId <= 0) {
            log.error("getStudentsByCurrentGraduate：clubId 不能小于等于 0。");
            return null;
        }

        if (sortType == SORT_BY_REAL_NAME) {    //根据学生真实姓名首字母排序
            return mClubMapper.getCurrentGraduateStudentsBySortStuName(clubId);
        }

        return mClubMapper.getCurrentGraduateStudents(clubId);
    }

    public List<WCMyClubModel> getMyClubs(long studentId) {

        if (studentId <= 0) {
            log.error("getMyClubs：studentId不能小于等于0");
            return null;
        }

        List<WCMyClubModel> result = new ArrayList<WCMyClubModel>();

        List<WCClubBean> myClubs = mClubMapper.getClubsByStudentId(studentId);
        for (WCClubBean myClub : myClubs) {
            WCMyClubModel myClubModel = new WCMyClubModel(myClub);
            myClubModel.setMemberCount(mClubMapper.getClubMemberCount(myClub.getClubId()));
            myClubModel.setActivityCount(mClubMapper.getClubActivityCount(myClub.getClubId()));
            myClubModel.setTodoCount(mDynamicMapper.getStudentTodoCountOneClub(studentId, myClub.getClubId()));
            result.add(myClubModel);
        }

        return result;
    }

    public WCStudentForClubModel getClubStudentByStudentId(long studentId, long clubId) {

        WCStudentBaseInfoModel baseInfoModel = mUserService.getUserBaseInfo(studentId);

        WCClubGraduateBean graduateBean = mClubGraduateService.getCurrentClubGraduate(clubId);

        WCStudentForClubModel result = new WCStudentForClubModel(baseInfoModel);

        if (graduateBean != null) {
            WCClubBean clubBean = mClubMapper.getClubById(clubId);
            WCStudentClubGraduateRelationBean clubGraduateRelationBean
                    = mClubGraduateService.getStudentClubGraduationRelationByGraduateId(studentId, graduateBean.getClubGraduateId());

            result.setClubId(clubBean.getClubId());
            result.setClubLevel(clubBean.getLevel());
            result.setClubLogo(clubBean.getAvatarUrl());
            result.setClubSlogan(clubBean.getSlogan());
            result.setClubIntroduction(clubBean.getIntroduction());

            if (clubGraduateRelationBean.getDepartmentId() > 0) {
                WCClubDepartmentBean departmentBean
                        = mClubResponsibilityService.getClubDepartmentById(clubGraduateRelationBean.getDepartmentId());
                if (departmentBean != null) {
                    result.setDepartmentId(departmentBean.getDepartmentId());
                    result.setDepartmentName(departmentBean.getDepartmentName());
                }
            }

            if (clubGraduateRelationBean.getJobId() > 0) {
                WCClubJobBean jobBean = mClubResponsibilityService.getClubJobById(clubGraduateRelationBean.getJobId());
                if (jobBean != null) {
                    result.setJobId(jobBean.getJobId());
                    result.setJobName(jobBean.getJobName());
                }
            }

        }

        log.info("getClubStudentByStudentId：result = " + result.toString());

        return result;
    }

    public List<WCManageClubModel> getMyManageClubs(long studentId) {

        if (studentId <= 0) {
            log.error("getMyManageClubs：studentId不能小于等于0");
            return null;
        }

        List<WCManageClubModel> myManageClubs = mClubMapper.getMyManageCurrentGraClub(studentId);
        List<WCManageClubModel> resultClubs = new ArrayList<WCManageClubModel>();
        if (myManageClubs != null && myManageClubs.size() > 0) {
            for (WCManageClubModel myManageClub : myManageClubs) {
                List<WCClubHonorBean> honors = mClubHonorMapper.getClubHonorsByClubId(myManageClub.getClubId());
                myManageClub.setHonorCount(honors != null ? honors.size() : 0);
                myManageClub.setMemberCount(mClubMapper.getClubMemberCount(myManageClub.getClubId()));

                if (!StringUtils.isEmpty(myManageClub.getJobs())) {
                    try {
                        JSONObject authority = new JSONObject(myManageClub.getJobs());
                        myManageClub.setJobAuthority(authority);

                        String jobs = "";

                        Iterator it = authority.keys();
                        ArrayList<String> job = new ArrayList<String>();
                        while (it.hasNext()) {
                            job.add((String) it.next());
                        }
                        for (int i = 0; i < job.size(); i++) {
                            jobs += job.get(i);
                            if (i != (job.size() - 1)) {
                                jobs += ",";
                            }
                        }

                        myManageClub.setJobs(jobs);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (myManageClub.getMyJob() > 0 && myManageClub.getJobAuthority() != null) {
                    if (myManageClub.getJobAuthority().has(myManageClub.getMyJob() + "")) {
                        try {
                            String jobs = (String) myManageClub.getJobAuthority().get(myManageClub.getMyJob() + "");
                            if (jobs.contains("1")
                                    || jobs.contains("2")
                                    || jobs.contains("5")
                                    || jobs.contains("7")) {
                                resultClubs.add(myManageClub);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return resultClubs;
    }
}
