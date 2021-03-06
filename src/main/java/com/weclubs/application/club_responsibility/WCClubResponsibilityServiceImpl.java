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
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 部门职能的服务类
 *
 * Created by fangzanpan on 2017/3/10.
 */
@Service("clubResponsibilityService")
class WCClubResponsibilityServiceImpl implements WCIClubResponsibilityService {

    private Logger log = Logger.getLogger(WCClubResponsibilityServiceImpl.class);

    private WCClubMapper mClubMapper;
    private WCClubDepartmentMapper mDepartmentMapper;
    private WCClubJobMapper mJobMapper;
    private WCClubAuthorityMapper mAuthorityMapper;

    @Autowired
    public WCClubResponsibilityServiceImpl(WCClubDepartmentMapper mDepartmentMapper,
                                           WCClubJobMapper mJobMapper, WCClubAuthorityMapper mAuthorityMapper,
                                           WCClubMapper mClubMapper) {
        this.mDepartmentMapper = mDepartmentMapper;
        this.mJobMapper = mJobMapper;
        this.mAuthorityMapper = mAuthorityMapper;
        this.mClubMapper = mClubMapper;
    }

    public void createDepartment(WCClubDepartmentBean departmentBean) {

        if (departmentBean == null) {
            log.error("createDepartment：创建部门失败，department不能为空。");
            return;
        }

        if (StringUtils.isEmpty(departmentBean.getDepartmentName())) {
            log.error("createDepartment：创建部门失败，department.getName不能为空。");
            return;
        }

        WCClubDepartmentBean department = mDepartmentMapper.getClubDepartmentByName(departmentBean.getDepartmentName());

        if (department != null) {
            log.error("createDepartment：创建部门失败，【" + department.getDepartmentName() + "】在数据库中已经存在。");
            return;
        }

        mDepartmentMapper.createClubDepartment(departmentBean);
    }

    public void createJob(WCClubJobBean jobBean) {

        if (jobBean == null) {
            log.error("createJob：创建士炜失败，jobBean不能为空。");
            return;
        }

        if (StringUtils.isEmpty(jobBean.getJobName())) {
            log.error("createJob：创建职位失败，jobBean.getName不能为空。");
            return;
        }

        WCClubJobBean job = mJobMapper.getClubJobByJobName(jobBean.getJobName());

        if (job != null) {
            log.error("createJob：创建职位失败，【" + job.getJobName() + "】在数据库中已经存在。");
            return;
        }

        mJobMapper.createClubJob(jobBean);
    }

    public WCHttpStatus setDepartmentsByClubId(long clubId, List<WCClubDepartmentBean> departments) {

        WCHttpStatus check = WCHttpStatus.SUCCESS;

        if (clubId <= 0) {
            log.error("setDepartmentsByClubId：设置社团部门失败，clubId不能小于等于0。");

            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "club_id 不能小于等于0";
            return check;
        }

        WCClubBean clubBean = mClubMapper.getClubById(clubId);

        if (clubBean == null) {
            log.error("setDepartmentsByClubId：设置社团部门失败，找不到 id = " + clubId + " 的社团");

            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "找不到 id = 【" + clubId + "】的社团";
            return check;
        }

        String departmentsStr = "";


        List<String> list = new ArrayList<String>();
        if (departments != null && departments.size() > 0) {
            for (WCClubDepartmentBean department : departments) {
                String id = department.getDepartmentId() + "";
                departmentsStr += id + ",";
                if (!list.contains(id)) {
                    list.add(id);
                }
            }
        }

        log.info("setDepartmentByClubId：去重之前的 departmentsStr = " + departmentsStr);

        departmentsStr = "";

        // 对于数组去重处理
        for (int i = 0; i < list.size(); i++) {
            departmentsStr += list.get(i);

            if (i != (list.size() - 1)) {
                departmentsStr += ",";
            }
        }

        log.info("setDepartmentsByClubId：去重之后的 departmentsStr = " + departmentsStr);

        mDepartmentMapper.setCurrentClubDepartments(clubId, departmentsStr);

        return check;
    }

