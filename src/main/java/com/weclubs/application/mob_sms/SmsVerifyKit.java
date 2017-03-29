package com.weclubs.application.mob_sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * 短信验证工具
 *
 * Created by fangzanpan on 2017/3/22.
 */
public class SmsVerifyKit {

    private Logger log = Logger.getLogger(SmsVerifyKit.class);

    private final static String ANDROID_APP_KEY = "1c36d86921782";  //padakill007@gmail.com; weclubs 应用
    private final static String IOS_APP_KEY = "1c8a005d31dea";  // linyanzuo1222@gmail.com; weclubs 应用

    private String appkey;
    private String phone ;
    private String zone ;
    private String code ;

    /**
     *
     * @param appkey 应用KEY
     * @param phone 电话号码 xxxxxxxxx
     * @param zone 区号 86
     * @param code 验证码 xx
     */
    public SmsVerifyKit(String appkey, String phone, String zone, String code) {
        super();
        this.appkey = appkey;
        this.phone = phone;
        this.zone = zone;
        this.code = code;
    }

    public SmsVerifyKit(String caller, String phone, String code) {
        super();
        this.phone = phone;
        this.code = code;
        this.zone = "86";

        if (caller.contains("ios")) {
            this.appkey = IOS_APP_KEY;
        } else if (caller.contains("android")) {
            this.appkey = ANDROID_APP_KEY;
        }
    }

    /**
     * 服务端发起验证请求验证移动端(手机)发送的短信
     * @return  拼接完成的key
     * @throws Exception    抛出字节流异常
     */
    public  STATUS go() throws Exception{

        String address = "https://webapi.sms.mob.com/sms/verify";

        MobClient client = null;
        try {
            client = new MobClient(address);
            client.addParam("appkey", appkey).addParam("phone", phone)
                    .addParam("zone", zone).addParam("code", code);
            client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            client.addRequestProperty("Accept", "application/json");
            String result = client.post();

            log.info("register：验证结果：" + result);
            JSONObject statusObj = JSON.parseObject(result);
            if (statusObj == null) {
                log.error("状态码为空");
                return STATUS.VERIFY_CODE_ERROR;
            }
            int status = statusObj.getInteger("status");
            return getStatusByCode(status);
        } finally {
            if (client != null) {
                client.release();
            }
        }
    }

    private STATUS getStatusByCode(int status) {
        STATUS result = STATUS.VERIFY_CODE_ERROR;
        switch (status) {
            case 200:
                result = STATUS.SUCCESS;
                break;
            case 405:
                result = STATUS.APPKEY_NULL;
                break;
            case 406:
                result = STATUS.APPKEY_UNAVAILABLE;
                break;
            case 456:
                result = STATUS.COUNT_OR_MOBILE_NULL;
                break;
            case 457:
                result = STATUS.MOBILE_UNVAILABLE;
                break;
            case 466:
                result = STATUS.VERIFY_CODE_NULL;
                break;
            case 467:
                result = STATUS.FREQUENTLY_REQUEST;
                break;
            case 468:
                result = STATUS.VERIFY_CODE_ERROR;
                break;
            case 474:
                result = STATUS.UNOPEN_SERVER_VERIFY;
                break;
        }
        return result;
    }

    public enum STATUS {

        SUCCESS(200, "验证成功"),   // 验证成功
        APPKEY_NULL(405, "AppKey 为空"),  // AppKey 为空
        APPKEY_UNAVAILABLE(406, "AppKey 无效"),   //AppKey 无效
        COUNT_OR_MOBILE_NULL(456, "国家代码或者手机号码为空"),  // 国家代码或者手机号码为空
        VERIFY_CODE_NULL(466, "请求校验的验证码为空"),    // 请求校验的验证码为空
        MOBILE_UNVAILABLE(457, "手机号码格式错误"), // 手机号码格式错误
        FREQUENTLY_REQUEST(467, "请求校验验证码频繁"),   // 请求校验验证码频繁（5分钟内同一个appkey的同一个号码最多只能校验三次）
        VERIFY_CODE_ERROR(468, "验证码错误"),    // 验证码错误
        UNOPEN_SERVER_VERIFY(474, "没有打开服务端验证开关");   // 没有打开服务端验证开关

        int code;
        String tips;

        STATUS (int code, String tips) {
            this.code = code;
            this.tips = tips;
        }
    }
}
