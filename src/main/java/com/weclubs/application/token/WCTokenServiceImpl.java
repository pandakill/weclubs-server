package com.weclubs.application.token;

import com.weclubs.bean.WCTokenBean;
import com.weclubs.mapper.WCTokenMapper;
import com.weclubs.util.Base64;
import com.weclubs.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * token的service层
 *
 * Created by fangzanpan on 2017/2/6.
 */
@Service("tokenService")
public class WCTokenServiceImpl implements WCITokenService {

    private Logger logger = Logger.getLogger(WCTokenServiceImpl.class);

    private static final long TOKEN_AVAILABLE_TIME = 7 * 24 * 60 * 60 * 1000;

    @Autowired
    private WCTokenMapper mTokenMapper;

    public void add(WCTokenBean bean) {
        mTokenMapper.createToken(bean);
    }

    public void update(WCTokenBean bean) {
        mTokenMapper.updateToken(bean);
    }

    public WCTokenBean findTokenByUserIdAndCaller(long userId, String caller) {
        return mTokenMapper.getTokenByUIdAndCaller(userId, caller);
    }

    public void deleteTokenByUserIdAndCaller(long userId, String caller) {
        mTokenMapper.deleteTokenByUIdAndCaller(userId, caller);
    }

    /**
     * 1、按照升序，password+userId进行md5加密
     * 2、然后将步骤1加密所得的字符串+caller进行md5加密
     * 3、将步骤3的加密字符串 + "_date=" + date 进行base64加密存储
     *
     * @param userId    用户唯一id
     * @param password  用户密码
     * @param caller    用户的caller
     * @return  生成的token
     */
    public String createTokenByUserIdAndCaller(long userId, String password, String caller) {
        WCTokenBean tokenBean = findTokenByUserIdAndCaller(userId, caller);
        String md5Psw = MD5.md5((password + userId));
        md5Psw = MD5.md5(md5Psw + caller);
        long currentTimeMillis = System.currentTimeMillis();
        String base64EncodeStr = md5Psw + "_date=" + currentTimeMillis;
        String token = Base64.encode(base64EncodeStr.getBytes());

        if (tokenBean != null) {
            tokenBean.setToken(token);
            tokenBean.setCreateDate(currentTimeMillis);
            update(tokenBean);
        } else {
            tokenBean = new WCTokenBean();
            tokenBean.setToken(token);
            tokenBean.setUId(userId);
            tokenBean.setCaller(caller);
            tokenBean.setCreateDate(currentTimeMillis);

            add(tokenBean);
        }

        return token;
    }

    /**
     * token有效时间默认为7天
     *
     * @param userId    用户id
     * @param caller    用户的caller
     * @param token     用户传进来的token
     * @return  如果token有效返回true
     */
    public boolean isTokenAvailable(long userId, String caller, String token) {

        WCTokenBean tokenBean = findTokenByUserIdAndCaller(userId, caller);
        if (tokenBean == null) {
            return false;
        }

        if (StringUtils.isEmpty(token)) {
            logger.info("token：" + tokenBean.getToken());
            logger.error("token为空");
            return false;
        }

        String decodeToken = new String(Base64.decode(token));
        String[] tokenDecode = decodeToken.split("_date=");
        if (tokenDecode.length != 2) {
            logger.info("token被篡改：解密的token为：" + Arrays.toString(tokenDecode));
            logger.info("token：" + tokenBean.getToken());
            return false;
        }
        long tokenDate;
        try {
            tokenDate = Long.parseLong(tokenDecode[1]);
        } catch (Exception e) {
            logger.info("token被篡改：时间戳格式有误.");
            logger.info("token：" + tokenBean.getToken());
            return false;
        }

        if (tokenBean.getCreateDate() != tokenDate) {
            logger.info("token.getCreateDate().getTime = " + tokenBean.getCreateDate() + "; tokenDate = " + tokenDate);
            logger.info("token被篡改：时间戳与数据库匹配不正确.");
            logger.info("token：" + tokenBean.getToken());
            return false;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - tokenDate >= TOKEN_AVAILABLE_TIME) {
            logger.info("token超时失效.");
            logger.info("token：" + tokenBean.getToken());
            return false;
        }

        return true;
    }
}
