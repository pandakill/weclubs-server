package com.weclubs.application.token;


import com.weclubs.bean.WCTokenBean;

/**
 * token的dao接口
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDITokenService {

    WCTokenBean findTokenByUserIdAndCaller(long userId, String caller);

    void deleteTokenByUserIdAndCaller(long userId, String caller);

    String createTokenByUserIdAndCaller(long userId, String password, String caller);

    boolean isTokenAvailable(long userId, String caller, String token);
}
