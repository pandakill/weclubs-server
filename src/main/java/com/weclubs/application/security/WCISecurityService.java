package com.weclubs.application.security;

import com.weclubs.bean.WCStudentBean;
import com.weclubs.model.request.WCRequestModel;
import com.weclubs.util.WCHttpStatus;

import java.util.HashMap;

/**
 * API接口的安全检查接口
 * 1. 在这里会进行记录请求是否合法
 * 2. token验证
 * 3. 设备的记录
 * 4. 唯一UID的记录
 *
 * Created by fangzanpan on 2017/2/5.
 */
public interface WCISecurityService {

    /**
     * 检查用户登录token是否合法;每个token有效时间为7天时间
     *
     * @param token 用户登录的token，唯一；在缓存文件token.txt文件中获取
     *
     * @return  如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkTokenAvailable(long userId, String caller, String token);

    /**
     * 检验请求参数是否合法
     *
     * @param data    请求参数列表,即请求参数里面的data数据
     * @return  如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkDataAvailable(HashMap<String, Object> data, String sign);

    /**
     * 检查该请求id是否唯一
     *
     * @param id    请求id
     * @return 如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkRequestID(long id);

    /**
     * 检查请求参数是否符合要求,请求参数的格式如下
     *
     * @param params    请求参数
     * @return 如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkRequestParams(HashMap<String, Object> params);

    /**
     * 检查请求参数是否符合要求,请求参数的格式如下
     *
     * @param requestModel    请求参数
     * @return 如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkRequestParams(WCRequestModel requestModel);

    /**
     * 根据请求参数判断token是否合法
     *
     * @param requestModel  请求参数
     * @return 如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkTokenAvailable(WCRequestModel requestModel);

    /**
     * 判断 caller 是否合法
     *
     * @param caller    请求参数中的 caller 值
     * @return 如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkCallerAvailable(String caller);

    /**
     * 检查登录名和密码是否正确
     *
     * @param studentBean    学生实体
     * @param password  用户密码
     * @return 如果合法，返回 {@link WCHttpStatus} 中的SUCCESS
     */
    WCHttpStatus checkPasswordAvailable(WCStudentBean studentBean, String password);

    /**
     * 根据客户端上传的的密码，以及用户 id 进行二次加密，该返回得到的即为数据库存储的密码字段
     * 客户端上传的密码应该是： MD5(BASE64(password))
     *
     * @param userId    用户 id
     * @param password  客户端上传的密码
     * @return  二次加密后的密码
     */
    String encodePassword(long userId, String password);

}
