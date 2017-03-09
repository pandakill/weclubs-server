package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
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
}
