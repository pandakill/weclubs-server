package com.weclubs.bean;

import java.io.Serializable;

/**
 * 投票主题表，对应数据库 t_vote 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCVoteBean implements Serializable {

    private long voteId;
    private long clubId;
    private String subject;
    private int optionCount;
    private int isUndefined;
    private long createDate;
    private int isAnonymous;
    private int isPublic;
    private int isTermination;
    private int isDel;

    private WCClubBean clubBean;

    public long getVoteId() {
        return voteId;
    }

    public void setVoteId(long voteId) {
        this.voteId = voteId;
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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
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

    public WCClubBean getClubBean() {
        return clubBean;
    }

    public void setClubBean(WCClubBean clubBean) {
        this.clubBean = clubBean;
    }

    @Override
    public String toString() {
        return "WCVoteBean{" +
                "voteId=" + voteId +
                ", clubId=" + clubId +
                ", subject='" + subject + '\'' +
                ", optionCount=" + optionCount +
                ", isUndefined=" + isUndefined +
                ", createDate='" + createDate + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", isPublic=" + isPublic +
                ", isTermination=" + isTermination +
                ", isDel=" + isDel +
                ", clubBean=" + clubBean +
                '}';
    }
}
