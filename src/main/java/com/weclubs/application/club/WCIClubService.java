package com.weclubs.application.club;

import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.model.WCManageClubModel;
import com.weclubs.model.WCMyClubModel;
import com.weclubs.model.WCStudentForClubModel;

import java.util.HashMap;
import java.util.List;

/**
 * 社团服务类的接口层
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCIClubService {

    WCClubBean getClubInfoById(long clubId);

    long createClub(WCClubBean clubBean, long studentId);

    void updateClub(WCClubBean clubBean);

    List<WCClubBean> getClubsBySchoolId(long schoolId);

    List<WCClubBean> getClubsByStudentId(long studentId);

    List<WCClubHonorBean> getClubHonorByClubId(long clubId);

    List<WCClubStudentBean> getStudentsByCurrentGraduate(long clubId, int sortType);

    List<WCMyClubModel> getMyClubs(long studentId);

    WCStudentForClubModel getClubStudentByStudentId(long studentId, long clubId);

    /**
     * 根据学生id 获取该学生具有管理权限的社团列表
     *
     * @param studentId 学生id
     * @return  返回该学生具有管理权限的社团
     */
    List<WCManageClubModel> getMyManageClubs(long studentId);

    /**
     * 批量添加社团荣誉列表
     *
     * @param clubId    社团id
     * @param honorList 荣誉列表键值对
     */
    void addClubHonor(long clubId, List<HashMap<String, Object>> honorList);

    /**
     * 更新社团荣誉列表
     *
     * @param honorList 社团荣誉列表键值对
     */
    void updateClubHonor(List<HashMap<String, Object>> honorList);

    /**
     * 检查某社团名称是否在该学校中已经存在
     *
     * @param clubName  社团名字
     * @param schoolId  学校id
     *
     * @return  true：已经存在；false：不存在
     */
    boolean checkClubExit(String clubName, long schoolId);
}
