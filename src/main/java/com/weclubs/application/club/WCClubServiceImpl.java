package com.weclubs.application.club;

import com.weclubs.application.club_responsibility.WCIClubResponsibilityService;
import com.weclubs.application.message.WCIMessageService;
import com.weclubs.application.rongcloud.WCIRongCloudService;
import com.weclubs.application.rongcloud.WCRongCloudServiceImpl;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.*;
import com.weclubs.mapper.WCClubHonorMapper;
import com.weclubs.mapper.WCClubMapper;
import com.weclubs.mapper.WCDynamicMapper;
import com.weclubs.model.*;
import com.weclubs.util.PinYinComparator;
import com.weclubs.util.WCHttpStatus;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    private WCClubMapper mClubMapper;
    private WCClubHonorMapper mClubHonorMapper;
    private WCDynamicMapper mDynamicMapper;

    private WCIClubGraduateService mClubGraduateService;
    private WCIUserService mUserService;
    private WCIClubResponsibilityService mClubResponsibilityService;
    private WCIRongCloudService mRongCloudService;
    private WCIMessageService mMessageService;

    @Autowired
    public WCClubServiceImpl(WCDynamicMapper mDynamicMapper, WCClubMapper mClubMapper,
                             WCIUserService mUserService, WCIClubResponsibilityService mClubResponsibilityService,
                             WCClubHonorMapper mClubHonorMapper, WCIClubGraduateService mClubGraduateService,
                             WCIRongCloudService rongCloudService, WCIMessageService messageService) {
        this.mDynamicMapper = mDynamicMapper;
        this.mClubMapper = mClubMapper;
        this.mUserService = mUserService;
        this.mClubResponsibilityService = mClubResponsibilityService;
        this.mClubHonorMapper = mClubHonorMapper;
        this.mClubGraduateService = mClubGraduateService;
        this.mRongCloudService = rongCloudService;
        this.mMessageService = messageService;
    }

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

                WCHttpStatus check = mRongCloudService.createGroupChat(clubBean.getClubId());
                log.info("createClub：创建群聊结果 = " + check.toString());

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

    public ArrayList<HashMap<String, Object>> getStudentsByCurrentGraduate(long clubId, int sortType) {

        if (clubId <= 0) {
            log.error("getStudentsByCurrentGraduate：clubId 不能小于等于 0。");
            return null;
        }

        List<WCClubStudentBean> listStudent = new ArrayList<WCClubStudentBean>();

        listStudent = mClubMapper.getCurrentGraduateStudentsBySortStuName(clubId);

        ArrayList<HashMap<String, Object>> studentsMap = new ArrayList<HashMap<String, Object>>();
        if (listStudent != null) {
            for (WCClubStudentBean student : listStudent) {
                studentsMap.add(student.getCommonStudent());
            }
        }

        if (sortType == SORT_BY_REAL_NAME) {    //根据学生真实姓名首字母排序
            return getStudentSortByPinyin(studentsMap);
        } else if (sortType == SORT_BY_JOB){
            return getStudentSortByJob(studentsMap);
        } else if (sortType == SORT_BY_DEPARTMENT) {
            return getStudentSortByDepartment(studentsMap);
        } else if (sortType == SORT_BY_GRADUATE) {
            return getStudentSortByGraduate(studentsMap);
        }

        // 如果没有排序规则，则默认按照名字首字母进行排序
        return getStudentSortByPinyin(studentsMap);
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

            if (clubGraduateRelationBean != null && clubGraduateRelationBean.getDepartmentId() > 0) {
                WCClubDepartmentBean departmentBean
                        = mClubResponsibilityService.getClubDepartmentById(clubGraduateRelationBean.getDepartmentId());
                if (departmentBean != null) {
                    result.setDepartmentId(departmentBean.getDepartmentId());
                    result.setDepartmentName(departmentBean.getDepartmentName());
                }
            }

            if (clubGraduateRelationBean != null && clubGraduateRelationBean.getJobId() > 0) {
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

    public void addClubHonor(long clubId, List<HashMap<String, Object>> honorList) {

        if (clubId <= 0) {
            log.error("addClubHonor：clubId不能小于扥估0");
            return;
        }

        if (honorList == null || honorList.size() == 0) {
            log.error("addClubHonor：荣誉列表不能为空");
            return;
        }

        List<WCClubHonorBean> list = new ArrayList<WCClubHonorBean>();
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

    public void updateClubHonor(List<HashMap<String, Object>> honorList) {

        if (honorList == null || honorList.size() == 0) {
            log.error("updateClubHonor：更新荣誉列表失败，不允许荣誉列表为空");
            return;
        }

        List<WCClubHonorBean> honorBeanList = new ArrayList<WCClubHonorBean>();
        for (HashMap<String, Object> result : honorList) {
            WCClubHonorBean honorBean = new WCClubHonorBean();
            honorBean.setContent((String) result.get("content"));

            long clubId = 0;
            if (result.get("club_id") instanceof String) {
                clubId = Long.parseLong((String) result.get("club_id"));
            } else if (result.get("club_id") instanceof Integer) {
                clubId = (Integer) result.get("club_id");
            }
            honorBean.setClubId(clubId);

            long getDate = 0;
            if (result.get("get_date") instanceof String) {
                getDate = Long.parseLong((String) result.get("get_date"));
            } else if (result.get("get_date") instanceof Long) {
                getDate = (Long) result.get("get_date");
            }
            honorBean.setGetDate(getDate);

            honorBeanList.add(honorBean);
        }
        log.info("updateClubHonor：荣誉实体列表为 -- " + honorList.toString());

        for (WCClubHonorBean honorBean : honorBeanList) {
            mClubHonorMapper.updateClubHonor(honorBean);
        }
    }

    public boolean checkClubExit(String clubName, long schoolId) {
        List<WCClubBean> list = getClubsBySchoolId(schoolId);

        if (list == null || list.size() == 0) {
            log.info("checkClubExit：schoolId = " + schoolId + " 社团列表为空。");
            return false;
        }

        boolean isExit = false;
        for (WCClubBean clubBean : list) {
            if (clubName.equals(clubBean.getName())) {
                isExit = true;
                break;
            }
        }
        return isExit;
    }

    public WCHttpStatus setClubAvatar(String avatarUrl, long clubId) {
        WCHttpStatus check = WCHttpStatus.SUCCESS;

        if (StringUtils.isEmpty(avatarUrl)) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "头像地址不能为空";
            return check;
        }

        WCClubBean clubBean = getClubInfoById(clubId);
        if (clubBean == null) {
            check = WCHttpStatus.FAIL_REQUEST;
            check.msg = "社团 id 【" + clubId + "】不存在";
            return check;
        }

        clubBean.setAvatarUrl(avatarUrl);
        updateClub(clubBean);

        return check;
    }

    @Override
    public List<WCClubStudentBean> getCurrentGraduateStudentsByClubId(long clubId) {
        WCClubBean clubBean = getClubInfoById(clubId);
        if (clubBean == null) {
            log.error("找不到该社团");
            return null;
        }

        return mClubMapper.getCurrentGraduateStudents(clubId);
    }

    @Override
    public boolean checkStudentExitCurrentGraduate(long studentId, long clubId) {
        List<WCClubStudentBean> members = getCurrentGraduateStudentsByClubId(clubId);
        if (members == null || members.size() == 0) {
            log.error("该社团当前届人数为0");
            return false;
        }

        for (WCClubStudentBean member : members) {
            if (studentId == member.getStudentId()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<WCClubBean> searchClubList(long schoolId, String... keywords) {
        String k = "%" + keywords[0] +  "%";
        return mClubMapper.searchClubListByKeyword(schoolId, k);
    }

    @Override
    public List<HashMap<String, Object>> getHotClubBySchool(long schoolId) {
        List<WCClubBean> clubList = getClubsBySchoolId(schoolId);

        if (clubList != null && clubList.size() > 0) {
            int maxSize = clubList.size() > 6 ? 6 : clubList.size();
            ArrayList<HashMap<String, Object>> result = new ArrayList<>();
            for (int i = 0; i < maxSize; i++) {
                HashMap<String, Object> hash = new HashMap<>();
                hash.put("club_id", clubList.get(i).getClubId());
                hash.put("club_name", clubList.get(i).getName());
                hash.put("avatar_url", clubList.get(i).getAvatarUrl());
                result.add(hash);
            }
            return result;
        }
        return null;
    }

    @Override
    public WCHttpStatus applyForClub(long userId, long clubId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        WCStudentBean studentBean = mUserService.getUserInfoById(userId);
        if (studentBean == null) {
            check.msg = "找不到该学生";
            return check;
        }

        WCClubBean clubBean = getClubInfoById(clubId);
        if (clubBean == null) {
            check.msg = "找不到该社团";
            return check;
        }

        List<WCClubStudentBean> students = getSAByCurrentGraduateClub(clubId);
        if (students == null || students.size() == 0) {
            check.msg = "该社团没有人可以负责成员审批";
            return check;
        }

        WCApplyIntoClubMessageModel messageModel = getNotifyMsgBean("student", clubBean, studentBean, students, 0);

        String[] toUserIds = new String[students.size()];
        for (int i = 0; i < students.size(); i++) {
            toUserIds[i] = WCRongCloudServiceImpl.getRongUserId(students.get(i).getStudentId());
        }

        mRongCloudService.publicApplyClubMsg(messageModel, toUserIds);

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    @Override
    public WCHttpStatus resolveApply(long clubId, long studentId, long userId, long messageId, int opinion) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        WCStudentMessageRelationBean messageRelationBean = mMessageService.getMsgDetailByMsgIdAndStuId(studentId, messageId);
//        if (messageRelationBean == null) {
//            check.msg = "找不到该申请消息";
//            return check;
//        }

        if (messageRelationBean != null) {
            messageRelationBean.setStatus(1);
            // TODO: 2017/7/18 需要更新状态
        }

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean == null) {
            check.msg = "找不到该学生";
            return check;
        }

        WCStudentBean userBean = mUserService.getUserInfoById(userId);
        if (userBean == null) {
            check.msg = "找不到该管理员";
            return check;
        }

        WCClubBean clubBean = getClubInfoById(clubId);
        if (clubBean == null) {
            check.msg = "找不到该社团";
            return check;
        }

        WCClubStudentBean adminUserBean = new WCClubStudentBean();
        adminUserBean.setStudentId(userBean.getStudentId());
        adminUserBean.setRealName(userBean.getRealName());
        adminUserBean.setNickName(userBean.getNickName());
        adminUserBean.setAvatarUrl(userBean.getAvatarUrl());
        List<WCClubStudentBean> adminList = new ArrayList<>();
        adminList.add(adminUserBean);

        if (opinion == 1) {
            check = addStudentIntoClub(studentId, clubId, userId);

            if (check == WCHttpStatus.SUCCESS) {
                /*
                  发送系统通知给用户操作结果
                */
                String[] toUserIds = new String[] {WCRongCloudServiceImpl.getRongUserId(studentId)};
                WCApplyIntoClubMessageModel messageModel = getNotifyMsgBean("club", clubBean, studentBean, adminList, opinion);
                mRongCloudService.publicApplyClubMsg(messageModel, toUserIds);
            }
        } else {
            /*
              发送系统通知拒绝加入社团给用户操作结果
            */
            String[] toUserIds = new String[] {WCRongCloudServiceImpl.getRongUserId(studentId)};
            WCApplyIntoClubMessageModel messageModel = getNotifyMsgBean("club", clubBean, studentBean, adminList, opinion);
            mRongCloudService.publicApplyClubMsg(messageModel, toUserIds);
        }

        return check;
    }

    @Override
    public List<WCClubStudentBean> getSAByCurrentGraduateClub(long clubId) {

        WCClubBean clubBean = getClubInfoById(clubId);
        if (clubBean == null) {
            log.error("该社团不存在");
            return null;
        }

        return mClubMapper.getCurrentGraduateGA(clubId);
    }

    @Override
    public WCHttpStatus addStudentIntoClub(long studentId, long clubId, long userId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        WCClubBean clubBean = getClubInfoById(clubId);
        if (clubBean == null) {
            check.msg = "找不到该社团";
            return check;
        }

        WCClubGraduateBean graduateBean = mClubGraduateService.getCurrentClubGraduate(clubId);
        if (graduateBean == null) {
            check.msg = "找不到当前届社团";
            return check;
        }

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean == null) {
            check.msg = "找不到该学生";
            return check;
        }

        WCStudentBean userBean = mUserService.getUserInfoById(userId);
        if (userBean == null) {
            check.msg = "找不到该管理员";
            return check;
        }

        WCStudentClubGraduateRelationBean DBRelationBean
                = mClubGraduateService.getStudentClubGraduationRelationByGraduateId(studentId, graduateBean.getClubGraduateId());
        if (DBRelationBean == null) {
            check.msg = "该学生已经加入该社团";
            return check;
        }

        WCStudentClubGraduateRelationBean relationBean = new WCStudentClubGraduateRelationBean();
        relationBean.setGraduateId(graduateBean.getClubGraduateId());
        relationBean.setStatus(1);
        relationBean.setStudentId(studentId);
        relationBean.setIsDel(0);

        mClubGraduateService.createStuCluGraduateRelation(relationBean);
        check = WCHttpStatus.SUCCESS;

        return check;
    }

    /**
     * 根据真实姓名首字母进行排序
     *
     * @param studentsMap   学生的键值对列表
     * @return  排序后的根据ABCD。。。排序的学生键值对
     */
    private ArrayList<HashMap<String, Object>> getStudentSortByPinyin(ArrayList<HashMap<String, Object>> studentsMap) {
        String english = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] AZ = english.split("");
        HashMap<String, ArrayList<HashMap<String, Object>>> resultMap = new HashMap<String, ArrayList<HashMap<String, Object>>>();
        for (String s : AZ) {
            resultMap.put(s, new ArrayList<HashMap<String, Object>>());
        }
        resultMap.put("*", new ArrayList<HashMap<String, Object>>());

        PinYinComparator comparator = new PinYinComparator();
        for (HashMap<String, Object> stringObjectHashMap : studentsMap) {
            for (String s : AZ) {
                String studentName = (String) stringObjectHashMap.get("name");
                if (StringUtils.isEmpty(studentName)) {
                    ((ArrayList<HashMap<String, Object>>) resultMap.get("*")).add(stringObjectHashMap);
                    break;
                } else {
                    String[] args = PinyinHelper.toGwoyeuRomatzyhStringArray(studentName.charAt(0));
                    if (args.length > 0) {
                        log.info("s.toLowerCase() = " + s.toLowerCase(Locale.getDefault()) + "; args[0] = " + args[0]
                                + "; char = " + ((String) stringObjectHashMap.get("name")).charAt(0));
                        if (s.toLowerCase(Locale.getDefault()).equals(args[0].split("")[0])) {
                            ((ArrayList<HashMap<String, Object>>) resultMap.get(s)).add(stringObjectHashMap);
                            break;
                        }
                    } else {
                        ((ArrayList<HashMap<String, Object>>) resultMap.get("*")).add(stringObjectHashMap);
                        break;
                    }
                }
            }
        }

        return getStudentsSort(resultMap);
    }

    /**
     * 根据学生职位进行排序
     *
     * @param studentsMap   学生数组列表
     * @return  排序后的列表
     */
    private ArrayList<HashMap<String, Object>> getStudentSortByJob(ArrayList<HashMap<String, Object>> studentsMap) {
        HashMap<String, ArrayList<HashMap<String, Object>>> jobKeys = new HashMap<String, ArrayList<HashMap<String, Object>>>();

        if (studentsMap != null && studentsMap.size() > 0) {
            for (HashMap<String, Object> studentHash : studentsMap) {
                if (jobKeys.containsKey((String) studentHash.get("job"))) {
                    jobKeys.get(studentHash.get("job")).add(studentHash);
                } else {
                    if (StringUtils.isEmpty((String) studentHash.get("department"))) {
                        if (jobKeys.containsKey("*")) {
                            jobKeys.get(studentHash.get("*")).add(studentHash);
                        } else {
                            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
                            arrayList.add(studentHash);
                            jobKeys.put("*", arrayList);
                        }
                    } else {
                        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
                        arrayList.add(studentHash);
                        jobKeys.put((String) studentHash.get("job"), arrayList);
                    }
                }
            }
        }

        return getStudentsSort(jobKeys);
    }

    private ArrayList<HashMap<String, Object>> getStudentSortByDepartment(ArrayList<HashMap<String, Object>> studentsMap) {
        HashMap<String, ArrayList<HashMap<String, Object>>> jobKeys = new HashMap<String, ArrayList<HashMap<String, Object>>>();

        if (studentsMap != null && studentsMap.size() > 0) {
            for (HashMap<String, Object> studentHash : studentsMap) {
                if (jobKeys.containsKey((String) studentHash.get("department"))) {
                    jobKeys.get(studentHash.get("department")).add(studentHash);
                } else {
                    if (StringUtils.isEmpty((String) studentHash.get("department"))) {
                        if (jobKeys.containsKey("*")) {
                            jobKeys.get(studentHash.get("*")).add(studentHash);
                        } else {
                            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
                            arrayList.add(studentHash);
                            jobKeys.put("*", arrayList);
                        }
                    } else {
                        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
                        arrayList.add(studentHash);
                        jobKeys.put((String) studentHash.get("department"), arrayList);
                    }
                }
            }
        }

        return getStudentsSort(jobKeys);
    }

    private ArrayList<HashMap<String, Object>> getStudentSortByGraduate(ArrayList<HashMap<String, Object>> studentsMap) {
        HashMap<String, ArrayList<HashMap<String, Object>>> jobKeys = new HashMap<String, ArrayList<HashMap<String, Object>>>();

        if (studentsMap != null && studentsMap.size() > 0) {
            for (HashMap<String, Object> studentHash : studentsMap) {
                if (jobKeys.containsKey(String.valueOf(studentHash.get("graduate_year")))) {
                    jobKeys.get(String.valueOf(studentHash.get("graduate_year"))).add(studentHash);
                } else {
                    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
                    arrayList.add(studentHash);
                    jobKeys.put(String.valueOf(studentHash.get("graduate_year")), arrayList);
                }
            }
        }

        return getStudentsSort(jobKeys);
    }

    private ArrayList<HashMap<String, Object>> getStudentsSort(HashMap<String, ArrayList<HashMap<String, Object>>> studentHashList) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

        Set set = studentHashList.entrySet();
        for (Object aSet : set) {
            HashMap<String, Object> item = new HashMap<String, Object>();

            Map.Entry entry = (Map.Entry) aSet;
            ArrayList<HashMap<String, Object>> students = (ArrayList<HashMap<String, Object>>) entry.getValue();
            if (students != null && students.size() > 0) {
                item.put("group_name", entry.getKey());
                item.put("students", entry.getValue());
                result.add(item);
            }
        }
        return result;
    }

    /**
     * 生成 "add_new_user" 的消息类型
     *
     * @param userType  用户类型，student、club两种，如果是申请加入社团，则为student类型；如果是审批申请的则为club类型
     * @param clubBean  社团实体类
     * @param studentBean   如果是student类型的话，该studentBean是用于显示的，如果是club类型的话，是用于接收消息的
     * @param admins        如果是student类型的话，该admins是用于接收消息的，如果是club类型的的话，admins只有一个，且admins.get(0)是用于显示content的
     * @param status        如果是student的话，status恒为0，如果是club类型的话，由外面传进来
     * @return  生成消息类型
     */
    private WCApplyIntoClubMessageModel getNotifyMsgBean(String userType, WCClubBean clubBean, WCStudentBean studentBean,
                                                         List<WCClubStudentBean> admins, int status) {

        String title = "";
        String content = "";
        if ("student".equals(userType)) {
            title = "申请加入社团";
            content = clubBean.getName();
        } else if ("club".equals(userType)) {
            title = "申请加入社团批复";

            String adminName = !StringUtils.isEmpty(admins.get(0).getRealName()) ? admins.get(0).getRealName() : admins.get(0).getNickName();
            if (status == 0) {
                content = "【" + adminName + "】拒绝您的申请";
            } else if (status == 1) {
                content = "【" + adminName + "】同意您的申请";
            }
        }

        WCMessageBean messageBean = new WCMessageBean();
        messageBean.setTitle(title);
        messageBean.setContent(content);
        messageBean.setIsDel(0);

        List<Long> msgUserId = new ArrayList<>();
        if ("student".equals(userType)) {
            for (WCStudentBean student : admins) {
                msgUserId.add(student.getStudentId());
            }
            messageBean.setType(1);
        } else if ("club".equals(userType)) {
            msgUserId.add(studentBean.getStudentId());
            messageBean.setType(0);
        }

        mMessageService.publicMessage(messageBean, msgUserId);

        if (messageBean.getMessageId() == 0) {
            log.error("getNotifyMsgBean：message.getMessageId = 0");
            return null;
        }

        WCApplyIntoClubMessageModel messageModel = new WCApplyIntoClubMessageModel();
        messageModel.setTitle(title);
        messageModel.setContent(content);
        messageModel.setMessage_type("add_new_user");
        if ("student".equals(userType)) {
            messageModel.setUser_id(studentBean.getStudentId());
            messageModel.setUser_name(!StringUtils.isEmpty(studentBean.getRealName()) ? studentBean.getRealName() : studentBean.getNickName());
            messageModel.setUser_avatar(studentBean.getAvatarUrl());
        } else if ("club".equals(userType)) {
            messageModel.setUser_id(clubBean.getClubId());
            messageModel.setUser_name(clubBean.getName());
            messageModel.setUser_avatar(clubBean.getAvatarUrl());
        }
        messageModel.setUser_type(userType);
        WCApplyIntoClubMessageModel.ExtraBean extra = new WCApplyIntoClubMessageModel.ExtraBean();
        extra.setClub_id(clubBean.getClubId());
        extra.setStatus(0);
        extra.setMessage_id(messageBean.getMessageId());
        messageModel.setExtra(extra);
        messageModel.setSponsor_date(System.currentTimeMillis());

        messageBean.setData(messageModel.toString());
        mMessageService.updateMsg(messageBean);

        return messageModel;
    }
}
