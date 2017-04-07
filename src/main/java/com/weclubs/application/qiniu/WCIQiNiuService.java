package com.weclubs.application.qiniu;

import java.util.HashMap;

/**
 * 七牛的相关接口的 service 接口类
 *
 * Created by fangzanpan on 2017/4/7.
 */
public interface WCIQiNiuService {

    HashMap<String, Object> getUploadConfig();

    String getUploadToken();
}
