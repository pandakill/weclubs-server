package com.weclubs.application.jiguang_push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.sun.istack.internal.NotNull;
import com.weclubs.util.Constants;
import org.apache.log4j.Logger;

/**
 * 极光推送的 client 类
 *
 * Created by fangzanpan on 2017/5/24.
 */
public class WCJPushClient {

    private static Logger log = Logger.getLogger(WCJPushClient.class);

    private static final Object sMonitor = new Object();
    private static WCJPushClient sInstance = null;

    private JPushClient mJPushClient = null;

    private WCJPushClient() {
        log.info("WCJPushClient constructor.");
        mJPushClient = new JPushClient(Constants.JIGUANG_SECRET_KEY, Constants.JIGUANG_APP_KEY, 3);
    }

    public static WCJPushClient getInstance() {
        synchronized (sMonitor) {
            if (sInstance == null) {
                sInstance = new WCJPushClient();
            }

            return sInstance;
        }
    }

    public void pushNotify(PushPayload pushPayload) {
        if (pushPayload == null) {
            log.error("pushPayload 不能为空");
            return;
        }

        log.info("pushNotify：pushPayload = " + pushPayload.toString());

        try {
            PushResult pushResult = mJPushClient.sendPush(pushPayload);
            log.info(pushResult.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    public void pushNotifyToPerson(String title, String content, long... userId) {
        PushPayload pushPayload = buildPushObject_alias_to_person(title, content, userId);

        pushNotify(pushPayload);
    }

    /**
     * 推送通知发送给全平台所有用户
     *
     * @param content   推送通知内容
     */
    private PushPayload buildPushObject_all_all(String title, String content) {
        return PushPayload.newBuilder()
                .setNotification(Notification.alert(content))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtra(title, content).incrBadge(1).build())
                        .build())
                .build();
    }

    /**
     * 推送通知和消息发送给全平台所有用户
     *
     * @param content   推送消息、通知内容
     */
    private PushPayload buildPushAndMsgObject_all_all(String title, String content) {
        return PushPayload.newBuilder()
                .setNotification(Notification.alert(content))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtra(title, content).incrBadge(1).build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }

    /**
     * 推送通知给指定的用户
     * 根据别名推送，要求客户端在登录的时候设置好别名，退出登录时，别名设置为空
     *
     * @param content   推送消息内容
     */
    private PushPayload buildPushObject_alias_to_person(String title, String content, @NotNull long... userId) {

        String[] str = new String[userId.length];
        if (userId.length > 0) {
            for (int i = 0; i < userId.length; i++) {
                str[i] = "user_" + userId[i];
            }
        }

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(str))
                .setNotification(Notification.alert(content))
                .setNotification(Notification.newBuilder()
                .setAlert(content)
                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
                .addPlatformNotification(IosNotification.newBuilder().addExtra(title, content).incrBadge(1).build())
                .build())
                .build();
    }

    /**
     * 推送通知和消息给指定的用户
     * 根据别名推送，要求客户端在登录的时候设置好别名，退出登录时，别名设置为空
     *
     * @param content   推送消息内容
     */
    private PushPayload buildPushAndMsgObject_alias_to_person(String title, String content, @NotNull long... userId) {

        String[] str = new String[userId.length];
        if (userId.length > 0) {
            for (int i = 0; i < userId.length; i++) {
                str[i] = "user_" + userId[i];
            }
        }

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(str))
                .setNotification(Notification.alert(content))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtra(title, content).incrBadge(1).build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
}
