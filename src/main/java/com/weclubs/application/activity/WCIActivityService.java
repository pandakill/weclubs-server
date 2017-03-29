package com.weclubs.application.activity;

import com.weclubs.model.WCActivityDetailBaseModel;

import java.util.List;

/**
 * 活动相关的 service 接口类
 *
 * Created by fangzanpan on 2017/3/28.
 */
public interface WCIActivityService {

    List<WCActivityDetailBaseModel> getActivitiesByCurrentClub(long clubId);
}
