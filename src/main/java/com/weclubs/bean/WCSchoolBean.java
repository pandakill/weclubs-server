package com.weclubs.bean;

import java.io.Serializable;

/**
 * 学校、院系、专业关系表，对应数据库 t_school 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCSchoolBean implements Serializable {

    private long id;
    private String name;
    private long parentId;
    private int isDel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "WCSchoolBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", isDel=" + isDel +
                '}';
    }
}
