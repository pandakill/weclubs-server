package com.weclubs.application.dynamic;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;

/**
 * 动态的 service 接口类
 *
 * Created by fangzanpan on 2017/3/27.
 */
public interface WCIDynamicService {

    WCStudentMissionRelationBean getDynamicStudentRelationByDynamicId(long studentId, long dynamicId, String dynamicType);

    WCClubMissionBean getDynamicDetail(long dynamicId, String type);
}
