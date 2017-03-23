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

    void createMajor(String name, long schoolId);

    void updateSchool(WCSchoolBean schoolBean);

    void updateMajor(WCSchoolBean majorBean);

    WCSchoolBean getSchoolById(long schoolId);

    WCSchoolBean getMajorById(long majorId);

    List<WCSchoolBean> getSchools();

    List<WCSchoolBean> getMajorsBySchoolId(long schoolId);
}
