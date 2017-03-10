package com.weclubs.mapper;

import com.weclubs.bean.WCClubJobBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_club_department 部门表的映射接口
 *
 * Created by fangzanpan on 2017/3/10.
 */
public interface WCClubJobMapper {

    void createClubJob(WCClubJobBean clubJobBean);

    void updateClubJob(WCClubJobBean clubJobBean);

    void deleteClubJob(@Param("clubJobId") long clubJobId);

    WCClubJobBean getClubJobById(@Param("clubJobId") long clubJobId);

    List<WCClubJobBean> getClubJobBySuggest();

    WCClubJobBean getClubJobByJobName(@Param("jobName") String jobName);

    void setCurrentClubJobs(@Param("clubId") long clubId, @Param("jobs") String jobs);

    String getCurrentClubJobs(@Param("clubId") long clubId);
}
