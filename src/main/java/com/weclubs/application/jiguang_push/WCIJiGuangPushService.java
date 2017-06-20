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

    /**
     * 创建新的会议时需要发起的推送通知
     *
     * @param date  会议开始时间
     * @param address   会议举办地点
     * @param clubName  社团名称
     * @param meeting   会议标题
     * @param meetingId 会议 id
     * @param receiverId    推送通知的接收者
     */
    void pushNewMeetingCreate(long date, String address, String clubName, String meeting, long meetingId, long... receiverId);

    /**
     * 创建新的会议时需要发起的推送通知
     *
     * @param clubName  社团名称
     * @param notify   通知内容
     * @param notifyId 通知 id
     * @param receiverId    推送通知的接收者
     */
    void pushNewNotifyCreate(String clubName, String notify, long notifyId, long... receiverId);

    /**
     * 提醒尚未确认动态的人需要确认操作
     *
     * @param clubName  社团名称
     * @param dynamicType   动态的类型
     * @param dynamic   动态的内容
     * @param dynamicId 动态 id
     * @param receiverId    推送通知的接收者
     */
    void pushUnConfirmDynamic(String clubName, String dynamicType, String dynamic, long dynamicId, long... receiverId);

    /**
     * 提醒尚未完成任务的人需要抓紧完成
     *
     * @param clubName  社团名称
     * @param dynamic   动态的内容
     * @param dynamicId 动态 id
     * @param receiverId    推送通知的接收者
     */
    void pushUnFinishMission(String clubName, String dynamic, long dynamicId, long... receiverId);
}
