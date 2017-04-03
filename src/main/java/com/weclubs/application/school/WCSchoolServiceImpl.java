package com.weclubs.application.school;

import com.weclubs.bean.WCSchoolBean;
import com.weclubs.mapper.WCSchoolMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 学校、专业的 service 实现类
 *
 * Created by fangzanpan on 2017/3/23.
 */
@Service("schoolService")
public class WCSchoolServiceImpl implements WCISchoolService {

    private Logger log = Logger.getLogger(WCSchoolServiceImpl.class);

    @Autowired
    private WCSchoolMapper mSchoolMapper;

    public void createSchool(String name) {

        if (StringUtils.isEmpty(name)) {
            log.error("createSchool：学校名字不能为空");
            return;
        }

        mSchoolMapper.createSchool(name);
    }

    public void createCollege(String name, long schoolId) {

        if (StringUtils.isEmpty(name)) {
            log.error("createCollege：院系名称不能为空");
            return;
        }

        if (mSchoolMapper.getSchoolById(schoolId) == null) {
            log.error("createCollege：找不到学校名称");
            return;
        }

        mSchoolMapper.createCollege(name, schoolId);
    }

    public void updateSchool(WCSchoolBean schoolBean) {

        if (schoolBean == null) {
            log.error("updateSchool：学校不能为空");
            return;
        }

        if (schoolBean.getParentId() != 0) {
            log.error("updateSchool：parentId一定要等于0");
            return;
        }

        mSchoolMapper.updateSchool(schoolBean);
    }

    public void updateCollege(WCSchoolBean collegeBean) {

        if (collegeBean == null) {
            log.error("updateCollege：院系实体不能为空");
            return;
        }

        if (collegeBean.getParentId() <= 0) {
            log.error("updateCollege：院系的parentId不能小于等于0");
            return;
        }

        mSchoolMapper.updateCollege(collegeBean);
    }

    public WCSchoolBean getSchoolById(long schoolId) {

        if (schoolId <= 0) {
            log.error("getSchoolById：schoolId不能小于等于0");
            return null;
        }


        return mSchoolMapper.getSchoolById(schoolId);
    }

    public WCSchoolBean getCollegeById(long collegeId) {

        if (collegeId <= 0) {
            log.error("getCollegeById：CollegeId不能小于等于0");
            return null;
        }

        return mSchoolMapper.getCollegeById(collegeId);
    }

    public List<WCSchoolBean> getSchools() {
        return mSchoolMapper.getSchools();
    }

    public List<WCSchoolBean> getCollegesBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getCollegesBySchoolId：学校id不能小于等于0");
            return null;
        }

        WCSchoolBean schoolBean = mSchoolMapper.getSchoolById(schoolId);
        if (schoolBean == null) {
            log.error("getCollegesBySchoolId：找不到schoolId = " + schoolId + " 的学校");
            return null;
        }

        if (schoolBean.getParentId() > 0) {
            log.error("getCollegesBySchoolId：输入的 schoolId " + schoolId +  " 找到的是院系而非学校");
            return null;
        }

        return mSchoolMapper.getCollegesBySchoolId(schoolId);
    }
}
