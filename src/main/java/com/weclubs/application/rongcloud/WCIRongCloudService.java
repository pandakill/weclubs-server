package com.weclubs.application.rongcloud;

import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCGroupChatListModel;
import com.weclubs.util.WCHttpStatus;

import java.util.HashMap;
import java.util.List;

/**
 * 融云相关的 API 服务接口
 *
 * Created by fangzanpan on 2017/6/21.
 */
public interface WCIRongCloudService {

    /**
     * 获取系统消息的用户id
     *
     * @return  系统系统用户的id
     */
    String getSystemMsgId();

    /**
     * 根据 weclubs 的用户 id 获取 im 的用户 id
     *
     * @param userId    用户 id
     *
     * @return  im 服务器的用户 id
     */
    String getRongUserId(long userId);

    /**
     * 根据融云的 userId 转换成服务器自己的用户 id
     *
     * @param rongUserId    融云服务器的用户id
     * @return  服务器自己的用户 id
     */
    long getUserIdFromRongUserId(String rongUserId);

    /**
     * 根据 weclubs 的社团 id 获取融云服务器的社团群聊 id
     *
     * @param clubId    社团 id
     *
     * @return  im 服务器的社团群聊 id
     */
    String getRongClubId(long clubId);

    /**
     * 根据融云的 社团群聊 id 转换成服务器自己的社团 id
     *
     * @param rongClubId    融云服务器的社团群聊 id
     * @return  服务器自己的用户 id
     */
    long getClubIdFromRongClubId(String rongClubId);

    /**
     * 根据 id 等 获取融云服务器的 token
     *
     * @param userId    用户 id
     *
     * @return  返回融云服务器的 token
     */
    String getUserToken(long userId);

    /**
     * 根据 id 等 获取融云服务器的 token
     *
     * @param userId    用户 id
     * @param userName  用户昵称
     * @param avatarUrl 用户头像地址
     *
     * @return  返回融云服务器的 token
     */
    String getUserToken(long userId, String userName, String avatarUrl);

    /**
     * 创建群聊
     *
     * @param clubId    社团 id
     * @param clubName  社团名称
     * @param userId    需要加入到群聊的用户 id
     */
    WCHttpStatus createGroupChat(long clubId, String clubName, long... userId);

    /**
     * 根据社团 id 获取该社团群聊的群组成员
     *
     * @param clubId    社团 id
     *
     * @return  群组成员
     */
    List<WCStudentBean> getStudentsByGroupId(long clubId);

    /**
     * 根据学生 id 获取该学生的群聊列表
     *
     * @param studentId 学生 ID
     *
     * @return  返回当前学生的群聊列表
     */
    List<WCGroupChatListModel> getMyClubChatList(long studentId);

    /**
     * 根据学生 id 获取该学生的群聊列表
     *
     * @param studentId 学生 ID
     *
     * @return  返回当前学生的群聊列表
     */
    List<HashMap<String, Object>> getMyClubChatListForMap(long studentId);
}
