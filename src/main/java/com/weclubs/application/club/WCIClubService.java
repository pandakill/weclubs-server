package com.weclubs.application.club;

import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCClubStudentBean;

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

    String getCacheTest();

    List<WCClubStudentBean> getStudentsByCurrentGraduate(long clubId, int sortType);
}
