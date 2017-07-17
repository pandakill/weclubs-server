package io.rong.messages;

import com.weclubs.model.WCApplyIntoClubMessageModel;
import io.rong.util.GsonUtil;

/**
 * 通知消息
 *
 * Created by fangzanpan on 2017/7/17.
 */
public class WCMessageNotifyMessage extends BaseMessage {

    private transient static final String TYPE = "WCMessageNotify";

    private String title;
    private String content;
    private long user_id;
    private String user_name;
    private String user_avatar;
    private String user_type;
    private long sponsor_date;
    private String message_type;
    private String extra;

    public WCMessageNotifyMessage() {}

    public WCMessageNotifyMessage(WCApplyIntoClubMessageModel messageModel) {
        setTitle(messageModel.getTitle());
        setContent(messageModel.getContent());
        setUser_type(messageModel.getUser_type());
        setUser_id(messageModel.getUser_id());
        setUser_avatar(messageModel.getUser_avatar());
        setUser_name(messageModel.getUser_name());
        setMessage_type(messageModel.getMessage_type());
        setSponsor_date(messageModel.getSponsor_date());
        setExtra(GsonUtil.toJson(messageModel.getExtra(), WCApplyIntoClubMessageModel.ExtraBean.class));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public long getSponsor_date() {
        return sponsor_date;
    }

    public void setSponsor_date(long sponsor_date) {
        this.sponsor_date = sponsor_date;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, WCMessageNotifyMessage.class);
    }
}
