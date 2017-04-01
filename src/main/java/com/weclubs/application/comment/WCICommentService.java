package com.weclubs.application.comment;

import com.weclubs.model.WCCommentDetailModel;

import java.util.List;

/**
 * 评论的 comment 接口类
 *
 * Created by fangzanpan on 2017/4/1.
 */
public interface WCICommentService {

    List<WCCommentDetailModel> getCommentListBySourceId(String sourceType, long sourceId);
}
