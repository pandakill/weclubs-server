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

    public void createMajor(String name, long schoolId) {

        if (StringUtils.isEmpty(name)) {
            log.error("createMajor：院系名称不能为空");
            return;
        }

        if (mSchoolMapper.getSchoolById(schoolId) == null) {
            log.error("createMajor：找不到学校名称");
            return;
        }

        mSchoolMapper.createMajor(name, schoolId);
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

    public void updateMajor(WCSchoolBean majorBean) {

        if (majorBean == null) {
            log.error("updateMajor：院系实体不能为空");
            return;
        }

        if (majorBean.getParentId() <= 0) {
            log.error("updateMajor：院系的parentId不能小于等于0");
            return;
        }

        mSchoolMapper.updateMajor(majorBean);
    }

    public WCSchoolBean getSchoolById(long schoolId) {

        if (schoolId <= 0) {
            log.error("getSchoolById：schoolId不能小于等于0");
            return null;
        }


        return mSchoolMapper.getSchoolById(schoolId);
    }

    public WCSchoolBean getMajorById(long majorId) {

        if (majorId <= 0) {
            log.error("getMajorById：majorId不能小于等于0");
            return null;
        }

        return mSchoolMapper.getMajorById(majorId);
    }

    public List<WCSchoolBean> getSchools() {
        return mSchoolMapper.getSchools();
    }

    public List<WCSchoolBean> getMajorsBySchoolId(long schoolId) {

        if (schoolId <= 0) {
            log.error("getMajorsBySchoolId：学校id不能小于等于0");
            return null;
        }

        WCSchoolBean schoolBean = mSchoolMapper.getSchoolById(schoolId);
        if (schoolBean == null) {
            log.error("getMajorsBySchoolId：找不到schoolId = " + schoolId + " 的学校");
            return null;
        }

        if (schoolBean.getParentId() <= 0) {
            log.error("getMajorsBySchoolId：输入的 schoolId " + schoolId +  " 找到的是学校而非院系");
            return null;
        }

        return mSchoolMapper.getMajorsBySchoolId(schoolId);
    }
}
