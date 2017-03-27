package com.weclubs.model;

import java.io.Serializable;

/**
 * 学生基本信息类
 *
 * Created by fangzanpan on 2017/3/27.
 */
public class WCStudentBaseInfoModel implements Serializable {

    private long studetnId;
    private String studentName;

    private long departmentId;
    private String deparmentName;

    private int gradueate;

    private long schoolId;
    private String schoolName;

    private long collegeId;
    private String collegeName;

    private String className;
    private String majorName;

    private String avatarUrl;
    private String mobile;
}
