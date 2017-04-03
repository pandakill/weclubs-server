package com.weclubs.application.school;

import com.weclubs.bean.WCSchoolBean;

import java.util.List;

/**
 * 学校、专业类的 service
 *
 * Created by fangzanpan on 2017/3/23.
 */
public interface WCISchoolService {

    void createSchool(String name);

    void createCollege(String name, long schoolId);

    void updateSchool(WCSchoolBean schoolBean);

    void updateCollege(WCSchoolBean collegeBean);

    WCSchoolBean getSchoolById(long schoolId);

    WCSchoolBean getCollegeById(long collegeId);

    List<WCSchoolBean> getSchools();

    List<WCSchoolBean> getCollegesBySchoolId(long schoolId);
}
