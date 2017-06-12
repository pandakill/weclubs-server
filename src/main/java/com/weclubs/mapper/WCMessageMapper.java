package com.weclubs.mapper;

import com.weclubs.bean.WCMessageBean;
import com.weclubs.bean.WCStudentMessageRelationBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息的 mybatis 接口
 *
 * Created by fangzanpan on 2017/6/12.
 */
public interface WCMessageMapper {

    /**
     * 根据学生 id 或者消息 id 获取消息详情
     *
     * @param studentId 学生id
     * @param msgId     消息id
     * @return  消息实体
     */
    WCStudentMessageRelationBean getMsgDetailByStuIdAndMsgId(@Param("studentId") long studentId, @Param("msgId") long msgId);

    /**
     * 根据学生 id 获取其所有的消息
     *
     * @param studentId 学生id
     * @return  消息列表
     */
    List<WCStudentMessageRelationBean> getMsgListByStuId(@Param("studentId") long studentId);

    /**
     * 创建新消息
     *
     * @param msg   消息实体
     */
    void createMsg(WCMessageBean msg);

    /**
     * 添加学生、消息关系列表
     *
     * @param studentList   学生 id 列表
     * @param msgId 消息 id
     */
    void createStudentRelation(@Param("list") List<Long> studentList, @Param("msgId") long msgId);

    /**
     * 更新学生、消息关系
     *
     * @param relationBean  学生消息关系
     */
    void updateStudentRelation(WCStudentMessageRelationBean relationBean);
}
