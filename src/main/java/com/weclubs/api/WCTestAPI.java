package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.model.WCResultData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 测试接口
 *
 * Created by fangzanpan on 2017/3/8.
 */
@RestController
@RequestMapping(value = "/test")
public class WCTestAPI {

    private Logger log = Logger.getLogger(WCTestAPI.class);

    @Autowired
    private WCIClubService mClubService;

    @RequestMapping(value = "/getClubsBySchoolId", method = RequestMethod.GET)
    public WCResultData getClubsBySchoolId() {

        log.info("getClubsBySchoolId：获取 schoolId = 1 的学校所有社团");

        long schoolId = 1;

        List<WCClubBean> clubs = mClubService.getClubsBySchoolId(schoolId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("clubs", clubs);

        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "getClubDetailById", method = RequestMethod.GET)
    public WCResultData getClubDetailById () {

        log.info("getClubDetailById：获取 clubId = 1 的社团详情");

        long clubId = 1;

        WCClubBean clubBean = mClubService.getClubInfoById(clubId);

        return WCResultData.getSuccessData(clubBean);
    }
}
