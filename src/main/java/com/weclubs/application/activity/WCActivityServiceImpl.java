package com.weclubs.application.activity;

import com.weclubs.mapper.WCClubActivityMapper;
import com.weclubs.model.WCActivityDetailBaseModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 活动相关的 service 实现类
 *
 * Created by fangzanpan on 2017/3/28.
 */
@Service(value = "activityService")
class WCActivityServiceImpl implements WCIActivityService {

    private Logger log = Logger.getLogger(WCActivityServiceImpl.class);

    @Autowired
    private WCClubActivityMapper mActivityMapper;

    public List<WCActivityDetailBaseModel> getActivitiesByCurrentClub(long clubId) {

        if (clubId <= 0) {
            log.error("getActivitiesByCurrentClub：clubId不能小于等于0");
        }

        return mActivityMapper.getActivitiesByCurrentClubId(clubId);
    }
}
