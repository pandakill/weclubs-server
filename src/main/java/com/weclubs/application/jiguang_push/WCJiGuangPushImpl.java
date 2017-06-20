package com.weclubs.application.jiguang_push;

import com.alibaba.fastjson.JSON;
import com.weclubs.application.message.WCIMessageService;
import com.weclubs.bean.WCMessageBean;
import com.weclubs.util.Constants;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
     *      title：有人确认通知了
     *      content：【XXX】确认了【由于三号台风来临，紧急听课一天】通知
     * iOS
     *      content：【XXX】确认了【由于三号台风来临，紧急听课一天】通知
     *
     * 点击需要跳转至动态详情页面，scene_id = 201
     */
    public void pushDynamicStatusChange(String activity, String chineseType, String userName,
                                        String attribution, String dynamicType, long dynamicId, long... receiverId) {

        String title = "有人" + activity + chineseType + "了";

        String missionAttr = attribution.length() > 10
                ? (attribution.substring(0, 10) + "...")
                : attribution;
        String content = "【" + userName + "】"
                + activity + "了【" + missionAttr + "】" + chineseType;

        Map<String, String> jsonObject = new HashMap<String, String>();
        jsonObject.put("scene_id", Constants.SCENE_MANAGE_DYNAMIC + "");
        jsonObject.put("dynamic_id", dynamicId + "");
        jsonObject.put("dynamic_type", dynamicType);

        // 发送推送通知
        WCJPushClient.getInstance().pushNotifyToPerson(title, content, jsonObject, receiverId);

        // 发送通知之后需要记录到服务器当中
        WCMessageBean messageBean = new WCMessageBean();
        messageBean.setTitle(title);
        messageBean.setContent(content);
        messageBean.setData(JSON.toJSONString(jsonObject));

        // 消息接收者
        List<Long> msgReceiver = new ArrayList<Long>();
        for (long l : receiverId) {
            msgReceiver.add(l);
        }
        mMessageService.publicMessage(messageBean, msgReceiver);
    }

    /*
     * 通知样式格式：
     * Android
     *      title：你有新的会议需要参加
     *      content：篮球协会：6月19日 18:00 前在【新教学楼1楼】参加【篮球协会第三届学生干部选举】会议
     * iOS
     *      content：篮球协会：6月19日 18:00 前在【新教学楼1楼】参加【篮球协会第三届学生干部选举】会议
     *
     * 点击需要跳转至动态详情页面，scene_id = 101
     */
    public void pushNewMeetingCreate(long date, String address, String clubName, String meeting,
                                     long meetingId, long... receiverId) {
        String title = "你有新的会议需要参加";

        meeting = meeting.length() > 10
                ? (meeting.substring(0, 10) + "...")
                : meeting;
        String dateStr = DateUtils.formatDate(new Date(date), "mm月dd日 HH:MM");

        String content = clubName + "：" + dateStr + " 前在【" + address + "】参加【" + meeting + "】";

        Map<String, String > jsonObject = new HashMap<String, String>();
        jsonObject.put("scene_id", Constants.SCENE_PERSON_DYNAMIC + "");
        jsonObject.put("dynamic_id", meetingId + "");
        jsonObject.put("dynamic_type", Constants.TODO_MEETING);

        WCJPushClient.getInstance().pushNotifyToPerson(title, content, jsonObject, receiverId);

        WCMessageBean messageBean = new WCMessageBean();
        messageBean.setTitle(title);
        messageBean.setContent(content);
        messageBean.setData(JSON.toJSONString(jsonObject));

        // 消息接收者
        List<Long> msgReceiver = new ArrayList<Long>();
        for (long l : receiverId) {
            msgReceiver.add(l);
        }
        mMessageService.publicMessage(messageBean, msgReceiver);
    }

    /*
     * 通知样式格式：
     * Android
     *      title：你有新的通知需要确认
     *      content：篮球协会：发布了【台风来临，下午活动取消，举办时间待定】
     * iOS
     *      content：篮球协会：发布了【台风来临，下午活动取消，举办时间待定】
     *
     * 点击需要跳转至动态详情页面，scene_id = 101
     */
    public void pushNewNotifyCreate(String clubName, String notify, long notifyId, long... receiverId) {
        String title = "你有新的通知需要确认";

        notify = notify.length() > 20 ? (notify.substring(0, 20) + "...") : notify;
        String content = clubName + "：发布了新通知【" + notify + "】";

        Map<String, String > jsonObject = new HashMap<String, String>();
        jsonObject.put("scene_id", Constants.SCENE_PERSON_DYNAMIC + "");
        jsonObject.put("dynamic_id", notifyId + "");
        jsonObject.put("dynamic_type", Constants.TODO_NOTIFY);

        WCJPushClient.getInstance().pushNotifyToPerson(title, content, jsonObject, receiverId);

        WCMessageBean messageBean = new WCMessageBean();
        messageBean.setTitle(title);
        messageBean.setContent(content);
        messageBean.setData(JSON.toJSONString(jsonObject));

        // 消息接收者
        List<Long> msgReceiver = new ArrayList<Long>();
        for (long l : receiverId) {
            msgReceiver.add(l);
        }
        mMessageService.publicMessage(messageBean, msgReceiver);
    }

    public void pushUnConfirmDynamic(String clubName, String dynamicType, String dynamic, long dynamicId, long... receiverId) {

        String chineseType = "";
        if (dynamicType.equals(Constants.TODO_MEETING)) {
            chineseType = "会议";
        } else if (dynamicType.equals(Constants.TODO_MISSION)) {
            chineseType = "任务";
        } else if (dynamicType.equals(Constants.TODO_NOTIFY)) {
            chineseType = "通知";
        }


        String title = "你有" + chineseType + "需要确认";

        dynamic = dynamic.length() > 20 ? (dynamic.substring(0, 20) + "...") : dynamic;
        String content = clubName + "：需要你确认收到" + chineseType + "【" + dynamic + "】";

        Map<String, String > jsonObject = new HashMap<String, String>();
        jsonObject.put("scene_id", Constants.SCENE_PERSON_DYNAMIC + "");
        jsonObject.put("dynamic_id", dynamicId + "");
        jsonObject.put("dynamic_type", dynamicType);

        WCJPushClient.getInstance().pushNotifyToPerson(title, content, jsonObject, receiverId);
    }
}