    public void setDepartmentsByClubId(long clubId, String ids) {

        if (clubId <= 0) {
            log.error("setDepartmentsByClubId：clubId不能小于等于0");
            return;
        }

        if (StringUtils.isEmpty(ids)) {
            log.error("setDepartmentsByClubId：ids不能为空");
            return;
        }

        String[] idArray = ids.split(",");
        List<WCClubDepartmentBean> departmentBeans = new ArrayList<WCClubDepartmentBean>();
        for (String s : idArray) {
            WCClubDepartmentBean departmentBean = getClubDepartmentById(Long.parseLong(s));
            departmentBeans.add(departmentBean);
        }

        setDepartmentsByClubId(clubId, departmentBeans);
    }

    public WCHttpStatus setNewDepartmentsByClubId(long clubId, String ids, String departments) {

        WCHttpStatus check = WCHttpStatus.SUCCESS;

        if (clubId <= 0) {
            log.error("setNewDepartmentsByClubId：clubId不能小于等于0");
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "club_id 不能小于等于0";
            return check;
        }

        List<WCClubDepartmentBean> departmentBeans = new ArrayList<WCClubDepartmentBean>();

        if (!StringUtils.isEmpty(ids)) {

            String[] idArray = ids.split(",");
            for (String s : idArray) {
                WCClubDepartmentBean departmentBean = getClubDepartmentById(Long.parseLong(s));
                departmentBeans.add(departmentBean);
            }
        } else {
            log.info("setDepartmentsByClubId：selected为空");
        }

        if (!StringUtils.isEmpty(departments)) {

            String[] names = departments.split(",");
            for (String name : names) {
                WCClubDepartmentBean departmentBean = mDepartmentMapper.getClubDepartmentByName(name);
                if (departmentBean == null) {
                    departmentBean = new WCClubDepartmentBean();
                    departmentBean.setDepartmentName(name);
                    mDepartmentMapper.createClubDepartment(departmentBean);
                    log.info("setNewDepartmentsByClubId：找不到【" + name + "】的部门，新建部门之后的id=" + departmentBean.getDepartmentId());
                }

                departmentBeans.add(departmentBean);
            }
        } else {
            log.info("setNewDepartmentsByClubId：departments为空");
        }

        if (departmentBeans.size() == 0) {
            log.error("setDepartmentsByClubId：设置的社团部门为空！");
        }

        return setDepartmentsByClubId(clubId, departmentBeans);
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

    public WCHttpStatus setNewJobByClubId(long clubId, org.json.JSONObject jobAuth) {

        WCHttpStatus check = WCHttpStatus.SUCCESS;

        if (clubId <= 0) {
            log.error("setNewJobByClubId：clubId不能小于等于0");
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "club_id 不能小于等于0";
            return check;
        }

        List<String> jobIdOrName = new ArrayList<String>();
        Iterator iterator = jobAuth.keys();
        while (iterator.hasNext()) {
            String idOrName = (String) iterator.next();
            if (!jobIdOrName.contains(idOrName)) {
                jobIdOrName.add(idOrName);
            }
        }

        org.json.JSONObject resultJsonObj = new org.json.JSONObject();

        List<WCClubJobBean> jobBeanList = new ArrayList<WCClubJobBean>();
        for (String s : jobIdOrName) {
            try {
                long id = Long.parseLong(s);
                WCClubJobBean jobBean = mJobMapper.getClubJobById(id);
                if (jobBean != null) {
                    jobBeanList.add(jobBean);
                    resultJsonObj.put(s, jobAuth.getString(s));
                } else {
                    log.info("setNewJobByClubId：找不到 id = 【" + s + "】的职位，无法添加");

                    check = WCHttpStatus.FAIL_REQUEST;
                    check.msg = "找不到 id = 【" + s + "】的职位，无法添加";
                    return check;
                }
            } catch (NumberFormatException e) {
                WCClubJobBean jobBean = mJobMapper.getClubJobByJobName(s);
                if (jobBean == null) {
                    jobBean = new WCClubJobBean();
                    jobBean.setJobName(s);
                    mJobMapper.createClubJob(jobBean);
                    log.info("setNewJobByClubId：找不到【" + s + "】的职位，新建职位之后的id=" + jobBean.getJobId());
                }
                try {
                    resultJsonObj.put(jobBean.getJobId() + "", jobAuth.get(s));
                } catch (JSONException e1) {
                    e1.printStackTrace();

                    check = WCHttpStatus.FAIL_REQUEST;
                    check.msg = "参数有误";
                    return check;
                }
                jobBeanList.add(jobBean);
            } catch (JSONException e) {
                e.printStackTrace();

                check = WCHttpStatus.FAIL_REQUEST;
                check.msg = "参数有误";
                return check;
            }
        }
        log.info("setNewJobByClubId：jobBeanList = " + jobBeanList.toString());
        log.info("setNewJobByClubId：resultJsonObj = " + resultJsonObj.toString());

        mJobMapper.setCurrentClubJobs(clubId, resultJsonObj.toString());
        return check;
    }

    public List<WCClubDepartmentBean> getDepartmentsBySuggest() {
        return mDepartmentMapper.getClubDepartmentBySuggest();
    }

    public List<WCClubJobBean> getJobsBySuggest() {
        return mJobMapper.getClubJobBySuggest();
    }

    public List<WCClubDepartmentBean> getDepartmentsByClubId(long clubId, boolean pureSelected) {

        if (clubId <= 0) {
            log.error("getDepartmentsByClubId：获取社团部门失败，clubId不能小于等于0。");
            return null;
        }

        if (mClubMapper.getClubById(clubId) == null) {
            log.error("getDepartmentsByClubId：找不到 id = " + clubId + " 的社团。");
            return null;
        }

        List<WCClubDepartmentBean> selected = new ArrayList<WCClubDepartmentBean>();
        String departmentsStr = mDepartmentMapper.getCurrentClubDepartmentsByClubId(clubId);
        List<WCClubDepartmentBean> suggestDepartments = getDepartmentsBySuggest();

        if (!StringUtils.isEmpty(departmentsStr)) {
            String[] depArray = departmentsStr.split(",");

            for (String s : depArray) {
                WCClubDepartmentBean departmentBean = mDepartmentMapper.getClubDepartmentById(Long.parseLong(s));
                departmentBean.setIsSelected(1);
                selected.add(departmentBean);
            }
        }

        if (pureSelected) {
            return selected;
        }

        List<WCClubDepartmentBean> resultArray = new ArrayList<WCClubDepartmentBean>();
        resultArray.addAll(selected);
        for (WCClubDepartmentBean suggestDepartment : suggestDepartments) {
            boolean isEquale = false;
            for (WCClubDepartmentBean departmentBean : selected) {
                if (departmentBean.getDepartmentId() == suggestDepartment.getDepartmentId()) {
                    isEquale = true;
                    break;
                }
            }
            if (!isEquale) {
                resultArray.add(suggestDepartment);
            }
        }

        return resultArray;
    }

    public List<WCClubJobBean> getJobsByClubId(long clubId, boolean pureSelected) {

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
                org.json.JSONObject jobJson = new org.json.JSONObject(jobsStr);
                Iterator iterator = jobJson.keys();
                while (iterator.hasNext()) {
                    String jobId = (String) iterator.next();
                    String authorityStr = jobJson.getString(jobId);

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
                        jobBean.setIsSelected(1);
                    }
                    result.add(jobBean);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        if (pureSelected) {
            return result;
        }

        List<WCClubJobBean> suggestJobs = mJobMapper.getClubJobBySuggest();
        if (suggestJobs != null && suggestJobs.size() > 0) {
            for (WCClubJobBean suggestJob : suggestJobs) {  // 这是推荐的列表
                boolean isEquale = false;
                for (WCClubJobBean jobBean : result) {  // 这是选中的列表
                    if (suggestJob.getJobId() == jobBean.getJobId()) {
                        isEquale = true;
                        break;
                    }
                }
                if (!isEquale) {
                    result.add(suggestJob);
                }
            }
        }

        return result;
    }

    public WCClubDepartmentBean getClubDepartmentById(long departmentId) {

        if (departmentId <= 0) {
            log.error("getClubDepartmentById：departmentId不能小于等于0");
            return null;
        }

        return mDepartmentMapper.getClubDepartmentById(departmentId);
    }

    public WCClubJobBean getClubJobById(long jobId) {

        if (jobId <= 0) {
            log.error("getClubJobById：jobId不能小于等于0");
            return null;
        }

        return mJobMapper.getClubJobById(jobId);
    }

    public List<WCClubAuthorityBean> getAllAuthority() {
        return mAuthorityMapper.getAuthorities();
    }
}
