package com.weclubs.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weclubs.application.comment.WCICommentService;
import com.weclubs.application.security.WCISecurityService;
import com.weclubs.model.WCCommentDetailModel;
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
 * 评论相关的 API 接口
 *
 * Created by fangzanpan on 2017/3/26.
 */
@RestController
@RequestMapping(value = "/comment")
class WCCommentAPI {

    private Logger log = Logger.getLogger(WCCommentAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;
    @Autowired
    private WCICommentService mCommentService;

    @RequestMapping(value = "get_comment_list")
    public WCResultData getCommentList(@RequestBody WCRequestModel requestModel) {

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
        String sourceType = (String) requestData.get("source_type");
        long sourceId = 0;
        if (requestData.get("source_id") instanceof String) {
            sourceId = Long.parseLong((String) requestData.get("source_id"));
        } else if (requestData.get("source_id") instanceof Integer) {
            sourceId = (Integer) requestData.get("source_id");
        }
        int pageNo = WCRequestParamsUtil.getPageNo(requestModel);
        int pageSize = WCRequestParamsUtil.getPageSize(requestModel);

        PageHelper.startPage(pageNo, pageSize);
        List<WCCommentDetailModel> comments = mCommentService.getCommentListBySourceId(sourceType, sourceId);
        PageInfo<WCCommentDetailModel> pageInfo = new PageInfo<WCCommentDetailModel>(comments);

        ArrayList<HashMap<String, Object>> resultHash = new ArrayList<HashMap<String, Object>>();
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (WCCommentDetailModel commentDetailModel : pageInfo.getList()) {
                HashMap<String, Object> hash = getCommentDetail(commentDetailModel, sourceType);
                resultHash.add(hash);
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("comment", resultHash);
        result.put("has_more", pageInfo.isHasNextPage() ? 1 : 0);
        return WCResultData.getSuccessData(result);
    }

    private HashMap<String, Object> getCommentDetail(WCCommentDetailModel model, String sourceType) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("comment_id", model.getCommentId());
        result.put("content", model.getContent());
        result.put("source_type", sourceType);
        result.put("source_id", model.getSourceId());
        result.put("create_date", model.getCreateDate());

        result.put("student_id", model.getStudentId());
        result.put("student_name", model.getStudentName());
        result.put("student_avatar", model.getStudentAvatar());

        return result;
    }
}
