package com.weclubs.application.jiguang_push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
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

    public void pushNotifyToPerson(String registrationId, String content) {
        PushPayload pushPayload = buildPushObject_all_alias(content, 1, 2);
        log.error(pushPayload.toString());

        try {
            PushResult pushResult = mJPushClient.sendPush(pushPayload);
            log.error(pushResult.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送消息发送给全平台所有用户
     *
     * @param content   推送消息内容
     */
    private PushPayload buildPushObject_all_all(String content) {
        return PushPayload.alertAll(content);
    }

    /**
     * 推送消息给指定的用户
     *
     * @param content   推送消息内容
     */
    private PushPayload buildPushObject_all_alias(String content, @NotNull long... userId) {

        String[] str = new String[userId.length];
        if (userId.length > 1) {
            for (int i = 0; i < userId.length; i++) {
                str[i] = "user_" + userId[i];
            }
        }

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(str))
                .setNotification(Notification.alert(content))
                .build();
    }
}
