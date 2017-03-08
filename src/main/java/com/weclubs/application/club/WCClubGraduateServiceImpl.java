package com.weclubs.application.club;

import com.weclubs.bean.WCClubGraduateBean;
import com.weclubs.mapper.WCClubGraduateMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 社团届数服务的实现类
 *
 * Created by fangzanpan on 2017/3/8.
 */
public class WCClubGraduateServiceImpl implements WCIClubGraduateService {

    private Logger log = Logger.getLogger(WCClubGraduateServiceImpl.class);

    @Autowired
    private WCClubGraduateMapper mClubGraduateMapper;

    public void createClubGraduate(WCClubGraduateBean clubGraduateBean) {

        if (clubGraduateBean == null) {
            log.error("createClubGraduate：创建社团届数失败，clubGraduate对象不能为空。");
            return;
        }

        if (clubGraduateBean.getId() <= 0) {
            log.error("createClubGraduate：创建社团届数失败，clubGraduate.clubId不能小于等于0。");
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
}
