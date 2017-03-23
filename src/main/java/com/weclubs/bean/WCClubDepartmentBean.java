package com.weclubs.bean;

/**
 * 社团部门实体，对应 t_club_department 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubDepartmentBean {

    private long departmentId;
    private String name;
    private int isSuggest;
    private int isDel;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(int isSuggest) {
        this.isSuggest = isSuggest;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WCClubDepartmentBean{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", isSuggest=" + isSuggest +
                ", isDel=" + isDel +
                '}';
    }
}
