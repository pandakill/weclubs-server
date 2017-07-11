package com.weclubs.application.banner;

import com.weclubs.bean.WCBannerBean;
import com.weclubs.util.WCHttpStatus;

import java.util.HashMap;
import java.util.List;

/**
 * banner 广告图的 service
 *
 * Created by fangzanpan on 2017/7/11.
 */
public interface WCIBannerService {

    WCHttpStatus createBannerByList(List<WCBannerBean> list);

    List<WCBannerBean> getIndexBannerListBySchool(long schoolId);

    List<HashMap<String, Object>> getIndexBannerHashListBySchool(long schoolId);

    List<WCBannerBean> getBannerListBySchoolAndSource(long schoolId, String sourceType);
}
