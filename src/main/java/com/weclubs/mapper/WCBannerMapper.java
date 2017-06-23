package com.weclubs.mapper;

import com.weclubs.bean.WCBannerBean;

import java.util.List;

/**
 * 广告图的 mapper
 *
 * Created by fangzanpan on 2017/6/23.
 */
public interface WCBannerMapper {

    List<WCBannerBean> getBannerList(long schoolId);


}
