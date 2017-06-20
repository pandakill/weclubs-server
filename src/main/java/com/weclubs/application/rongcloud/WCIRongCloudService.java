package com.weclubs.application.rongcloud;

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
     * 根据 id 等 获取融云服务器的 token
     *
     * @param userId    用户 id
     * @param userName  用户昵称
     * @param avatarUrl 用户头像地址
     *
     * @return  返回融云服务器的 token
     */
    String getUserToken(long userId, String userName, String avatarUrl);

    void getGroupList();
}
