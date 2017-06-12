package com.weclubs.application.message;

import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCMessageBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.bean.WCStudentMessageRelationBean;
import com.weclubs.mapper.WCMessageMapper;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 消息的 service 实现类
 *
 * Created by fangzanpan on 2017/6/12.
 */
@Service(value = "messageService")
class WCMessageServiceImpl implements WCIMessageService {

    private Logger log = Logger.getLogger(WCMessageServiceImpl.class);

    private WCMessageMapper mMessageMapper;
    private WCIUserService mUserService;

    @Autowired
    public WCMessageServiceImpl(WCIUserService userService, WCMessageMapper messageMapper) {
        this.mUserService = userService;
        this.mMessageMapper = messageMapper;
    }

    public List<WCStudentMessageRelationBean> getMsgListByStudentId(long studentId) {

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);
        if (studentBean == null) {
            log.error("getMsgListByStudentId：找不到studentId = 【" + studentId + "】的学生");
            return null;
        }

        return mMessageMapper.getMsgListByStuId(studentId);
    }

    public WCHttpStatus publicMessage(WCMessageBean messageBean, List<Long> students) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (messageBean.getMessageId() > 0) {
            check.msg = "消息格式不正确，请检查后重新发送";
            return check;
        }

        if (StringUtils.isEmpty(messageBean.getContent())) {
            check.msg = "消息内容不能为空";
            return check;
        }

        mMessageMapper.createMsg(messageBean);

        if (messageBean.getMessageId() <= 0) {
            check.msg = "发送消息失败，请重新尝试";
            return check;
        }

        mMessageMapper.createStudentRelation(students, messageBean.getMessageId());

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    public WCStudentMessageRelationBean getMsgDetailByMsgIdAndStuId(long studentId, long msgId) {

        if (studentId <= 0) {
            log.error("getMsgDetailByMsgIdAndStuId：学生 id 不能小于等于0");
            return null;
        }

        if (mUserService.getUserInfoById(studentId) == null) {
            log.error("getMsgDetailByMsgIdAndStuId：找不到学生【" + studentId + "】");
            return null;
        }

        if (msgId <= 0) {
            log.error("getMsgDetailByMsgIdAndStuId：消息 id 不能小于等于0");
            return null;
        }

        return mMessageMapper.getMsgDetailByStuIdAndMsgId(studentId, msgId);
    }

    public WCMessageBean getDynamicMsg() {
        return null;
    }
}
