package com.weclubs.application.club;

import com.weclubs.bean.WCClubGraduateBean;
import com.weclubs.bean.WCStudentClubGraduateRelationBean;

/**
 * 社团届数服务类的接口层
 *
 * Created by fangzanpan on 2017/3/8.
 */
public interface WCIClubGraduateService {

    void createClubGraduate(WCClubGraduateBean clubGraduateBean);

    void updateClubGraduate(WCClubGraduateBean clubGraduateBean);

    void deleteClubGraduateById(long clubGraduateId);

    void setClubGraduateSelected(long clubGraduateId);

    WCClubGraduateBean getCurrentClubGraduate(long clubId);

    WCStudentClubGraduateRelationBean getStudentClubGraduationRelationByGraduateId(long studentId, long graduateId);

    void createStuCluGraduateRelation(WCStudentClubGraduateRelationBean relationBean);
}
