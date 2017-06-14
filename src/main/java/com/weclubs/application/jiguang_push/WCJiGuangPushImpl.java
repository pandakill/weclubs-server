package com.weclubs.application.jiguang_push;

import com.alibaba.fastjson.JSONObject;
import com.weclubs.application.message.WCIMessageService;
import com.weclubs.bean.WCMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 极光推送的实体
 *
 * Created by fangzanpan on 2017/5/23.
 */
@Service(value = "jiGuangPushService")
class WCJiGuangPushImpl implements WCIJiGuangPushService {

    protected static Logger log = LoggerFactory.getLogger(WCJiGuangPushImpl.class);

    private WCIMessageService mMessageService;

    @Autowired
    public WCJiGuangPushImpl(WCIMessageService messageService) {
        this.mMessageService = messageService;
    }

    /*
     * 通知样式格式：
     * Android
     *      title：有人通知任务了
     *      content：【XXX】确认了【由于三号台风来临，紧急听课一天】通知
     * iOS
     *      content：【XXX】确认了【由于三号台风来临，紧急听课一天】通知
     */
    public void pushDynamicStatusChange(String activity, String chineseType, String userName,
                                        String attribution, String dynamicType, long dynamicId, long... receiverId) {

        String title = "有人" + activity + chineseType + "了";

        String missionAttr = attribution.length() > 10
                ? (attribution.substring(0, 10) + "...")
                : attribution;
        String content = "【" + userName + "】"
                + activity + "了【" + missionAttr + "】" + chineseType;

        // 发送推送通知
        WCJPushClient.getInstance().pushNotifyToPerson(title, content, receiverId);

        // 发送通知之后需要记录到服务器当中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dynamic_id", dynamicId);
        jsonObject.put("dynamic_type", dynamicId);

        WCMessageBean messageBean = new WCMessageBean();
        messageBean.setTitle(title);
        messageBean.setContent(content);
        messageBean.setData(jsonObject.toJSONString());

        // 消息接收者
        List<Long> msgReceiver = new ArrayList<Long>();
        for (long l : receiverId) {
            msgReceiver.add(l);
        }
        mMessageService.publicMessage(messageBean, msgReceiver);
    }
}
