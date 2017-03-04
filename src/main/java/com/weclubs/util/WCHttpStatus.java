package com.weclubs.util;

/**
 * 接口请求的数据返回代码和状态值
 *
 * Created by fangzanpan on 2017/2/4.
 */
public enum WCHttpStatus {

    SUCCESS(2000, "请求成功."),

    FAIL_REQUEST(3000, "请求失败."),
    FAIL_REQUEST_UNVALID_PARAMS(3001, "参数违法."),
    FAIL_USER_TOKEN_UNVALID(3010, "token失效."),
    FAIL_USER_PSW_UNVALID(3011, "密码错误."),
    FAIL_USER_UNPERMISSION(3012, "用户没有改权限."),
    FAIL_USER_UNKNOWK(3014, "找不到该用户."),

    FAIL_APPLICATION_ERROR(5000, "程序出错.")
    ;

    public int code;
    public String msg;

    WCHttpStatus(int value, String msg) {
        this.code = value;
        this.msg = msg;
    }
}
