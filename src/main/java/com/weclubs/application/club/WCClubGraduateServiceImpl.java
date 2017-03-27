package com.weclubs.application.club;

import com.weclubs.bean.WCClubGraduateBean;
import com.weclubs.bean.WCStudentClubGraduateRelationBean;
import com.weclubs.mapper.WCClubGraduateMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 社团届数服务的实现类
 *
 * Created by fangzanpan on 2017/3/8.
 */
@Service(value = "clubGraduateService")
public class WCClubGraduateServiceImpl implements WCIClubGraduateService {

    private Logger log = Logger.getLogger(WCClubGraduateServiceImpl.class);

    @Autowired
    private WCClubGraduateMapper mClubGraduateMapper;

    public void createClubGraduate(WCClubGraduateBean clubGraduateBean) {

        if (clubGraduateBean == null) {
            log.error("createClubGraduate：创建社团届数失败，clubGraduate对象不能为空。");
            return;
        }

        if (clubGraduateBean.getClubGraduateId() <= 0) {
            log.error("createClubGraduate：创建社团届数失败，clubGraduate.clubGraduateId不能小于等于0。");
            return;
        }

        mClubGraduateMapper.createClubGraduate(clubGraduateBean);
    }

    public void updateClubGraduate(WCClubGraduateBean clubGraduateBean) {

        if (clubGraduateBean == null) {
            log.error("updateClubGraduate：更新社团届数失败，clubGraduate对象不能为空。");
            return;
        }

        mClubGraduateMapper.updateClubGraduate(clubGraduateBean);
    }

    public void deleteClubGraduateById(long clubGraduateId) {

    }

    public void setClubGraduateSelected(long clubGraduateId) {

        WCClubGraduateBean willSelect = mClubGraduateMapper.getClubGraduateByClubGraduateId(clubGraduateId);

        if (willSelect == null) {
            log.error("setClubGraduateSelected：根据 clubGraduateId 查找不到对象。");
            return;
        }

        WCClubGraduateBean oldSelect = mClubGraduateMapper.getCurrentClubGraduateByClubId(willSelect.getClubId());

        willSelect.setIsCurrent(0);
        oldSelect.setIsCurrent(1);

        mClubGraduateMapper.updateClubGraduate(willSelect);
        mClubGraduateMapper.updateClubGraduate(oldSelect);
    }

    public WCClubGraduateBean getCurrentClubGraduate(long clubId) {

        if (clubId <= 0) {
            log.error("getCurrentClubGraduate：clubId 不能小于等于0");
            return null;
        }

        return mClubGraduateMapper.getCurrentClubGraduateByClubId(clubId);
    }

    public WCStudentClubGraduateRelationBean getStudentClubGraduationRelationByGraduateId(long studentId, long graduateId) {

        if (graduateId <= 0) {
            log.error("getStudentClubGraduationRelationByGraduateId：graduateId不能小于等于0");
            return null;
        }

        return mClubGraduateMapper.getStudentGraduateRelation(studentId, graduateId);
    }
}
