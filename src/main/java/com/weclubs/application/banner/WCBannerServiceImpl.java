package com.weclubs.application.banner;

import com.weclubs.application.school.WCISchoolService;
import com.weclubs.bean.WCBannerBean;
import com.weclubs.mapper.WCBannerMapper;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * banner 的 service 实现类
 *
 * Created by fangzanpan on 2017/7/11.
 */
@Service(value = "bannerService")
class WCBannerServiceImpl implements WCIBannerService {

    private Logger log = Logger.getLogger(WCBannerServiceImpl.class);

    @Autowired
    private WCBannerMapper mBannerMapper;
    @Autowired
    private WCISchoolService mSchoolService;

    @Override
    public WCHttpStatus createBannerByList(List<WCBannerBean> list) {
        WCHttpStatus check = WCHttpStatus.FAIL_REQUEST;

        if (list == null || list.size() == 0) {
            check.msg = "列表不能为空";
            return check;
        }

        if (list.size() == 1) {
            mBannerMapper.createBanner(list.get(0));
        } else {
            mBannerMapper.createBannerByList(list);
        }

        check = WCHttpStatus.SUCCESS;
        return check;
    }

    @Override
    public List<WCBannerBean> getIndexBannerListBySchool(long schoolId) {
        return getBannerListBySchoolAndSource(schoolId, WCBannerBean.SOURCE_INDEX);
    }

    @Override
    public List<HashMap<String, Object>> getIndexBannerHashListBySchool(long schoolId) {

        List<WCBannerBean> bannerList = getIndexBannerListBySchool(schoolId);

        if (bannerList == null || bannerList.size() == 0) {
            log.info("getIndexBannerHashListBySchool：读取数据库为空");
            return null;
        }
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        result.addAll(bannerList.stream().map(WCBannerBean::toHashMap).collect(Collectors.toList()));
        return result;
    }

    @Override
    public List<WCBannerBean> getBannerListBySchoolAndSource(long schoolId, String sourceType) {
        return mBannerMapper.getBannerListBySource(schoolId, WCBannerBean.SOURCE_INDEX);
    }
}
