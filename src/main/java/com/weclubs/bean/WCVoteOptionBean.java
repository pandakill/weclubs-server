package com.weclubs.bean;

import java.io.Serializable;

/**
 * 投票选项内容表，对应数据库 t_vote_option 表
 *
 * Created by fangzanpan on 2017/3/4.
 */
public class WCVoteOptionBean implements Serializable {

    private long voteOption;
    private long voteId;
    private String content;
    private int isDel;

    private WCVoteBean voteBean;

    public long getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(long voteOption) {
        this.voteOption = voteOption;
    }

    public long getVoteId() {
        return voteId;
    }

    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public WCVoteBean getVoteBean() {
        return voteBean;
    }

    public void setVoteBean(WCVoteBean voteBean) {
        this.voteBean = voteBean;
    }

    @Override
    public String toString() {
        return "WCVoteOptionBean{" +
                "voteOption=" + voteOption +
                ", voteId=" + voteId +
                ", content='" + content + '\'' +
                ", isDel=" + isDel +
                ", voteBean=" + voteBean +
                '}';
    }
}
