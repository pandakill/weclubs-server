package com.weclubs.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 广告图的实体 bean
 *
 * Created by fangzanpan on 2017/6/23.
 */
public class WCBannerBean implements Serializable {

    public final static int BANNER_STATUS_DEL = -1; // 删除状态
    public final static int BANNER_STATUS_OFF_LINE = 0; // 未上线状态
    public final static int BANNER_STATUS_ON_LINE = 1;  // 上线状态

    public final static String SOURCE_INDEX = "index";  // 首页展示来源

    private long bannerId;
    private long schoolId;
    private String title;
    private String attribution;
    private String imgUrl;
    private String sourceType;
    private long beginDate;
    private long endDate;
    private long createDate;
    private int status;
    private String extra;

    public long getBannerId() {
        return bannerId;
    }

    public void setBannerId(long bannerId) {
        this.bannerId = bannerId;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
