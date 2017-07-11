package com.weclubs.mapper;

import com.weclubs.bean.WCBannerBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 广告图的 mapper
 *
 * Created by fangzanpan on 2017/6/23.
 */
public interface WCBannerMapper {

    void createBanner(WCBannerBean bannerBean);

    void createBannerByList(List<WCBannerBean> list);

    List<WCBannerBean> getBannerListBySource(@Param("schoolId") long schoolId, @Param("sourceType") String sourceType);

    List<WCBannerBean> getBannerListByAll(@Param("schoolId") long schoolId);
}
