package com.weclubs.application.dynamic;

import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.util.WCHttpStatus;

/**
 * 动态的 service 接口类
 *
 * Created by fangzanpan on 2017/3/27.
 */
public interface WCIDynamicService {

    WCStudentMissionRelationBean getDynamicStudentRelationByDynamicId(long studentId, long dynamicId, String dynamicType);

    /**
     * 获取动态详情
     *
     * @param dynamicId 动态id
     * @param type  动态的类型
     * @return  返回动态详情类
     */
    WCClubMissionBean getDynamicDetail(long dynamicId, String type);

    /**
     * 设置动态的状态
     *
     * @param studentId 学生id
     * @param dynamicId 动态id
     * @param type  动态类型
     * @param status 状态，如果有多的参数的话，第一个参数为状态，第二个为状态理由
     * @return  设置结果
     */
    WCHttpStatus setDynamicStatus(long studentId, long dynamicId, String type, String... status);
}
