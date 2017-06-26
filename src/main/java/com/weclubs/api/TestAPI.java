package com.weclubs.api;

import com.weclubs.application.club.WCIClubService;
import com.weclubs.application.qiniu.WCIQiNiuService;
import com.weclubs.application.rongcloud.WCIRongCloudService;
import com.weclubs.application.user.WCIUserService;
import com.weclubs.bean.WCClubBean;
import com.weclubs.bean.WCClubStudentBean;
import com.weclubs.bean.WCStudentBean;
import com.weclubs.mapper.WCClubMapper;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCCommonUtil;
import com.weclubs.util.WCHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by fangzanpan on 2017/6/21.
 */
@RestController
@RequestMapping(value = "/test")
public class TestAPI {

    @Autowired
    private WCIClubService mClubService;
    @Autowired
    private WCIRongCloudService mRongCloudService;
    @Autowired
    private WCClubMapper mClubMapper;
    @Autowired
    private WCIUserService mUserService;
    @Autowired
    private WCIQiNiuService mQiNiuService;

    @RequestMapping(value = "/create_group_chat", method = RequestMethod.POST)
    public WCResultData createGroupChat() {
        WCHttpStatus check = WCHttpStatus.SUCCESS;

        List<WCClubBean> clubs = mClubService.getClubsBySchoolId(1);

        for (WCClubBean club : clubs) {
            long clubId = club.getClubId();
            WCClubBean clubBean = mClubService.getClubInfoById(clubId);
            List<WCClubStudentBean> students = mClubMapper.getCurrentGraduateStudents(clubId);

            long[] studentIds = new long[students.size()];
            for (int i = 0; i < students.size(); i++) {
                studentIds[i] = students.get(i).getStudentId();
            }

            check = mRongCloudService.createGroupChat(clubId, clubBean.getName(), studentIds);
        }

        try {
            Image image = ImageIO.read(new URL("http://on633pcgq.bkt.clouddn.com/activity/chengji/img/chengji.png"));
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            int fontSize =  width/20;

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
//            g.setFont(new Font(fontName, fontStyle, fontSize));
//            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));

            String pressText = "广东省";
            int width_1 = fontSize * pressText.length();

            g.drawString(new String(pressText.getBytes()), width-20-width_1, height-10);
            g.dispose();

            mQiNiuService.uploadFile(WCCommonUtil.getImageStream(bufferedImage));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return WCResultData.getHttpStatusData(check, new HashMap<String, Object>());
    }

    @RequestMapping(value = "/get_im_user_token", method = RequestMethod.POST)
    public WCResultData getImUserToken() {

        long studentId = 1;

        WCStudentBean studentBean = mUserService.getUserInfoById(studentId);

        String token = mRongCloudService.getUserToken(studentId, studentBean.getRealName(), studentBean.getAvatarUrl());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        return WCResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/get_group_user_list", method = RequestMethod.POST)
    public WCResultData getGroupUserList() {

        long clubId = 31;

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user", mRongCloudService.getStudentsByGroupId(clubId));
        return WCResultData.getSuccessData(result);
    }
}
