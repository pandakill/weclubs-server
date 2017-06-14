package com.weclubs.application.jiguang_push;

/**
 * 极光推送的 service 接口类
 *
 * Created by fangzanpan on 2017/5/23.
 */
public interface WCIJiGuangPushService {

    /**
     * 动态发生改变时，需要发送推送通知给任务发起者
     *
     * @param activity  动作，例如：确认，请假
     * @param chineseType   任务类型，例如会议，任务
     * @param userName  完成动作的用户
     * @param attribution   动态的标题
     * @param dynamicType   动态类型，例如mission，notify
     * @param dynamicId 动态id
     * @param receiverId    推送通知的接收者
     */
    void pushDynamicStatusChange(String activity, String chineseType, String userName,
                                 String attribution, String dynamicType, long dynamicId, long... receiverId);
}
