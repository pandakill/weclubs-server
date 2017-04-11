package com.weclubs.application.qiniu;

import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 七牛上传文件的相关 service 实现类
 *
 * Created by fangzanpan on 2017/4/7.
 */
@Service(value = "qiNiuService")
class WCQiNiuServiceImpl implements WCIQiNiuService {

    public HashMap<String, Object> getUploadConfig() {

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("domain", "http://on633pcgq.bkt.clouddn.com/");
        result.put("avatar_config", "?imageMogr2/auto-orient/gravity/Center/crop/400x400/format/webp/blur/1x0/quality/90|imageslim");

        return result;
    }

    public String getUploadToken() {
        Auth auth = Auth.create("hzCyB2-zRuT70ZzXogZx4FghzxyNnfNrWgMaE-O1", "2pAkT3kVSB5vnN4z_ubt5Y3tLyEa_hqhpAddlXdm");
        String bucket = "pdaily";
        long expiresTime = 3 * 60 * 1000;

        return auth.uploadToken(bucket, null, expiresTime, null);
    }
}
