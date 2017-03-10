package com.weclubs.mapper;

import com.weclubs.bean.WCClubAuthorityBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_authority 权限的映射接口
 *
 * Created by fangzanpan on 2017/3/10.
 */
public interface WCClubAuthorityMapper {

    void createAuthority(WCClubAuthorityBean authorityBean);

    void deleteAuthority(@Param("authorityId") long authorityId);

    WCClubAuthorityBean getAuthorityById(@Param("authorityId") long authorityId);

    List<WCClubAuthorityBean> getAuthorities();
}
