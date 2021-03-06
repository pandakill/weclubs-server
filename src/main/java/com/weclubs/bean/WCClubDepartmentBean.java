package com.weclubs.bean;

/**
 * 社团部门实体，对应 t_club_department 表
 *
 * Created by fangzanpan on 2017/3/10.
 */
public class WCClubDepartmentBean {

    private long departmentId;
    private String departmentName;
    private int isSuggest;
    private int isDel;

    private int isSelected;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "WCClubDepartmentBean{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", isSuggest=" + isSuggest +
                ", isDel=" + isDel +
                '}';
    }
}
