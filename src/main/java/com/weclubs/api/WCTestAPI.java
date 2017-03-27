package com.weclubs.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.club_responsibility.WCIClubResponsibilityService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubAuthorityBean;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubJobBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 测试接口
 *
 * Created by fangzanpan on 2017/3/8.
 */
@RestController
@RequestMapping(value = "/test")
public class WCTestAPI {

    private Logger log = Logger.getLogger(WCTestAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIClubService mClubService;
    @Autowired
    private WCINotificationService mNotificationService;
    @Autowired
    private WCIClubResponsibilityService mResponsibilityService;

    @RequestMapping(value = "/getClubsBySchoolId", method = RequestMethod.GET)
    public WCResultData getClubsBySchoolId() {

        log.info("getClubsBySchoolId：获取 schoolId = 1 的学校所有社团");

        long schoolId = 1;

        List<WCClubBean> clubs = mClubService.getClubsBySchoolId(schoolId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("clubs", clubs);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "getClubDetailById", method = RequestMethod.GET)
    public WCResultData getClubDetailById () {

        log.info("getClubDetailById：获取 clubId = 1 的社团详情");

        long clubId = 1;

        WCClubBean clubBean = mClubService.getClubInfoById(clubId);

        return WCResultData.getSuccessData(clubBean);
    }

    @RequestMapping(value = "/getClubsBySchoolId", method = RequestMethod.POST)
    public WCResultData getClubsBySchoolIdPost(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus checkSecurity = mSecurityService.checkRequestParams(requestModel);

        if (checkSecurity != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(checkSecurity, null);
        }

        HashMap<String, Object> requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

        if (requestParams == null) {
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }

        try {

            log.info("getClubsBySchoolId：获取 schoolId = 1 的学校所有社团");

            long schoolId = Long.parseLong((String) requestParams.get("school_id"));

            List<WCClubBean> clubs = mClubService.getClubsBySchoolId(schoolId);

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("clubs", clubs);

            return WCResultData.getSuccessData(result);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }
    }

    @RequestMapping(value = "/getNotificationsByClubId")
    public WCResultData getNotificationsByClubId(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus chekSecurity = mSecurityService.checkRequestParams(requestModel);
        if (chekSecurity != WCHttpStatus.SUCCESS) {
            log.error("getNotificationByClubId：请求参数违法");
            return WCResultData.getHttpStatusData(chekSecurity, null);
        }

        HashMap<String, Object> requestParams = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

        if (requestParams == null) {
            log.error("getNotificationByClubId：请求参数为空");
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }

        try {

            long studentId = Long.parseLong((String) requestParams.get("student_id"));

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("notification", mNotificationService.getNotificationsByStudentId(studentId));

            return WCResultData.getSuccessData(result);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
        }
    }

    @RequestMapping(value = "/setJobsByClubId")
    public String setJobsByClubId() {

        List<WCClubJobBean> jobs = new ArrayList<WCClubJobBean>();


        WCClubAuthorityBean auth1 = new WCClubAuthorityBean();
        auth1.setClubAuthorityId(1);
        WCClubAuthorityBean auth2 = new WCClubAuthorityBean();
        auth2.setClubAuthorityId(2);
        WCClubAuthorityBean auth3 = new WCClubAuthorityBean();
        auth3.setClubAuthorityId(3);
        WCClubAuthorityBean auth4 = new WCClubAuthorityBean();
        auth4.setClubAuthorityId(4);

        WCClubJobBean job1 = new WCClubJobBean();
        job1.setJobId(1);
        List<WCClubAuthorityBean> list1 = new ArrayList<WCClubAuthorityBean>();
        list1.add(auth1);
        list1.add(auth2);
        job1.setAuthorities(list1);
        jobs.add(job1);

        WCClubJobBean job2 = new WCClubJobBean();
        job2.setJobId(2);
        List<WCClubAuthorityBean> list2 = new ArrayList<WCClubAuthorityBean>();
        list2.add(auth1);
        list2.add(auth3);
        list2.add(auth4);
//        job2.setAuthorities(list2);
        jobs.add(job2);

        WCClubJobBean job3 = new WCClubJobBean();
        job3.setJobId(3);
        List<WCClubAuthorityBean> list3 = new ArrayList<WCClubAuthorityBean>();
        list3.add(auth1);
        list3.add(auth3);
        list3.add(auth4);
        job3.setAuthorities(list3);
        jobs.add(job3);

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

        return jobStr;

    }

    @RequestMapping(value = "/getClubJobByClubId")
    public WCResultData getClubJobByClubId(@RequestParam(value = "club_id") long clubId) {

        List<WCClubJobBean> jobs = mResponsibilityService.getJobsByClubId(clubId);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("club_id", clubId);
        result.put("jobs", jobs);

        return WCResultData.getSuccessData(result);
    }
}
