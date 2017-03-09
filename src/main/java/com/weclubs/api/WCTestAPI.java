package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.model.WCRequestModel;
import com.weclubs.model.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
