package com.weclubs.application.club;

import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.model.WCManageClubModel;
import com.weclubs.model.WCMyClubModel;
import com.weclubs.model.WCStudentForClubModel;
import com.weclubs.util.WCHttpStatus;

import java.util.ArrayList;
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

    ArrayList<HashMap<String, Object>> getStudentsByCurrentGraduate(long clubId, int sortType);

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

    /**
     * 设置社团头像
     *
     * @param avatarUrl 头像地址
     * @param clubId 社团id
     *
     * @return  如果修改设置成功，则返回 {@link WCHttpStatus#SUCCESS}
     *          否则返回 {@link WCHttpStatus#FAIL_REQUEST}
     */
    WCHttpStatus setClubAvatar(String avatarUrl, long clubId);

    /**
     * 获取当前届的所有学生
     *
     * @param clubId    社团id
     * @return  返回当前届的所有学生
     */
    List<WCClubStudentBean> getCurrentGraduateStudentsByClubId(long clubId);

    /**
     * 判断该学生是否在此社团
     *
     * @param studentId 学生id
     * @param clubId    社团id
     * @return  该学生是否已经加入该当前届社团
     */
    boolean checkStudentExitCurrentGraduate(long studentId, long clubId);

    /**
     * 搜索社团名字
     *
     * @param keywords  关键词
     *
     * @return  搜索结果
     */
    List<WCClubBean> searchClubList(long schoolId, String... keywords);

    /**
     * 根据学校 id 获取该学校的热门社团
     *
     * @param schoolId  学校 id
     * @return  返回热门社团的列表
     */
    List<HashMap<String, Object>> getHotClubBySchool(long schoolId);

    /**
     * 用户申请加入社团
     *
     * @param userId    申请的用户id
     * @param clubId    社团id
     * @return  如果申请成功返回 {@link WCHttpStatus#SUCCESS}
     */
    WCHttpStatus applyForClub(long userId, long clubId);
}
