package com.weclubs.application.dynamic;

import com.weclubs.application.meeting.WCIClubMeetingService;
import com.weclubs.application.mission.WCIClubMissionService;
import com.weclubs.application.notification.WCINotificationService;
import com.weclubs.bean.WCClubMissionBean;
import com.weclubs.bean.WCStudentMissionRelationBean;
import com.weclubs.mapper.WCDynamicMapper;
import com.weclubs.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 动态的 service 实现类
 *
 * Created by fangzanpan on 2017/3/27.
 */
@Service(value = "dynamicService")
public class WCDynamicServiceImpl implements WCIDynamicService {

    private Logger log = Logger.getLogger(WCDynamicServiceImpl.class);

    private WCIClubMissionService mMissionService;
    private WCIClubMeetingService mMeetingService;
    private WCINotificationService mNotifyService;

    private WCDynamicMapper mDynamicMapper;

    @Autowired
    public WCDynamicServiceImpl(WCIClubMissionService mMissionService, WCIClubMeetingService mMeetingService,
                                WCINotificationService mNotifyService, WCDynamicMapper dynamicMapper) {
        this.mMissionService = mMissionService;
        this.mMeetingService = mMeetingService;
        this.mNotifyService = mNotifyService;
        this.mDynamicMapper = dynamicMapper;
    }

    public WCStudentMissionRelationBean getDynamicStudentRelationByDynamicId(long studentId, long dynamicId, String dynamicType) {

        log.info("getDynamicStudentRelationByDynamicId:[studentId = " + studentId + "][dynamicId = " + dynamicId
                + "][dynamicType = " + dynamicType + "]");

        WCStudentMissionRelationBean result = null;

        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            result = mDynamicMapper.getDynamicStudentRelation(studentId, dynamicId, 0);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            result = mDynamicMapper.getDynamicStudentRelation(studentId, dynamicId, 1);
        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            result = mDynamicMapper.getDynamicStudentRelation(studentId, dynamicId, 2);
        }

        return result;
    }

    public WCClubMissionBean getDynamicDetail(long dynamicId, String dynamicType) {

        WCClubMissionBean dynamicBean = null;
        if (Constants.TODO_NOTIFY.equals(dynamicType)) {
            dynamicBean = mNotifyService.getNotificationDetailById(dynamicId);
        } else if (Constants.TODO_MISSION.equals(dynamicType)) {
            dynamicBean = mMissionService.getMissionDetailById(dynamicId);
        } else if (Constants.TODO_MEETING.equals(dynamicType)) {
            dynamicBean = mMeetingService.getMeetingDetailById(dynamicId);
        }

        return dynamicBean;
    }
}
