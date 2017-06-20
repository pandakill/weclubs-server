package com.weclubs.util;

/**
 * 保存相关的常量
 *
 * Created by fangzanpan on 2017/3/25.
 */
public class Constants {

    public final static int ONE_PAGE_SIZE = 10;

    public final static String TODO_NOTIFY = "notify";
    public final static String TODO_MEETING = "meeting";
    public final static String TODO_MISSION = "mission";

    // 极光推送的 APP_KEY 和 SECRET_KEY
    public final static String JIGUANG_APP_KEY = "3821df98e100598741efa38d";
    public final static String JIGUANG_SECRET_KEY = "a2f3cd6267bdaf05e8289d34";

    // 融云 IM 的 APP_KEY 和 SECRET_KEY
    public final static String RONGCLOUD_APP_KEY = "sfci50a7s1xji"; // 测试服务器
    public final static String RONGCLOUD_SECRET_KEY = "OSe4Pcbk0y"; // 测试服务器
    private final static String RONGCLOUD_RELEASE_APP_KEY = "sfci50a7s1xji";
    private final static String RONGCLOUD_RELEASE_SECRET_KEY = "OSe4Pcbk0y";

    /* 跳转协议的场景 ID 定义 */
    public final static int SCENE_PERSON_DYNAMIC = 101; // 参与者的动态详情页面
    public final static int SCENE_CLUB_DETAIL = 102;    // 社团的详情页卖弄
    public final static int SCENE_STUDENT_DETAIL = 103; // 学生的个人详情页面
    public final static int SCENE_MANAGE_DYNAMIC = 201; // 管理者的动态详情页面
}
