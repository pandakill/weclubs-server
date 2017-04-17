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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
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

    public long createClub(WCClubBean clubBean, long studentId) {

        if (clubBean == null) {
            log.error("createClub：创建 club 对象失败，club 不能为 null。");
            return 0;
        }

        mClubMapper.createClub(clubBean);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("createClub：创建 clubBean 失败。此时 clubBean = " + clubBean.toString());
            e.printStackTrace();
            return 0;
        }

        WCClubGraduateBean graduateBean = new WCClubGraduateBean();
        graduateBean.setClubId(clubBean.getClubId());
        graduateBean.setIsCurrent(1);
        mClubGraduateService.createClubGraduate(graduateBean);

        try {
            Thread.sleep(200);

            WCStudentClubGraduateRelationBean relationBean = new WCStudentClubGraduateRelationBean();
            relationBean.setStudentId(studentId);
            relationBean.setSuperAdmin(1);
            relationBean.setStatus(1);
            relationBean.setGraduateId(graduateBean.getClubGraduateId());
            mClubGraduateService.createStuCluGraduateRelation(relationBean);

            try {
                Thread.sleep(200);

                log.info("createClub：创建社团成功，clubBean = " + clubBean.toString());
                log.info("createClub：创建社团成功，clubGraduateBean = " + graduateBean.toString());
                log.info("createClub：创建社团成功，studentRelation = " + relationBean.toString());

                return clubBean.getClubId();
            } catch (InterruptedException e) {
                log.error("createClub：创建 studentRelation 失败。此时 clubId = " + graduateBean.getClubId());
                log.error("createClub：创建 studentRelation 失败。此时 clubGraduateId = " + graduateBean.getClubGraduateId());
                e.printStackTrace();
                return 0;
            }
        } catch (InterruptedException e) {
            log.error("createClub：创建 clubGraduateBean 失败。此时 clubId = " + clubBean.getClubId());
            e.printStackTrace();
            return 0;
        }
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

//    @Cacheable(value = "getClubHonorByClubId")
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

                if (myManageClub.getIsSuperAdmin() == 1) {
                    resultClubs.add(myManageClub);
                } else if (myManageClub.getMyJob() > 0 && myManageClub.getJobAuthority() != null) {
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

    @Override
    public void addClubHonor(long clubId, List<HashMap<String, Object>> honorList) {

        if (clubId <= 0) {
            log.error("addClubHonor：clubId不能小于扥估0");
            return;
        }

        if (honorList == null || honorList.size() == 0) {
            log.error("addClubHonor：荣誉列表不能为空");
            return;
        }

        List<WCClubHonorBean> list = new ArrayList<>();
        for (HashMap<String, Object> result : honorList) {
            WCClubHonorBean honorBean = new WCClubHonorBean();
            honorBean.setClubId(clubId);
            honorBean.setContent((String) result.get("content"));
            long getDate = 0;
            if (result.get("get_date") instanceof String) {
                getDate = Long.parseLong((String) result.get("get_date"));
            } else if (result.get("get_date") instanceof Long) {
                getDate = (Long) result.get("get_date");
            }
            honorBean.setGetDate(getDate);
            list.add(honorBean);
        }

        mClubHonorMapper.createHonorByList(list);
    }
}
