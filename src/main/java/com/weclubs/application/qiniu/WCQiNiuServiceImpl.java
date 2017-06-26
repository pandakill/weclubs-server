package com.weclubs.application.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.weclubs.util.WCHttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
        result.put("avatar_config", "?imageMogr2/auto-orient/thumbnail/200x200>/gravity/Center/crop/400x400/format/jpg/blur/1x0/quality/90|imageslim");

        return result;
    }

    public String getUploadToken() {
        Auth auth = Auth.create("hzCyB2-zRuT70ZzXogZx4FghzxyNnfNrWgMaE-O1", "2pAkT3kVSB5vnN4z_ubt5Y3tLyEa_hqhpAddlXdm");
        String bucket = "pdaily";
        long expiresTime = 3 * 60 * 1000;

        return auth.uploadToken(bucket, null, expiresTime, null);
    }

    @Override
    public WCHttpStatus uploadFile(InputStream inputStream) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        String upToken = getUploadToken();
        try {
            Response response = uploadManager.put(inputStream,null,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }
}
