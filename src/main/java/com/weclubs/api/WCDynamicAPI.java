package com.weclubs.api;

import com.weclubs.application.security.WCISecurityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态相关的 API
 *
 * Created by fangzanpan on 2017/3/24.
 */
@RestController
@RequestMapping(value = "dynamic")
public class WCDynamicAPI {

    private Logger log = Logger.getLogger(WCDynamicAPI.class);

    @Autowired
    private WCISecurityService mSecurityService;

//    public WCResultData get
}
