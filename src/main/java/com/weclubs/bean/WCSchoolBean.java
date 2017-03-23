package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学校、院系、专业关系表，对应数据库 t_school 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCSchoolBean implements Serializable {

    private long schoolId;
    private String name;
    private long parentId;
    private int isDel;

    private WCSchoolBean parentSchoolBean;

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCSchoolBean getParentSchoolBean() {
        return parentSchoolBean;
    }

    public void setParentSchoolBean(WCSchoolBean parentSchoolBean) {
        this.parentSchoolBean = parentSchoolBean;
    }

    @Override
    public String toString() {
        return "WCSchoolBean{" +
                "schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", isDel=" + isDel +
                ", parentSchoolBean=" + parentSchoolBean +
                '}';
    }
}
