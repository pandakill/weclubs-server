package com.weclubs.application.comment;

import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCCommentBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.mapper.WCCommentMapper;
import com.weclubs.model.WCCommentDetailModel;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论的 service 实现类
 *
 * Created by fangzanpan on 2017/4/1.
 */
@Service(value = "commentService")
class WCCommentServiceImpl implements WCICommentService {

    public final static int COMMENT_TYPE_ACTIVITY = 1;
    public final static int COMMENT_TYPE_MEETING = 2;
    public final static int COMMENT_TYPE_MISSION = 2;
    public final static int COMMENT_TYPE_NOTIFY = 2;

    private Logger log = Logger.getLogger(WCCommentServiceImpl.class);

    @Autowired
    private WCCommentMapper mCommentMapper;
    @Autowired
    private WCIUserService mUserService;

    public List<WCCommentDetailModel> getCommentListBySourceId(String sourceType, long sourceId) {

        if (StringUtils.isEmpty(sourceType)) {
            log.error("getCommentListBySourceId：sourceType不能为空");
            return null;
        }

        if (sourceId <= 0) {
            log.error("getCommentListBySourceId：sourceId不能小于等于0");
            return null;
        }

        int type = getSourceType(sourceType);

        if (type == 0) {
            log.error("getCommentListBySourceId：sourceType不符合规则，sourceType = " + sourceType);
            return null;
        }

        return mCommentMapper.getCommentListById(type, sourceId);
    }

    @Override
    public WCHttpStatus createComment(long studentId, String content, String sourceType, long sourceId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean == null) {
            check.msg = "该评论发布者不存在";
            return check;
        }

        WCCommentBean commentBean = new WCCommentBean();
        commentBean.setSourceId(sourceId);
        commentBean.setContent(content);
        commentBean.setSourceType(getSourceType(sourceType));
        commentBean.setStatus(1);
        commentBean.setStudentId(studentId);

        mCommentMapper.createComment(commentBean);

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    private int getSourceType(String sourceType) {
        int type = 0;

        if (sourceType.equals("activity")) {
            type = COMMENT_TYPE_ACTIVITY;
        } else if (sourceType.equals("meeting")) {
            type = COMMENT_TYPE_MEETING;
        } else if (sourceType.equals("mission")) {
            type = COMMENT_TYPE_MISSION;
        } else if (sourceType.equals("notify")) {
            type = COMMENT_TYPE_NOTIFY;
        }

        return type;
    }
}
