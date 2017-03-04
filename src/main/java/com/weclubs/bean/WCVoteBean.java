package com.weclubs.bean;

import java.io.Serializable;

/**
 * 投票主题表，对应数据库 t_vote 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCVoteBean implements Serializable {

    private long id;
    private long clubId;
    private String subject;
    private int optionCount;
    private int isUndefined;
    private String createDate;
    private int isAnonymous;
    private int isPublic;
    private int isTermination;
    private int isDel;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount = optionCount;
    }

    public int getIsUndefined() {
        return isUndefined;
    }

    public void setIsUndefined(int isUndefined) {
        this.isUndefined = isUndefined;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getIsTermination() {
        return isTermination;
    }

    public void setIsTermination(int isTermination) {
        this.isTermination = isTermination;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "WCVoteBean{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", subject='" + subject + '\'' +
                ", optionCount=" + optionCount +
                ", isUndefined=" + isUndefined +
                ", createDate='" + createDate + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", isPublic=" + isPublic +
                ", isTermination=" + isTermination +
                ", isDel=" + isDel +
                '}';
    }
}
