package com.weclubs.mapper;

import com.weclubs.bean.WCSchoolBean;

import java.util.List;

/**
 * 学校的映射表
 *
 * Created by fangzanpan on 2017/3/23.
 */
public interface WCSchoolMapper {

    void createSchool(String name);

    void createCollege(String name, long schoolId);

    void updateSchool(WCSchoolBean schoolBean);

    void updateCollege(WCSchoolBean collegeBean);

    WCSchoolBean getSchoolById(long schoolId);

    WCSchoolBean getCollegeById(long CollegeId);

    List<WCSchoolBean> getSchools();

    List<WCSchoolBean> getCollegesBySchoolId(long schoolId);
}
