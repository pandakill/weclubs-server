package com.weclubs.application.rongcloud;

import com.weclubs.application.club.WCIClubGraduateService;
import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.WCGroupChatListModel;
import com.weclubs.util.Constants;
import com.weclubs.util.WCHttpStatus;
import io.rong.RongCloud;
import io.rong.models.CodeSuccessResult;
import io.rong.models.GroupUser;
import io.rong.models.GroupUserQueryResult;
import io.rong.models.TokenResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 融云 IM 的服务实现
 *
 * Created by fangzanpan on 2017/6/21.
 */
@Service(value = "rongCloudService")
public class WCRongCloudServiceImpl implements WCIRongCloudService {

    public static final String RONG_USER_ID_TAG = "im_user_id_";
    public static final String RONG_CLUB_ID_TAG = "im_club_id_";

    private Logger log = Logger.getLogger(WCRongCloudServiceImpl.class);

    @Autowired
    private WCIUserService mUserService;
    @Autowired
    private WCIClubGraduateService mClubGraduateService;
    @Autowired
    private WCIClubService mClubService;

    public String getSystemMsgId() {
        return "weclubs_system_msg";
    }

    public String getRongUserId(long userId) {
        return RONG_USER_ID_TAG + userId;
    }

    public long getUserIdFromRongUserId(String rongUserId) {
        rongUserId = rongUserId.substring(RONG_USER_ID_TAG.length(), rongUserId.length());
        log.info("getUserIdFromRongUserId：剪裁后的 userId = " + rongUserId);
        return Long.parseLong(rongUserId);
    }

    public String getRongClubId(long clubId) {
        return RONG_CLUB_ID_TAG + clubId;
    }

    public long getClubIdFromRongClubId(String rongClubId) {
        rongClubId = rongClubId.substring(RONG_CLUB_ID_TAG.length(), rongClubId.length());
        log.info("getClubIdFromRongClubId：剪裁后的 clubId = " + rongClubId);
        return Long.parseLong(rongClubId);
    }

    public String getUserToken(long userId) {

        WCStudentBean userBean = mUserService.getUserInfoById(userId);

        if (userBean != null) {
            return getUserToken(userId, userBean.getRealName(), userBean.getAvatarUrl());
        }

        return null;
    }

    public String getUserToken(long userId, String userName, String avatarUrl) {

        try {
            TokenResult tokenResult = RongCloud.getInstance(Constants.RONGCLOUD_APP_KEY, Constants.RONGCLOUD_SECRET_KEY)
                    .user.getToken(getRongUserId(userId), userName, avatarUrl);

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

    public WCHttpStatus createGroupChat(long clubId, String clubName, long... userId) {

        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;
        String[] userIdStr = new String[userId.length];
        String clubIdStr = getRongClubId(clubId);

        for (int i = 0; i < userId.length; i++) {
            userIdStr[i] = getRongUserId(userId[i]);
        }

        try {
            CodeSuccessResult codeSuccessResult = RongCloud.getInstance(Constants.RONGCLOUD_APP_KEY, Constants.RONGCLOUD_SECRET_KEY)
                    .group.create(userIdStr, clubIdStr, clubName);
            if (codeSuccessResult != null && codeSuccessResult.getCode() == 200) {
                check = WCHttpStatus.SUCCESS;
                log.info("createGroupChat：" + codeSuccessResult.toString());
                return check;
            } else {
                check.msg = codeSuccessResult != null ? codeSuccessResult.getErrorMessage() : "创建群组失败";
                return check;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return check;
        }
    }

    public List<WCStudentBean> getStudentsByGroupId(long clubId) {
        try {
            GroupUserQueryResult queryResult = RongCloud.getInstance(Constants.RONGCLOUD_APP_KEY, Constants.RONGCLOUD_SECRET_KEY)
                    .group.queryUser(getRongClubId(clubId));

            if (queryResult != null && queryResult.getCode() == 200) {
                List<GroupUser> users = queryResult.getUsers();
                List<WCStudentBean> studentBeanList = new ArrayList<WCStudentBean>();
                for (GroupUser user : users) {
                    WCStudentBean studentBean = mUserService.getUserInfoById(getUserIdFromRongUserId(user.getId()));
                    if (studentBean != null) {
                        studentBeanList.add(studentBean);
                    }
                }

                return studentBeanList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WCGroupChatListModel> getMyClubChatList(long studentId) {

        List<WCClubBean> clubs = mClubService.getClubsByStudentId(studentId);

        List<WCGroupChatListModel> results = new ArrayList<WCGroupChatListModel>();

        if (clubs != null && clubs.size() > 0) {
            for (WCClubBean club : clubs) {
                results.add(new WCGroupChatListModel(club));
            }
        }
        return results;
    }

    public List<HashMap<String, Object>> getMyClubChatListForMap(long studentId) {
        List<WCGroupChatListModel> list = getMyClubChatList(studentId);

        if (list != null && list.size() > 0) {
            List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
            for (WCGroupChatListModel groupChatListModel : list) {
                result.add(groupChatListModel.toHashMap());
            }

            return result;
        }
        return null;
    }
}
