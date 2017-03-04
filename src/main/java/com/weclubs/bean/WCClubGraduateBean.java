package com.weclubs.bean;

import java.io.Serializable;

/**
 * 社团届数表信息，对应 t_club_graduate 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCClubGraduateBean implements Serializable {

    private long id;
    private long clubId;
    private int graduateCount;
    private String graduateName;
    private int isCurrent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public int getGraduateCount() {
        return graduateCount;
    }

    public void setGraduateCount(int graduateCount) {
        this.graduateCount = graduateCount;
    }

    public String getGraduateName() {
        return graduateName;
    }

    public void setGraduateName(String graduateName) {
        this.graduateName = graduateName;
    }

    public int getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(int isCurrent) {
        this.isCurrent = isCurrent;
    }

    @Override
    public String toString() {
        return "WCClubGraduateBean{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", graduateCount=" + graduateCount +
                ", graduateName='" + graduateName + '\'' +
                ", isCurrent=" + isCurrent +
                '}';
    }
}
