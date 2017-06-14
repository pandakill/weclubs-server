package com.weclubs.application.jiguang_push;

import com.alibaba.fastjson.JSONObject;
import com.weclubs.application.message.WCIMessageService;
import com.weclubs.bean.WCMessageBean;
import com.weclubs.util.Constants;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

        // 发送推送通知
        WCJPushClient.getInstance().pushNotifyToPerson(title, content, receiverId);

        // 发送通知之后需要记录到服务器当中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scene_id", Constants.SCENE_MANAGE_DYNAMIC);
        jsonObject.put("dynamic_id", dynamicId);
        jsonObject.put("dynamic_type", dynamicType);

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

        WCJPushClient.getInstance().pushNotifyToPerson(title, content, receiverId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scene_id", Constants.SCENE_PERSON_DYNAMIC);
        jsonObject.put("dynamic_id", meetingId);
        jsonObject.put("dynamic_type", Constants.TODO_MEETING);

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
