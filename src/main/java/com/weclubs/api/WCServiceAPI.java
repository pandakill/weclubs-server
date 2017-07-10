package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.qiniu.WCIQiNiuService;
import com.weclubs.application.school.WCISchoolService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.bean.WCSchoolBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import com.weclubs.util.WCRequestParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 综合类接口
 *
 * Created by fangzanpan on 2017/4/7.
 */
@RestController
@RequestMapping(value = "/service")
class WCServiceAPI {

    private Logger log = Logger.getLogger(WCServiceAPI.class);

    private WCISecurityService mSecurityService;
    private WCIQiNiuService mQiNiuService;
    private WCIClubService mClubService;
    private WCISchoolService mSchoolService;

    @Autowired
    public WCServiceAPI(WCISecurityService mSecurityService, WCIQiNiuService mQiNiuService,
                        WCIClubService clubService, WCISchoolService schoolService) {
        this.mSecurityService = mSecurityService;
        this.mQiNiuService = mQiNiuService;
        this.mClubService = clubService;
        this.mSchoolService = schoolService;
    }

    @RequestMapping(value = "/get_upload_token", method = RequestMethod.POST)
    public WCResultData getUploadToken(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        check = mSecurityService.checkTokenAvailable(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        String token = mQiNiuService.getUploadToken();

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_qiniu_config", method = RequestMethod.POST)
    public WCResultData getQiNiuConfig(@RequestBody WCRequestModel requestModel){

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

//        check = mSecurityService.checkTokenAvailable(requestModel);
//        if (check != WCHttpStatus.SUCCESS) {
//            return WCResultData.getHttpStatusData(check, null);
//        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("config", mQiNiuService.getUploadConfig());
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_index_list", method = RequestMethod.POST)
    public WCResultData getIndexList(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("hot_club", null);
        result.put("banner", null);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_index_club", method = RequestMethod.POST)
    public WCResultData getIndexClub(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
        }

        // TODO: 2017/7/8 这里需要完成判断如果用户没有选择学校或者没有认证
        long userId = WCCommonUtil.getLongData(requestData.get("user_id"));
        long schoolId = WCCommonUtil.getLongData(requestData.get("school_id"));

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(schoolId);
        if (schoolBean == null) {
            check.msg = "找不到该学校";
            return WCResultData.getHttpStatusData(check, null);
        }
        log.debug("getIndexClub：schoolBean = " + schoolBean.toString());

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCClubBean> suggestClubs = mClubService.getClubsBySchoolId(schoolBean.getSchoolId());
        PageInfo<WCClubBean> pageInfo = new PageInfo<WCClubBean>(suggestClubs);

        ArrayList<HashMap<String, Object>> clubHash = new ArrayList<>();
        if (pageInfo.getList() != null) {
            for (WCClubBean clubBean : pageInfo.getList()) {
                clubHash.add(getIndexClubModel(clubBean));
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("club_list", clubHash);
        result.put("is_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/search_club", method = RequestMethod.POST)
    public WCResultData searchClub(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
        }

        String keyword = (String) requestData.get("keyword");
        long schoolId = WCCommonUtil.getLongData(requestData.get("school_id"));

        WCSchoolBean schoolBean = mSchoolService.getSchoolById(schoolId);
        if (schoolBean == null) {
            check.msg = "找不到该学校";
            return WCResultData.getHttpStatusData(check, null);
        }
        log.debug("searchClub：schoolBean = " + schoolBean.toString());

        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCClubBean> suggestClubs = mClubService.searchClubList(schoolBean.getSchoolId(), keyword);
        PageInfo<WCClubBean> pageInfo = new PageInfo<WCClubBean>(suggestClubs);

        ArrayList<HashMap<String, Object>> clubHash = new ArrayList<>();
        if (pageInfo.getList() != null) {
            for (WCClubBean clubBean : pageInfo.getList()) {
                clubHash.add(getIndexClubModel(clubBean));
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("club_list", clubHash);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/feed_back", method = RequestMethod.POST)
    public WCResultData feedBack(@RequestBody WCRequestModel requestModel) {

        WCHttpStatus check = mSecurityService.checkRequestParams(requestModel);
        if (check != WCHttpStatus.SUCCESS) {
            return WCResultData.getHttpStatusData(check, null);
        }

        HashMap<String, Object> requestData = WCRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
        if (requestData == null || requestData.size() == 0) {
            return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST_NULL_PARAMS, null);
        }

        log.debug("feedBack：" + requestData.toString());

        return WCResultData.getSuccessData(null);
    }

    private HashMap<String, Object> getIndexClubModel(WCClubBean clubBean) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("club_id", clubBean.getClubId());
        result.put("club_name", clubBean.getName());
        result.put("avatar_url", clubBean.getAvatarUrl());
        result.put("level", clubBean.getLevel());
        result.put("slogan", clubBean.getSlogan());
        result.put("attribution", clubBean.getIntroduction());

        List<WCClubStudentBean> studentList = mClubService.getCurrentGraduateStudentsByClubId(clubBean.getClubId());
        int suggestNumber = 0;
        if (studentList != null) {
            suggestNumber = studentList.size() > 6 ? 6 : studentList.size();
        }

        ArrayList<HashMap<String, Object>> studentSuggest = new ArrayList<>();
        if (suggestNumber > 0) {
            for (int i = 0; i < suggestNumber; i++) {
                HashMap<String, Object> student = new HashMap<>();
                student.put("student_id", studentList.get(i).getStudentId());
                student.put("student_name", studentList.get(i).getRealName());
                student.put("avatar_url", studentList.get(i).getAvatarUrl());
                studentSuggest.add(student);
            }
        }

        result.put("student", studentSuggest);

        return result;
    }
}
