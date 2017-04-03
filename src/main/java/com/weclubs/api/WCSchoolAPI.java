package com.weclubs.api;

import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCSchoolBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 学校的接口（包括学校、院系选择）
 *
 * Created by fangzanpan on 2017/4/1.
 */
@RestController
@RequestMapping(value = "/school")
class WCSchoolAPI {

    private Logger log = Logger.getLogger(WCSchoolAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCISchoolService mSchoolService;

    @RequestMapping(value = "get_school_list")
    public WCResultData getSchoolList(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        List<WCSchoolBean> schoolList = mSchoolService.getSchools();
        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        for (WCSchoolBean schoolBean : schoolList) {
            HashMap<String, Object> hash = getSchoolHash(schoolBean);
            resultArray.add(hash);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("school", resultArray);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "get_college_list")
    public WCResultData getCollegeList(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
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

        long schoolId = 0;
        if (requestData.get("school_id") instanceof String) {
            schoolId = Long.parseLong((String) requestData.get("school_id"));
        } else if (requestData.get("school_id") instanceof Integer) {
            schoolId = (Integer) requestData.get("school_id");
        }

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(schoolId);
        if (schoolBean == null) {
            check = WCHttpStatus.FAIL_APPLICATION_ERROR;
            log.error("getCollegeList：找不到该学校，schoolId = " + schoolId);
            return WCResultData.getHttpStatusData(check, null);
        }
        List<WCSchoolBean> collegeList = mSchoolService.getCollegesBySchoolId(schoolId);
        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<HashMap<String, Object>>();
        if (collegeList != null && collegeList.size() > 0) {
            for (WCSchoolBean college : collegeList) {
                HashMap<String, Object> hash = getCollegeHash(college);
                resultArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.putAll(getSchoolHash(schoolBean));
        result.put("college", resultArray);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getSchoolHash(WCSchoolBean schoolBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("school_id", schoolBean.getSchoolId());
        result.put("school_name", schoolBean.getName());
        return result;
    }

    private HashMap<String, Object> getCollegeHash(WCSchoolBean collegeBean) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("college_id", collegeBean.getSchoolId());
        result.put("college_name", collegeBean.getName());
        return result;
    }
}
