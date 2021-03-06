package com.weclubs.mapper;

import com.weclubs.bean.WCClubHonorBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_honor 社团荣誉的映射接口
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCClubHonorMapper {

    void createClubHonor(WCClubHonorBean clubHonorBean);

    void updateClubHonor(WCClubHonorBean clubHonorBean);

    void deleteClubHonorById(@Param("clubHonorId") long clubHonorId);

    List<WCClubHonorBean> getClubHonorsByClubId(@Param("clubId") long clubId);

    void createHonorByList(@Param("list") List<WCClubHonorBean> clubHonorBean);
}
