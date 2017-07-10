package com.weclubs.mapper;

import com.weclubs.bean.WCCommentBean;
import com.weclubs.model.WCCommentDetailModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论的 mapper
 *
 * Created by fangzanpan on 2017/4/1.
 */
public interface WCCommentMapper {

    List<WCCommentDetailModel> getCommentListById(@Param("sourceType") int sourceType, @Param("sourceId") long sourceId);

    void createComment(WCCommentBean commentBean);
}
