package com.weclubs.model.response;

import com.weclubs.util.WCHttpStatus;

import java.util.ArrayList;

/**
 * 用户接口返回的数据模型
 *
 * Created by fangzanpan on 2017/2/4.
 */
public class WCResultData {
    private int result_code;
    private String result_msg;
    private long sys_time;
    private Object data;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        if (data instanceof ArrayList) {
            throw new IllegalArgumentException("data字段不允许为数组.");
        }

        this.data = data;
    }

    public static WCResultData getResultData(int code, String msg, Object obj) {
        WCResultData PDResultData = new WCResultData();
        PDResultData.setResult_code(code);
        PDResultData.setResult_msg(msg);
        try {
            PDResultData.setData(obj);
            PDResultData.setSys_time(System.currentTimeMillis());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            PDResultData.setResult_code(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS.code);
            PDResultData.setResult_msg(WCHttpStatus.FAIL_REQUEST_UNVALID_PARAMS.msg);
            PDResultData.setData("");
        }
        return PDResultData;
    }

    /**
     * 返回只有状态的结果，data内容为空
     *
     * @param code  状态值
     * @param msg   状态描述
     * @return  {@link WCResultData}
     */
    public static WCResultData getResultData(int code, String msg) {
        return getResultData(code, msg, null);
    }

    /**
     * 请求成功时需要返回的result结果
     *
     * @param obj   返回结果，obj不能为数组对象，否则会报错
     * @return  返回请求成功的 {@link WCResultData}
     */
    public static WCResultData getSuccessData(Object obj) {
        return getResultData(WCHttpStatus.SUCCESS.code, WCHttpStatus.SUCCESS.msg, obj);
    }

    /**
     * token失效的返回值
     *
     * @return  返回token失效的返回值 {@link WCResultData}
     */
    public static WCResultData getTokenUnvalidData() {
        return getResultData(WCHttpStatus.FAIL_USER_TOKEN_UNVALID.code, WCHttpStatus.FAIL_USER_TOKEN_UNVALID.msg, null);
    }

    public static WCResultData getHttpStatusData(WCHttpStatus status, Object obj) {
        return getResultData(status.code, status.msg, obj);
    }

    @Override
    public String toString() {
        return "{" +
                "result_code:" + result_code +
                ", result_msg:'" + result_msg + '\'' +
                ", sys_time:" + sys_time +
                ", data:" + (data != null ? data.toString() : "") +
                '}';
    }
}
