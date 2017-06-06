package com.weclubs.application.jiguang_push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.weclubs.application.token.WCITokenService;
import com.weclubs.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 极光推送的实体
 *
 * Created by fangzanpan on 2017/5/23.
 */
@Service(value = "jiGuangPushService")
public class WCJiGuangPushImpl implements WCIJiGuangPushService {

    protected static Logger log = LoggerFactory.getLogger(WCJiGuangPushImpl.class);

    private WCITokenService mTokenService;

    public static final String TITLE = "申通快递";
    public static final String ALERT = "有人拉取了接口";
    public static final String MSG_CONTENT = "申通快递祝新老客户新春快乐";
    public static final String REGISTRATION_ID = "0900e8d85ef";

    @Autowired
    public WCJiGuangPushImpl(WCITokenService mTokenService) {
        this.mTokenService = mTokenService;
    }

    public void pushNotify() {
        JPushClient jPushClient = new JPushClient(Constants.JIGUANG_SECRET_KEY, Constants.JIGUANG_APP_KEY, 3);

        PushPayload pushPayload = buildPushObject_all_alias_alert();
        log.error(pushPayload.toString());

        try {
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            log.error(pushResult.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

    }

    private PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())//设置接受的平台
                .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
                .setNotification(Notification.alert(ALERT))
                .build();
    }
}
