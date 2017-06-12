package com.weclubs.application.message;

import com.weclubs.bean.WCMessageBean;
import com.weclubs.bean.WCStudentMessageRelationBean;
import com.weclubs.util.WCHttpStatus;

import java.util.List;

/**
 * 消息相关的 service 接口
 *
 * Created by fangzanpan on 2017/6/12.
 */
public interface WCIMessageService {

    /**
     * 获取某个学生的消息列表
     *
     * @param studentId 学生 id
     * @return  消息列表
     */
    List<WCStudentMessageRelationBean> getMsgListByStudentId(long studentId);

    /**
     * 发布新的消息
     *
     * @param messageBean   消息实体，需要有内容、标题
     * @param students  接收消息的学生 id 列表
     * @return  如果发布成功，则返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus publicMessage(WCMessageBean messageBean, List<Long> students);

    /**
     * 根据学生 id 和消息 id 获取消息详情
     *
     * @param studentId 学生 id
     * @param msgId 消息 id
     * @return  返回消息详情
     */
    WCStudentMessageRelationBean getMsgDetailByMsgIdAndStuId(long studentId, long msgId);

    /**
     * 创建动态更新的推送消息
     *
     * @return  推送消息原型
     */
    WCMessageBean getDynamicMsg();
}
