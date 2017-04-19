package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.activity.WCIActivityService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.WCActivityDetailBaseModel;
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

import static com.weclubs.util.WCCommonUtil.getLongData;

/**
 * 社团活动的管理 API 接口文件
 *
 * Created by fangzanpan on 2017/4/19.
 */
@RestController
@RequestMapping(value = "/manage/club_activity")
class WCManageClubActivityAPI {

    private Logger log = Logger.getLogger(WCManageClubActivityAPI.class);

    private WCISecurityService mSecurityService;
    private WCIActivityService mActivityService;


    @Autowired
    public WCManageClubActivityAPI(WCISecurityService mSecurityService, WCIActivityService mActivityService) {
        this.mSecurityService = mSecurityService;
        this.mActivityService = mActivityService;
    }

    @RequestMapping(value = "/get_my_activity")
    public WCResultData getMyActivity(@RequestBody WCRequestModel requestModel) {

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

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);
        long sponsorId = getLongData(requestData.get("sponsor_id"));

        PageHelper.startPage(pageNo, pageSize);
        List<WCActivityDetailBaseModel> activityList = mActivityService.getManageClubBySponsorId(sponsorId);
        PageInfo<WCActivityDetailBaseModel> pageInfo = new PageInfo<>(activityList);

        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCActivityDetailBaseModel activityDetailBaseModel : pageInfo.getList()) {
                HashMap<String, Object> hash = activityDetailBaseModel.getClubDetailBaseInfo();
                resultArray.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("activity", resultArray);
        return WCResultData.getSuccessData(result);
    }
}
