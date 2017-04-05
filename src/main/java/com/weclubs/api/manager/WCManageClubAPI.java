package com.weclubs.api.manager;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 社团管理API
 *
 * Created by fangzanpan on 2017/4/5.
 */
@RestController
@RequestMapping(value = "manage/club")
public class WCManageClubAPI {

    private Logger log = Logger.getLogger(WCManageClubAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCIClubService mClubService;

    @RequestMapping(value = "/get_my_club")
    public WCResultData getMyManagerClub(@RequestBody WCRequestModel requestModel) {

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

        long studentId = WCRequestParamsUtil.getUserId(requestModel);

        HashMap<String, Object> result = new HashMap<String, Object>();
        return WCResultData.getSuccessData(result);
    }
}
