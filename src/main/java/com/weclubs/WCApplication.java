package com.weclubs;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用的配置和启动入口
 *
 * Created by fangzanpan on 2017/3/4.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan("com.weclubs")
public class WCApplication {

    private static Logger log = Logger.getLogger(WCApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(WCApplication.class, args);

        log.info("weClubs Application run.");
    }

    @RequestMapping("/")
    public String home() {

        log.info("welcome to weClubs application.");

        return "welcome to weClubs application.";
    }
}
