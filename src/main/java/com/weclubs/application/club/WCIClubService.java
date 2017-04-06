package com.weclubs.application.club;

import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.model.WCManageClubModel;
import com.weclubs.model.WCMyClubModel;
import com.weclubs.model.WCStudentForClubModel;

import java.util.List;

/**
 * 社团服务类的接口层
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCIClubService {

    WCClubBean getClubInfoById(long clubId);

    void createClub(WCClubBean clubBean);

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
}
