package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.club.WCClubServiceImpl;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.model.WCMyClubModel;
import com.weclubs.model.WCRequestModel;
import com.weclubs.model.WCResultData;
import com.weclubs.util.PinYinComparator;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 社团相关接口
 *
 * Created by fangzanpan on 2017/3/11.
 */
@RestController
@RequestMapping(value = "/club")
class WCClubAPI {

    private Logger log = Logger.getLogger(WCClubAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubService mClubService;

    @Autowired
    public WCClubAPI(WCIClubService mClubService, WCISecurityService mSecurityService) {
        this.mClubService = mClubService;
        this.mSecurityService = mSecurityService;
    }

    @RequestMapping(value = "/get_club_detail")
    public WCResultData getClubDetail(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus security = mSecurityService.checkRequestParams(requestModel);
        if (security != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(security, null);
        }

        security = mSecurityService.checkTokenAvailable(requestModel);
        if (security != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(security, null);
        }

        try {
            HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

            if (requestData == null || requestData.size() == 0) {
                log.error("getClubDetail：请求参数data为空");
                return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
            }

            long clubId = Long.parseLong((String) requestData.get("club_id"));

            WCClubBean clubBean = mClubService.getClubInfoById(clubId);
            if (clubBean == null) {
                WCHttpStatus clubNullError = WCHttpStatus.FAIL_CUSTOM_DAILOG_AND_CLOSE;
                clubNullError.msg = "该社团不存在";
                log.error("getClubDetail：找不到 id = " + clubId + " 的社团");
                return WCResultData.getHttpStatusData(clubNullError, null);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result = getClubBaseInfo(clubBean, result);

            result.put("club_level", clubBean.getLevel());
            result.put("slogan", clubBean.getSlogan());
            result.put("attribution", clubBean.getIntroduction());

            result.put("member_count", "10");
            result.put("member", null);

            result.put("activity_count", "9");
            result.put("activity", null);

            List<WCClubHonorBean> honors = mClubService.getClubHonorByClubId(clubId);
            result.put("club_honor", getClubHonorList(honors));

            return WCResultData.getSuccessData(result);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }
    }

    @RequestMapping(value = "/get_clubs_by_suggest")
    public WCResultData getClubsBySuggest(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getClubsBySuggest：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCClubBean> suggestClubs = mClubService.getClubsBySchoolId(1);
        PageInfo<WCClubBean> pageInfo = new PageInfo<WCClubBean>(suggestClubs);

        List<HashMap<String, Object>> resultSuggest = new ArrayList<HashMap<String, Object>>();
        for (WCClubBean suggestClub : pageInfo.getList()) {
            HashMap<String, Object> club = new HashMap<String, Object>();
            club = getClubBaseInfo(suggestClub, club);
            resultSuggest.add(club);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("club", resultSuggest);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_clubs_by_owner")
    public WCResultData getClubsByOwner(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getClubsByOwner：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestParams == null || requestParams.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        studentId = 2;

        PageHelper.startPage(pageNo, pageSize);
        List<WCClubBean> clubs = mClubService.getClubsByStudentId(studentId);
        PageInfo<WCClubBean> pageInfo = new PageInfo<WCClubBean>(clubs);

        ArrayList<HashMap<String, Object>> ownerClubs = new ArrayList<HashMap<String, Object>>();
        for (WCClubBean club : pageInfo.getList()) {
            HashMap<String, Object> clubHash = new HashMap<String, Object>();
            clubHash = getClubBaseInfo(club, clubHash);
            clubHash.put("member_count", "10");
            clubHash.put("club_level", club.getLevel());
            clubHash.put("todo_count", "10");
            clubHash.put("activity_count", "2");
            ownerClubs.add(clubHash);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club", ownerClubs);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_club_students", method = RequestMethod.POST)
    public WCResultData getStudentsOfClubByCurrentGraduate(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            log.error("getStudentsOfClubByCurrentGraduate：请求参数违法");
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            check = WCHttpStatus.FAIL_REQUEST_NULL_PARAMS;
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = WCRequestParamsUtil.getRequestId(requestModel);
        long clubId = Long.parseLong((String) requestData.get("club_id"));

        int sortType = WCClubServiceImpl.SORT_BY_REAL_NAME; // 默认排序为按照真实姓名
        if (requestData.containsKey("sort_type")) {
            if (requestData.get("sort_type") instanceof Integer) {
                sortType = (Integer) requestData.get("sort_type");
            } else if (requestData.get("sort_type") instanceof String) {
                sortType = Integer.parseInt((String) requestData.get("sort_type"));
            }
        }

        List<WCClubStudentBean> students = mClubService.getStudentsByCurrentGraduate(clubId, sortType);
        ArrayList<HashMap<String, Object>> studentsMap = new ArrayList<HashMap<String, Object>>();
        if (students != null) {
            for (WCClubStudentBean student : students) {
                studentsMap.add(getCommonStudent(student));
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("sort_type", sortType);
        result.put("student", commonStudentSortByPinyin(studentsMap));
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_my_clubs")
    public WCResultData getMyClubs(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        long studentId = WCRequestParamsUtil.getUserId(requestModel);
        // TODO: 2017/3/26
        studentId = 1;
        List<WCMyClubModel> myClubs = mClubService.getMyClubs(studentId);

        ArrayList<HashMap<String, Object>> myClubHash = new ArrayList<HashMap<String, Object>>();
        if (myClubs != null && myClubs.size() > 0) {
            for (WCMyClubModel myClub : myClubs) {
                HashMap<String, Object> hash = new HashMap<String, Object>();
                hash = getClubBaseInfo(myClub, hash);
                hash.put("member_count", myClub.getMemberCount());
                hash.put("todo_count", myClub.getTodoCount());
                hash.put("activity_count", myClub.getActivityCount());
                hash.put("level", myClub.getLevel());

                myClubHash.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club", myClubHash);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getClubBaseInfo(WCClubBean clubBean, HashMap<String, Object> result) {
        result.put("club_id", clubBean.getClubId());
        result.put("club_name", clubBean.getName());
        result.put("avatar_url", clubBean.getAvatarUrl());
        return result;
    }

    private ArrayList<HashMap<String, Object>> getClubHonorList(List<WCClubHonorBean> honors) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for (WCClubHonorBean honor : honors) {
            HashMap<String, Object> honorHash = new HashMap<String, Object>();
            honorHash.put("get_date", honor.getGetDate());
            honorHash.put("name", honor.getContent());
            result.add(honorHash);
        }
        return result;
    }

    private HashMap<String, Object> getCommonStudent(WCClubStudentBean studentBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("student_id", studentBean.getStudentId());
        result.put("name", !StringUtils.isEmpty(studentBean.getRealName()) ? studentBean.getRealName() : studentBean.getNickName());
        result.put("department", studentBean.getDepartmentBean() != null ? studentBean.getDepartmentBean().getDepartmentName() : null);
        result.put("job", studentBean.getJobBean() != null ? studentBean.getJobBean().getJobName() : null);
        result.put("mobile", studentBean.getMobile());
        result.put("avatar_url", studentBean.getAvatarUrl());
        result.put("major", studentBean.getGraduateYear() + "-"
                + (studentBean.getSchoolBean() != null ? studentBean.getSchoolBean().getName() : null));
        return result;
    }

    /**
     * 根据真实姓名首字母进行排序
     *
     * @param studentsMap   学生的键值对列表
     * @return  排序后的根据ABCD。。。排序的学生键值对
     */
    private HashMap<String, Object> commonStudentSortByPinyin(ArrayList<HashMap<String, Object>> studentsMap) {
        String english = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] AZ = english.split("");
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        for (String s : AZ) {
            resultMap.put(s, new ArrayList<HashMap<String, Object>>());
        }
        resultMap.put("*", new ArrayList<HashMap<String, Object>>());

        PinYinComparator comparator = new PinYinComparator();
        for (HashMap<String, Object> stringObjectHashMap : studentsMap) {
            for (String s : AZ) {
                String[] args = PinyinHelper.toGwoyeuRomatzyhStringArray(((String) stringObjectHashMap.get("name")).charAt(0));
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

        return resultMap;
    }
}
