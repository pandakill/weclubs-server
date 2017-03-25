package com.weclubs.application.club;

import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubHonorBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.mapper.WCClubHonorMapper;
import com.weclubs.mapper.WCClubMapper;
import com.weclubs.mapper.WCDynamicMapper;
import com.weclubs.mapper.WCStudentMapper;
import com.weclubs.model.WCMyClubModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 社团服务层实现类
 *
 * Created by fangzanpan on 2017/3/8.
 */
@Service(value = "clubService")
public class WCClubServiceImpl implements WCIClubService {

    public final static int SORT_BY_REAL_NAME = 0;
    public final static int SORT_BY_GRADUATE = 1;
    public final static int SORT_BY_DEPARTMENT = 2;
    public final static int SORT_BY_JOB = 3;

    private Logger log = Logger.getLogger(WCClubServiceImpl.class);

    @Autowired
    private WCClubMapper mClubMapper;
    @Autowired
    private WCClubHonorMapper mClubHonorMapper;
    @Autowired
    private WCStudentMapper mStudentMapper;
    @Autowired
    private WCDynamicMapper mDynamicMapper;

    public WCClubBean getClubInfoById(long clubId) {

        if (clubId <= 0) {
            log.error("getClubInfoById：查找社团失败，clubId");
            return null;
        }

        return mClubMapper.getClubById(clubId);
    }

    public void createClub(WCClubBean clubBean) {

        if (clubBean == null) {
            log.error("createClub：创建 club 对象失败，club 不能为 null。");
            return;
        }

        mClubMapper.createClub(clubBean);
    }

    public void updateClub(WCClubBean clubBean) {

        if (clubBean == null) {
            log.error("updateClub：更新失败，club 对象不能为空");
            return;
        }

        if (clubBean.getClubId() <= 0) {
            log.error("updateClub：更新失败，club.id 不能为小于等于 0。");
        }

        mClubMapper.updateClub(clubBean);
    }

    public List<WCClubBean> getClubsBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getClubsBySchoolId：schoolId 不能小于等于 0。");
            return null;
        }

        return mClubMapper.getClubsBySchoolId(schoolId);
    }

    public List<WCClubBean> getClubsByStudentId(long studentId) {

        if (studentId <= 0) {
            log.error("getClubsByStudentId：studentId 不能小于等于 0。");
            return null;
        }

        return mClubMapper.getClubsByStudentId(studentId);
    }

    @Cacheable(value = "getClubHonorByClubId")
    public List<WCClubHonorBean> getClubHonorByClubId(long clubId) {

        if (clubId <= 0) {
            log.error("getClubHonorByClubId：clubId 不能小于等于 0。");
            return null;
        }

        return mClubHonorMapper.getClubHonorsByClubId(clubId);
    }

    public List<WCClubStudentBean> getStudentsByCurrentGraduate(long clubId, int sortType) {

        if (clubId <= 0) {
            log.error("getStudentsByCurrentGraduate：clubId 不能小于等于 0。");
            return null;
        }

        if (sortType == SORT_BY_REAL_NAME) {    //根据学生真实姓名首字母排序
            return mClubMapper.getCurrentGraduateStudentsBySortStuName(clubId);
        }

        return mClubMapper.getCurrentGraduateStudents(clubId);
    }

    public List<WCMyClubModel> getMyClubs(long studentId) {

        if (studentId <= 0) {
            log.error("getMyClubs：studentId不能小于等于0");
            return null;
        }

        List<WCMyClubModel> result = new ArrayList<WCMyClubModel>();

        List<WCClubBean> myClubs = mClubMapper.getClubsByStudentId(studentId);
        for (WCClubBean myClub : myClubs) {
            WCMyClubModel myClubModel = new WCMyClubModel(myClub);
            myClubModel.setMemberCount(mClubMapper.getClubMemberCount(myClub.getClubId()));
            myClubModel.setActivityCount(mClubMapper.getClubActivityCount(myClub.getClubId()));
            myClubModel.setTodoCount(mDynamicMapper.getStudentTodoCountOneClub(studentId, myClub.getClubId()));
            result.add(myClubModel);
        }

        return result;
    }
}
