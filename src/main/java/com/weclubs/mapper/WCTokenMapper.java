package com.weclubs.mapper;

import com.weclubs.bean.WCTokenBean;
import org.apache.ibatis.annotations.Param;

/**
 * mybatis 的token映射接口，对应数据库的 t_token
 *
 * Created by fangzanpan on 2017/3/4.
 */
public interface WCTokenMapper {

    /**
     * 根据 uId 和 caller 查找对应的 token 实体对象
     *
     * @param uId   uId，相当于 t_student.id
     * @param caller    caller 值
     *
     * @return  返回当前的 token 实体对象，不存在则返回 null
     */
    WCTokenBean getTokenByUIdAndCaller(@Param("uId") long uId, @Param("caller") String caller);

    void createToken(WCTokenBean tokenBean);

    /**
     * 根据 uId 和 caller 删除对应的 token 实体对象
     *
     * @param uId   uId，相当于 t_student.id
     * @param caller    caller 值
     */
    void deleteTokenByUIdAndCaller(@Param("uId") long uId, @Param("caller") String caller);

    void updateToken(WCTokenBean tokenBean);
}
