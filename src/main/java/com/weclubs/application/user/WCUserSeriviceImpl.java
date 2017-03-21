package com.weclubs.application.user;

import com.weclubs.bean.WCStudentBean;
import com.weclubs.mapper.WCStudentMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户的service实现类
 *
 * Created by fangzanpan on 2017/3/21.
 */
@Service("userService")
public class WCUserSeriviceImpl implements WCIUserService {

    private Logger log = Logger.getLogger(WCUserSeriviceImpl.class);

    @Autowired
    private WCStudentMapper mStudentMapper;

    public WCStudentBean getUserInfoById(long userId) {

        if (userId <= 0) {
            log.error("getUserInfoById：userId不能小于等于0");
            return null;
        }

        return mStudentMapper.getStudentById(userId);
    }

    public WCStudentBean getUserInfoByMobile(String mobile) {

        if (StringUtils.isEmpty(mobile)) {
            log.error("getUserInfoByMobile：mobile不能为空");
            return null;
        }

        return mStudentMapper.getStudentByMobile(mobile);
    }

    public int checkMobileExist(String mobile) {

        int exitStatus = 404;

        if (StringUtils.isEmpty(mobile)) {
            log.error("checkMobileExist：mobile不能为空");
            exitStatus = -1;
            return exitStatus;
        }

        WCStudentBean studentBean = mStudentMapper.getStudentByMobile(mobile);

        if (studentBean != null) {
            exitStatus = studentBean.getStatus();
        }

        return exitStatus;
    }

    public WCStudentBean createUserByMobile(String mobile) {

        if (StringUtils.isEmpty(mobile)) {
            log.error("createUserByMobile：mobile不能为空");
            return null;
        }

        WCStudentBean studentBean = new WCStudentBean();
        studentBean.setMobile(mobile);

        mStudentMapper.createStudent(studentBean);

        log.info("创建完之后studentBean = " + studentBean.toString());
        return studentBean;
    }
}
