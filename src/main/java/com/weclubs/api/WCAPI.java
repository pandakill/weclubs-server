package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.MD5;
import com.weclubs.util.WCHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by fangzanpan on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/p")
public class WCAPI {

    @Autowired
    private WCIClubService mClubService;
    @Autowired
    private WCIUserService mUserService;

    @RequestMapping(value = "/create_users", method = RequestMethod.POST)
    public WCResultData createUsers(@RequestBody WCRequestModel requestModel) {
        HashMap<String, Object> requestData = (HashMap<String, Object>) requestModel.getData();
        List users = (List) requestData.get("users");
        System.out.println("users = " + users.toString());

        for (Object user : users) {
            WCStudentBean student = new WCStudentBean();
            student.setNickName((String) ((HashMap<String, Object>) user).get("nick_name"));
            student.setRealName((String) ((HashMap<String, Object>) user).get("real_name"));
            student.setMobile((String) ((HashMap<String, Object>) user).get("mobile"));
            mUserService.createUser(student);

            if (student.getStudentId() == 0) {
                return WCResultData.getHttpStatusData(WCHttpStatus.FAIL_REQUEST, null);
            }

            mUserService.changePassword(student.getStudentId(), MD5.md5("123456"));
        }

        return WCResultData.getSuccessData(null);
    }
}
