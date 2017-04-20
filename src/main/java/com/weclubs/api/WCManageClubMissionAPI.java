package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.WCSponsorMissionModel;
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
 * 社团任务管理相关的 API 接口
 *
 * Created by fangzanpan on 2017/4/20.
 */
@RestController
@RequestMapping(value = "/manage/club_mission")
class WCManageClubMissionAPI {

    private Logger log = Logger.getLogger(WCManageClubActivityAPI.class);

    private WCISecurityService mSecurityService;
    private WCIClubMissionService mClubMissionService;

    @Autowired
    public WCManageClubMissionAPI(WCISecurityService mSecurityService, WCIClubMissionService mClubMissionService) {
        this.mSecurityService = mSecurityService;
        this.mClubMissionService = mClubMissionService;
    }

    @RequestMapping(value = "/get_my_mission")
    public WCResultData getMyMission(@RequestBody WCRequestModel requestModel) {

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
        long sponsorId = WCRequestParamsUtil.getUserId(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCSponsorMissionModel> missionModelList = mClubMissionService.getMissionBySponsorId(sponsorId);
        PageInfo<WCSponsorMissionModel> pageInfo = new PageInfo<>(missionModelList);

        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCSponsorMissionModel sponsorMissionModel : pageInfo.getList()) {
                resultArray.add(sponsorMissionModel.getMissionHash());
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("mission", resultArray);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/public_mission")
    public WCResultData publicMission(@RequestBody WCRequestModel requestModel) {

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

        check = mClubMissionService.publicMission(requestData);

        return WCResultData.getHttpStatusData(check, null);
    }
}
