package com.weclubs.application.club_responsibility;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weclubs.bean.WCClubAuthorityBean;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubDepartmentBean;
import com.weclubs.bean.WCClubJobBean;
import com.weclubs.mapper.WCClubAuthorityMapper;
import com.weclubs.mapper.WCClubDepartmentMapper;
import com.weclubs.mapper.WCClubJobMapper;
import com.weclubs.mapper.WCClubMapper;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门职能的服务类
 *
 * Created by fangzanpan on 2017/3/10.
 */
@Service("clubResponsibilityService")
public class WCClubResponsibilityServiceImpl implements WCIClubResponsibilityService {

    private Logger log = Logger.getLogger(WCClubResponsibilityServiceImpl.class);

    @Autowired
    private WCClubMapper mClubMapper;
    @Autowired
    private WCClubDepartmentMapper mDepartmentMapper;
    @Autowired
    private WCClubJobMapper mJobMapper;
    @Autowired
    private WCClubAuthorityMapper mAuthorityMapper;

    public void createDepartment(WCClubDepartmentBean departmentBean) {

        if (departmentBean == null) {
            log.error("createDepartment：创建部门失败，department不能为空。");
            return;
        }

        if (StringUtils.isEmpty(departmentBean.getName())) {
            log.error("createDepartment：创建部门失败，department.getName不能为空。");
            return;
        }

        WCClubDepartmentBean department = mDepartmentMapper.getClubDepartmentByName(departmentBean.getName());

        if (department != null) {
            log.error("createDepartment：创建部门失败，【" + department.getName() + "】在数据库中已经存在。");
            return;
        }

        mDepartmentMapper.createClubDepartment(departmentBean);
    }

    public void createJob(WCClubJobBean jobBean) {

        if (jobBean == null) {
            log.error("createJob：创建士炜失败，jobBean不能为空。");
            return;
        }

        if (StringUtils.isEmpty(jobBean.getName())) {
            log.error("createJob：创建职位失败，jobBean.getName不能为空。");
            return;
        }

        WCClubJobBean job = mJobMapper.getClubJobByJobName(jobBean.getName());

        if (job != null) {
            log.error("createJob：创建职位失败，【" + job.getName() + "】在数据库中已经存在。");
            return;
        }

        mJobMapper.createClubJob(jobBean);
    }

    public void setDepartmentsByClubId(long clubId, List<WCClubDepartmentBean> departments) {

        if (clubId <= 0) {
            log.error("setDepartmentsByClubId：设置社团部门失败，clubId不能小于等于0。");
            return;
        }

        WCClubBean clubBean = mClubMapper.getClubById(clubId);

        if (clubBean == null) {
            log.error("setDepartmentsByClubId：设置社团部门失败，找不到 id = " + clubId + " 的社团");
            return;
        }

        String departmentsStr = "";

        if (departments != null && departments.size() > 0) {
            for (int i = 0; i < departments.size(); i++) {
                departmentsStr += departments.get(i).getDepartmentId();

                if (i != (departments.size() - 1)) {
                    departmentsStr += ",";
                }
            }
        }

        log.info("setDepartmentsByClubId：departmentsStr = " + departmentsStr);

        mDepartmentMapper.setCurrentClubDepartments(clubId, departmentsStr);
    }

    public void setJobsByClubId(long clubId, List<WCClubJobBean> jobs) {

        if (clubId <= 0) {
            log.error("setJobsByClubId：设置社团职能失败，clubId不能小于等于0。");
            return;
        }

        WCClubBean clubBean = mClubMapper.getClubById(clubId);

        if (clubBean == null) {
            log.error("setJobsByClubId：设置社团职能失败，找不到 id = " + clubId + " 的社团。");
            return;
        }

        String jobStr = "";

        if (jobs.size() > 0) {
            JSONArray jobArray = new JSONArray();

            for (WCClubJobBean job : jobs) {
                JSONObject jsonObject = new JSONObject();

                String authority = "";

                List<WCClubAuthorityBean> authorities = job.getAuthorities();
                if (authorities != null && authorities.size() > 0) {
                    for (int j = 0; j < authorities.size(); j++) {
                        authority += authorities.get(j).getClubAuthorityId();

                        if (j != (authorities.size() - 1)) {
                            authority += ",";
                        }
                    }
                }

                jsonObject.put(job.getJobId() + "", authority);
                jobArray.add(jsonObject);
            }

            jobStr = jobArray.toJSONString();
        }

        log.info("setJobsByClubId：jobStr = " + jobStr);

        mJobMapper.setCurrentClubJobs(clubId, jobStr);
    }

    public List<WCClubDepartmentBean> getDepartmentsBySuggest() {
        return mDepartmentMapper.getClubDepartmentBySuggest();
    }

    public List<WCClubJobBean> getJobsBySuggest() {
        return mJobMapper.getClubJobBySuggest();
    }

    public List<WCClubDepartmentBean> getDepartmentsByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getDepartmentsByClubId：获取社团部门失败，clubId不能小于等于0。");
            return null;
        }

        if (mClubMapper.getClubById(clubId) == null) {
            log.error("getDepartmentsByClubId：找不到 id = " + clubId + " 的社团。");
            return null;
        }

        List<WCClubDepartmentBean> result = new ArrayList<WCClubDepartmentBean>();
        String departmentsStr = mDepartmentMapper.getCurrentClubDepartmentsByClubId(clubId);

        if (!StringUtils.isEmpty(departmentsStr)) {
            String[] depArray = departmentsStr.split(",");

            for (String s : depArray) {
                WCClubDepartmentBean departmentBean = mDepartmentMapper.getClubDepartmentById(Long.parseLong(s));
                result.add(departmentBean);
            }
        }

        return result;
    }

    public List<WCClubJobBean> getJobsByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getJobsByClubId：获取社团职能失败，clubId不能小于等于0。");
            return null;
        }

        if (mClubMapper.getClubById(clubId) == null) {
            log.error("getJobsByClubId：找不到 id = " + clubId + " 的社团。");
            return null;
        }

        List<WCClubJobBean> result = new ArrayList<WCClubJobBean>();
        String jobsStr = mJobMapper.getCurrentClubJobs(clubId);

        if (!StringUtils.isEmpty(jobsStr)) {
            try {
                org.json.JSONArray jsonArray = new org.json.JSONArray(jobsStr);

                for (int i = 0; i < jsonArray.length(); i ++) {
                    org.json.JSONObject jsonObject = (org.json.JSONObject) jsonArray.get(i);

                    String jobId = (String) jsonObject.keys().next();
                    String authorityStr = jsonObject.getString(jobId);

                    WCClubJobBean jobBean = mJobMapper.getClubJobById(Long.parseLong(jobId));

                    if (!StringUtils.isEmpty(authorityStr)) {
                        List<WCClubAuthorityBean> authorities = new ArrayList<WCClubAuthorityBean>();

                        String[] authorityArray = authorityStr.split(",");
                        for (String anAuthorityArray : authorityArray) {
                            WCClubAuthorityBean authorityBean = mAuthorityMapper.getAuthorityById(Long.parseLong(anAuthorityArray));

                            if (authorityBean != null) {
                                authorities.add(authorityBean);
                            } else {
                                log.warn("getJobsByClubId：找不到 authorityId = " + anAuthorityArray + " 的权限");
                            }
                        }

                        jobBean.setAuthorities(authorities);
                    }
                    result.add(jobBean);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
