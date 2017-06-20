package com.weclubs.application.rongcloud;

import com.weclubs.util.Constants;
import io.rong.RongCloud;
import io.rong.models.TokenResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 融云 IM 的服务实现
 *
 * Created by fangzanpan on 2017/6/21.
 */
@Service(value = "rongCloudService")
class WCRongCloudServiceImpl implements WCIRongCloudService {

    private final String RONG_USER_ID_TAG = "im_user_id_";

    private Logger log = Logger.getLogger(WCRongCloudServiceImpl.class);

    private RongCloud mRongCloud = RongCloud.getInstance(Constants.RONGCLOUD_APP_KEY, Constants.RONGCLOUD_SECRET_KEY);

    public String getSystemMsgId() {
        return "weclubs_system_msg";
    }

    public String getRongUserId(long userId) {
        return RONG_USER_ID_TAG + userId;
    }

    public long getUserIdFromRongUserId(String rongUserId) {
        rongUserId = rongUserId.substring(11, rongUserId.length());
        log.info("getUserIdFromRongUserId：剪裁后的 userId = " + rongUserId);
        return Long.parseLong(rongUserId);
    }

    public String getUserToken(long userId, String userName, String avatarUrl) {

        try {
            TokenResult tokenResult = mRongCloud.user.getToken(getRongUserId(userId), userName, avatarUrl);

            if (tokenResult.getCode() == 200) {
                return tokenResult.getToken();
            } else {
                log.warn("getUserToken：" + tokenResult.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getGroupList() {
    }
}
