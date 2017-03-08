package com.weclubs.mapper;

import com.weclubs.bean.WCClubGraduateBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_graduate 社团届数的映射接口
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCClubGraduateMapper {

    void createClubGraduate(WCClubGraduateBean clubGraduateBean);

    void updateClubGraduate(WCClubGraduateBean clubGraduateBean);

    void deleteClubGraduateById(@Param("clubGraduateId") long clubGraduateId);

    List<WCClubGraduateBean> getClubGraduatesByClubId(@Param("clubId") long clubId);

    WCClubGraduateBean getClubGraduateByClubGraduateId(@Param("clubGraduateId") long clubGraduateId);

    WCClubGraduateBean getCurrentClubGraduateByClubId(@Param("clubId") long clubId);
}
