package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.rongcloud.WCIRongCloudService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.mapper.WCClubMapper;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by fangzanpan on 2017/6/21.
 */
@RestController
@RequestMapping(value = "/test")
public class TestAPI {

    @Autowired
    private WCIClubService mClubService;
    @Autowired
    private WCIRongCloudService mRongCloudService;
    @Autowired
    private WCClubMapper mClubMapper;
    @Autowired
    private WCIUserService mUserService;

    @RequestMapping(value = "/create_group_chat", method = RequestMethod.POST)
    public WCResultData createGroupChat() {
        WCHttpStatus check = WCHttpStatus.SUCCESS;

        List<WCClubBean> clubs = mClubService.getClubsBySchoolId(1);

        for (WCClubBean club : clubs) {
            long clubId = club.getClubId();
            WCClubBean clubBean = mClubService.getClubInfoById(clubId);
            List<WCClubStudentBean> students = mClubMapper.getCurrentGraduateStudents(clubId);

            long[] studentIds = new long[students.size()];
            for (int i = 0; i < students.size(); i++) {
                studentIds[i] = students.get(i).getStudentId();
            }

            check = mRongCloudService.createGroupChat(clubId, clubBean.getName(), studentIds);
        }

        return WCResultData.getHttpStatusData(check, new HashMap<String, Object>());
    }

    @RequestMapping(value = "/get_im_user_token", method = RequestMethod.POST)
    public WCResultData getImUserToken() {

        long studentId = 1;

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);

        String token = mRongCloudService.getUserToken(studentId, studentBean.getRealName(), studentBean.getAvatarUrl());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_group_user_list", method = RequestMethod.POST)
    public WCResultData getGroupUserList() {

        long clubId = 31;

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user", mRongCloudService.getStudentsByGroupId(clubId));
        return WCResultData.getSuccessData(result);
    }
}
