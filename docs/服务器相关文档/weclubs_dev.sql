-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:8889
-- Generation Time: Apr 21, 2017 at 10:36 AM
-- Server version: 5.5.42
-- PHP Version: 5.5.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `weclubs_dev`
--
CREATE DATABASE IF NOT EXISTS `weclubs_dev` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `weclubs_dev`;

-- --------------------------------------------------------

--
-- Table structure for table `t_club`
--

DROP TABLE IF EXISTS `t_club`;
CREATE TABLE IF NOT EXISTS `t_club` (
  `club_id` bigint(224) NOT NULL COMMENT '自增id',
  `school_id` bigint(224) NOT NULL COMMENT '学校id，对应的是t_chool.id',
  `name` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '社团名称',
  `introduction` text COLLATE utf8_bin COMMENT '社团简介',
  `slogan` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '社团口号',
  `avatar_url` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '社团的头像logo地址',
  `is_auth` int(1) NOT NULL DEFAULT '0' COMMENT '是否认证，默认为0；0未认证，1已认证',
  `level` int(2) NOT NULL DEFAULT '2' COMMENT '社团等级，0校级组织，1院系组织，2兴趣社团，3班级组织，4私下组织',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '社团的状态，0待审核，1审核通过，2审核失败，3冻结状态',
  `check_count` int(224) NOT NULL DEFAULT '0' COMMENT '社团的查看次数，新建的社团为0开始'
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `t_club`
--

INSERT INTO `t_club` (`club_id`, `school_id`, `name`, `introduction`, `slogan`, `avatar_url`, `is_auth`, `level`, `status`, `check_count`) VALUES
(1, 1, '社团联合会', '社团联合会（Students’ Association Union）', '社同联心', 'http://p2.wmpic.me/article/2015/03/16/1426483393_DXGAJIiR.jpeg', 1, 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_activity`
--

DROP TABLE IF EXISTS `t_club_activity`;
CREATE TABLE IF NOT EXISTS `t_club_activity` (
  `club_activity_id` bigint(224) NOT NULL COMMENT '自增id',
  `club_id` bigint(224) NOT NULL COMMENT '社团id，t_club.id',
  `name` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '活动名称',
  `attribution` longtext COLLATE utf8_bin COMMENT '活动描述',
  `address` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '活动举办地点',
  `poster_url` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '海报地址',
  `apply_deadline` bigint(13) NOT NULL COMMENT '报名截止时间，时间戳的字符串',
  `hold_date` bigint(13) NOT NULL COMMENT '举办时间，时间戳的字符串',
  `hold_deadline` bigint(13) NOT NULL COMMENT '举办截止时间，时间戳的字符串',
  `allow_apply` int(1) NOT NULL DEFAULT '0' COMMENT '是否可以报名，0不可以报名，1可以报名；默认为1',
  `allow_pre_apply` int(1) NOT NULL DEFAULT '0' COMMENT '是否支持预报名，0不支持，1支持；默认为支持',
  `record_comment` text COLLATE utf8_bin COMMENT '活动记录描述',
  `record_pic_url` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '活动记录图片地址，保存格式为json数组形式',
  `check_count` int(1) NOT NULL DEFAULT '0' COMMENT '该活动浏览人数，新建社团为0',
  `sponsor_id` bigint(224) NOT NULL COMMENT '发起人id，对应t_student.id',
  `activity_type` int(1) NOT NULL DEFAULT '1' COMMENT '活动的类型：1：校园级别，2社团级别（内部活动）',
  `need_sign` int(1) NOT NULL DEFAULT '0' COMMENT '是否需要签到，1：需要；0：不需要',
  `create_date` bigint(13) NOT NULL COMMENT '创建时间，时间戳的字符串',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除；默认为0'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='活动基本信息表';

--
-- Dumping data for table `t_club_activity`
--

INSERT INTO `t_club_activity` (`club_activity_id`, `club_id`, `name`, `attribution`, `address`, `poster_url`, `apply_deadline`, `hold_date`, `hold_deadline`, `allow_apply`, `allow_pre_apply`, `record_comment`, `record_pic_url`, `check_count`, `sponsor_id`, `activity_type`, `need_sign`, `create_date`, `is_del`) VALUES
(1, 1, '第十五届科技学术节 | 第十二届“文豆杯”高校联合DI科技创新大赛', NULL, '广大华软', 'http://mmbiz.qpic.cn/mmbiz_jpg/LVPLOAFfwwABzevcKQm7P8pR9mh5g4CqfJjPXyMfnj7wNGsPicyDxDfhQwicnuH5IOkMIJtAAIhiam0iccZK11QyicA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 1, 1, NULL, NULL, 0, 7, 1, 0, 1490796629608, 0),
(2, 1, 'i志愿 爱生活 || 2017年雷锋服务月分享会', NULL, '广大华软', 'https://mmbiz.qpic.cn/mmbiz_jpg/GNR9FMpbFX0xk6ibSBk1kbvFU54q2MyhoLFGXgWfgYicyiaSoTrEu3DvSQX97KlvfcPAHaHVpFPiagvK4gWaciaxAibQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1492589883955, 1490999629608, 1492589883955, 0, 0, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(3, 31, 'i志愿 爱生活 || 2017年雷锋服务月分享会', NULL, '广大华软', 'https://mmbiz.qpic.cn/mmbiz_jpg/GNR9FMpbFX0xk6ibSBk1kbvFU54q2MyhoLFGXgWfgYicyiaSoTrEu3DvSQX97KlvfcPAHaHVpFPiagvK4gWaciaxAibQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 0, 0, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(4, 31, '设计大赛 || html5设计大赛开始啦！', '主题\r\n国内外著名景点&未来科技\r\n（二选一）\r\n \r\nhtml5设计大赛细则\r\n\r\n参赛要求和作品要求\r\n1)参赛可以是个人或者团队参加（人数限制：1-4人）。\r\n2)参赛团队所提供的作品必须由本人或者本团队亲自制作创作，并且符合主题，不得抄袭；要形式新颖，构思独特，突出创新意识和技术含量。\r\n3)作品内容必须健康，安全，合法且积极向上，无暴力，无色情。\r\n4)作品在设计上充满作者想表达的思想内容，并且有一个或者多个创意点，给人简约、清爽的感觉，符合时代潮流。\r\n5)参赛作品请参赛团队保留源文件。\r\n6)初赛的作品可以经过修改在决赛时使用。\r\n\r\n比赛流程\r\n4月18日-4月20日：网上报名\r\n4月19日-4月20日：现场报名\r\n            4月22日-4月27日：制作初赛作品，并于4月27日中午12点之前上交作品。\r\n            4月28日-5月1日：主办方将于4月28日至5月1日的时间内公布入围名单，届时请各位选手留意消息通知。\r\n           5月2日-5月8日：制作决赛作品（初赛的作品可经过修改后在决赛使用），并于5月8日中午12点之前提交作品。\r\n           5月9日-5月10日：进行微信投票环节，票数最高者可获得最佳人气奖。\r\n           5月11日：决赛，届时有老师在现场与选手进行答辩评比环节（选手要从构思设计、创意点上去阐述作品）；决赛完毕后现场统计出分数最高的前三名并进行颁奖典礼。\r\n【上交作品方式：\r\nFTP：172.16.112.87\r\n用户名：ca\r\n密码：ca\r\n（若FTP故障请将作品上传给QQ：495328194）\r\n\r\n评分细则\r\n一、老师评分（所评分数占总分数的90%）：\r\n1、内容（30分）\r\n①10分   是否有明确的主题、内容。\r\n②10分   内容健康向上、有时代感、信息的实时性。\r\n③10分   创意水平的高低。\r\n\r\n2、常规技术（60分）\r\n①15分   整体视觉效果：整个网站在设计上是否统一和谐。\r\n②10分   美工设计：页面的美工设计含量。\r\n③15分   页面布局：页面的布局是否合理、美观。\r\n④5分    页面易用型：用户使用时是否方便。\r\n⑤5分    内容充实：网站内容对用户的吸引力。\r\n⑥10分   技术含量：网站整体的技术评价。\r\n（PS：一经发现抄袭，一律取消比赛资格）\r\n\r\n二、微信投票（所得分数占总分数的10%）：\r\n大众可以在微信公众进行投票，作品得到票数数量最多的得10分，票数第二的得9分，第三的得8分，并以此类推。', '广大华软', 'https://mmbiz.qpic.cn/mmbiz_jpg/GNR9FMpbFX0jhbfuOY845BPnYp9nL1UzLFIX1KRWMYn5ccUK5Da25QR13rhIzFiaHPsk4ibCBYmBVoVTuic3hECKA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 1, 1, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(5, 1, '决赛 || OMG实习生计划决赛', NULL, '广大华软', 'https://mmbiz.qpic.cn/mmbiz_jpg/GNR9FMpbFX3Mwf6ZLIueImGm3AXonWiaSgLV85D31cA8QPWyUFIkicpLiaknVCuDoroEDcEYcfnpXFkicLZ8qnuITw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 0, 0, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(6, 1, '校园借贷 || 拒绝黄赌毒、校园贷，从我做起', '为了进一步在学生中贯彻落实培育和践行社会主义核心价值观，引导同学们对规划个人成长和参与学校建设中所负责任的深入思考，所以这次主题为“感恩·责任·担当”的教育活动，西红柿决定在线上开展三个活动，内容分别为：拒绝黄赌毒、校园贷；关心空巢老人、留守儿童；理解与尊重，改善医患关系。\r\n而这一期，便从“拒绝黄赌毒、校园贷，从我做起”开始。', '广大华软', 'https://mmbiz.qpic.cn/mmbiz_jpg/Hz75afPdRkA8ofVRll2xhDEEaibkiaCkeibQT2DficzpuFv8uibXAFHHEN1CVVoVDqiaROzmib8VGkTh8qibGxPcljaZaA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 0, 0, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(7, 31, '宿舍文化建设 || 软件工程系第二届第二学期星级宿舍评选活动', '赶紧行动起来吧，把宿舍变成你的第二个家~与此同时本学期的星级宿舍评比活动即将来临，如果你想住在“五星级”宿舍中、如果你想向大家展示宿舍的整洁舒适、如果你想…\r\n无需再想赶紧来报名吧！\r\n报名方法：向本班生活委员报上宿舍号即可\r\n报名截止时间：4月15号前\r\n检查时间：每月中旬\r\n同学们如果对宿舍卫生有意见或者建议，随时可以私聊发给我们哦。\r\n以下小编给同学们的小建议：\r\n1、 整理好自己的宿舍以及物品，清爽的生活环境、干净整洁的桌面可以带来好心情外更能提高学shang习fen效率呢\r\n2、 及时清理和整洁宿舍的公共环境，养成好的生活习性\r\n3、 注意用电安全啦', '广大华软', 'https://mmbiz.qpic.cn/mmbiz_jpg/GNR9FMpbFX1WQs4wEZpM49KxwQxkJkib3mSYwiaVSFuZ5SuCh1j5shoGje1zGqrfwWibHvKYYHhtgJ2GPNtJnTiaUw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 1, 1, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(8, 31, '第十五届科技学术节 || 软件工程系学生代表赴中大南方交流', '为进一步深化我系与其他高校之间的交流合作，加强第十四届“讯一佳•青牛杯”软件设计大赛的校外沟通与宣传工作。4月6日，软件工程系学生干部代表赴中山大学南方学院进行经验交流。参加本次交流的有软件工程系学生会主席团成员林铭鑫、招嘉振、彭靖怡，学生会信息技术部成员以及第十四届“讯一佳•青牛杯”软件设计大赛相关负责人。此次的走访交流，我系学生干部得到了中山大学南方学院有关方面的热情接待。', '中大南方', 'https://mmbiz.qpic.cn/mmbiz_png/GNR9FMpbFX1BupCOku9oibpP0GWuf7xibhdia5Uqo0YNzruGCBatlGMq7kETr0AYI10wWhjkhbA46iczLVK1xG3icug/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1', 1490463686617, 1490999629608, 1490999999608, 0, 0, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(9, 31, '中国大学生软件服务外包大赛', '各位同学：\r\n 关于“2017中国大学生软件服务外包大赛”，大赛官网更新最新比赛通知，且相关截止日期即将到来。现在此通知提醒如下：\r\n1、报名自主命题的团队，请将《自主命题申请表》，《自主命题需求分析表》，《自主命题总汇表》尽快填好，与2017/4/7号前以电子版形式交由指定人处。\r\n2、报名自主命题需先提交相关表格待大赛组委审核通过后才能继续后续比赛。\r\n3、报名企业命题的团队请准备好所有相关资料于2017/4/7号前交由指定人处。\r\n4、资料收集人联系方式：邵茂仁\r\n电话：13726202816\r\nQQ：185466981\r\n微信：smrwoniu。\r\n软件工程系 系学习部\r\n2017/4/7\r\n', '广大华软', '', 1490463686617, 1490999629608, 1490999999608, 0, 0, NULL, NULL, 0, 1, 1, 0, 1490796629608, 0),
(10, 31, 'LOLO协会 | 内部技能培训和综合演练', '各位同学：\r\n 为了LOL协会能够壮大！我们决定，即日起每个周二定为我们LOL协会的内部技能培训和综合演练日，每周二早上八点至晚上十一点半，在XXX网咖会定期为我们LOL协会预留30个机位，大家可以凭会员证或者萌社二维码免费进入。', 'XXX网咖', 'http://pic81.nipic.com/file/20151029/22039241_220926719000_2.jpg', 1490463686617, 1490999629608, 1490999999608, 0, 0, NULL, NULL, 0, 1, 2, 0, 1490796629608, 0),
(11, 31, '汉语俱乐部 | 泰语书法培训', '为增强大家泰语的学习能力和泰语的书法书写技能，汉语俱乐部为此特地开班培训泰语书法。。欢迎大家踊跃报名参加', '华软图书馆4楼湖畔培训室', 'http://pic.qiantucdn.com/58pic/19/14/34/69T58PICMmx_1024.jpg', 0, 1492413252302, 1492589808673, 0, 0, NULL, NULL, 0, 1, 1, 0, 1492657210376, 0),
(17, 31, '内部素拓活动 | 无游戏周之--4月份春游活动', 'LOL俱乐部为增强俱乐部成员的凝聚力、放松大家的身心，为此不定期举办各类内部素拓活动。此次开展的是春游活动，初定于4月15日出发，集体到阳江闸坡游完。鼓励大家踊跃报名参加，当然，以自愿为主。', '东门广场集合（同意乘坐大巴前往阳江闸坡）', 'http://pic20.nipic.com/20120506/10009679_162510156000_2.jpg', 0, 1492413252302, 1492589808673, 0, 0, NULL, NULL, 0, 1, 2, 0, 1492659364582, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_authority`
--

DROP TABLE IF EXISTS `t_club_authority`;
CREATE TABLE IF NOT EXISTS `t_club_authority` (
  `club_authority_id` bigint(224) NOT NULL COMMENT '自增id',
  `name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '权限名字',
  `attribute` text COLLATE utf8_bin NOT NULL COMMENT '权限描述',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='社团权限表';

--
-- Dumping data for table `t_club_authority`
--

INSERT INTO `t_club_authority` (`club_authority_id`, `name`, `attribute`, `is_del`) VALUES
(1, '职位指定', '具备该资格，可以指定其他成员的职位，如设定某成为为干部资格', 0),
(2, '成员变更', '具备该资格，可以对社团成员进行管理，如新成员加入，老成员退出等', 0),
(3, '会议组织', '具备该资格，可以组织社团会议，通知参与成员，统计参与成员数据等', 0),
(4, '活动发起', '具备该资格，可以发起活动，包括社团活动和校园活动', 0),
(5, '任务指派', '具备该资格，可以向社团成员指派新的任务，并了解任务的完成情况', 0),
(6, '相册调整', '具备该资格，可以对社团的相册中的照片进行调整修改', 0),
(7, '资料变更', '具备该资格，可以对社团的信息进行编辑修改，如社团介绍，社团荣誉等', 0),
(8, '发起群聊', '具备该资格，可以选择社团中的部分成员，建立新的群聊', 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_department`
--

DROP TABLE IF EXISTS `t_club_department`;
CREATE TABLE IF NOT EXISTS `t_club_department` (
  `department_id` bigint(224) NOT NULL COMMENT '自增id',
  `department_name` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '部门名称',
  `is_suggest` int(1) NOT NULL DEFAULT '0' COMMENT '是否推荐的部门名称',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='社团部门表';

--
-- Dumping data for table `t_club_department`
--

INSERT INTO `t_club_department` (`department_id`, `department_name`, `is_suggest`, `is_del`) VALUES
(1, '主席团', 1, 0),
(2, '生活部', 1, 0),
(3, '书记处', 1, 0),
(4, '宣传部', 1, 0),
(5, '会员部', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_graduate`
--

DROP TABLE IF EXISTS `t_club_graduate`;
CREATE TABLE IF NOT EXISTS `t_club_graduate` (
  `club_graduate_id` bigint(224) NOT NULL COMMENT '自增id',
  `club_id` bigint(224) NOT NULL COMMENT '社团id，社团t_club.id',
  `graduate_count` int(50) NOT NULL COMMENT '社团届数，例如10、9',
  `graduate_name` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '社团届数名称，例如第十届团学',
  `departments` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '社团的部门，格式为t_club_department的id字符串，逗号隔开。例如：1,2,3',
  `jobs` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '社团的职位，格式为josn数据，职位和权限对等，例如：[{"2":"1,2,5"},{"3":"3,2,4"}]',
  `is_current` int(1) NOT NULL DEFAULT '0' COMMENT '是否为当前显示的届数，0否，1是；默认为0'
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='社团届数表';

--
-- Dumping data for table `t_club_graduate`
--

INSERT INTO `t_club_graduate` (`club_graduate_id`, `club_id`, `graduate_count`, `graduate_name`, `departments`, `jobs`, `is_current`) VALUES
(1, 1, 10, '第十届社团联合会', '1,2,5,4,3', '{"1":"1","2":"2","6":""}', 1),
(2, 1, 9, '第九届社团联合会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 0),
(3, 1, 8, '第八届社团联合会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 0),
(4, 2, 9, '第一届院团学', '2,3,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 0),
(5, 2, 10, '第十届院团学', '2,3,4', '[{5:"1,2,3,4,5,6"},{2:"2,3,4,5,6"},{3:"2,3,4,5,6"},{4:""}]', 1),
(6, 21, 1, '第一届心协', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 0),
(7, 22, 1, '第一届院学', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(8, 23, 1, '第一届自律会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(9, 24, 1, '第一届红十字会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(10, 25, 1, '第一届宿委会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(11, 27, 1, '第一届电子协会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(12, 28, 1, '第一届网管', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(13, 29, 1, '第一届旅游协会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(14, 30, 1, '第一届篮球协会', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(15, 32, 1, '16届软测1班', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(16, 33, 1, '19届软开1班', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(17, 31, 1, '第一届LOL联盟', '1,2,4', '{1:"1,2,3,4,5,6",2:"2,3,4,5,6",3:"2,3,4,5,6",4:""}', 1),
(22, 44, 0, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_honor`
--

DROP TABLE IF EXISTS `t_club_honor`;
CREATE TABLE IF NOT EXISTS `t_club_honor` (
  `honor_id` bigint(224) NOT NULL COMMENT '自增id',
  `club_id` bigint(224) NOT NULL COMMENT '社团id，对应t_club.id',
  `content` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '荣誉内容',
  `get_date` bigint(13) NOT NULL COMMENT '荣誉获得时间，精确到日',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='社团荣誉表';

--
-- Dumping data for table `t_club_honor`
--

INSERT INTO `t_club_honor` (`honor_id`, `club_id`, `content`, `get_date`, `is_del`) VALUES
(1, 1, '华软第十届明星社团', 14918367241541, 0),
(2, 1, '华软最值得加入社团', 14918367241541, 0),
(3, 1, '全国十大最浪社团', 1492059338907, 0),
(4, 1, '十大最鸡巴社团', 1492059338907, 0),
(7, 31, '十大帅哥最多社团', 1492414323684, 0),
(8, 31, '十大最值得加入社团', 1492414323684, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_job`
--

DROP TABLE IF EXISTS `t_club_job`;
CREATE TABLE IF NOT EXISTS `t_club_job` (
  `job_id` bigint(224) NOT NULL COMMENT '自增id',
  `job_name` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '职位名称',
  `is_sugguest` int(1) NOT NULL DEFAULT '0' COMMENT '是否推荐使用，0不推荐，1推荐',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='社团职位表';

--
-- Dumping data for table `t_club_job`
--

INSERT INTO `t_club_job` (`job_id`, `job_name`, `is_sugguest`, `is_del`) VALUES
(1, '主席', 1, 0),
(2, '部长', 1, 0),
(3, '副部长', 1, 0),
(4, '干事', 1, 0),
(5, '书记', 1, 0),
(6, '会员', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_club_mission`
--

DROP TABLE IF EXISTS `t_club_mission`;
CREATE TABLE IF NOT EXISTS `t_club_mission` (
  `mission_id` bigint(224) NOT NULL COMMENT '自增id',
  `club_id` bigint(224) NOT NULL COMMENT '社团id，t_club.id',
  `graduate_id` bigint(224) NOT NULL COMMENT '对应的社团届数',
  `attribution` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '任务/通知描述',
  `type` int(1) NOT NULL COMMENT '任务类型，0通知，1任务，2会议',
  `sign_type` int(1) NOT NULL DEFAULT '0' COMMENT '签到类型，0：不需要签到；1：二维码签到',
  `parent_id` bigint(224) NOT NULL DEFAULT '0' COMMENT '父级任务id',
  `address` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '任务举办地点',
  `deadline` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '任务截止时间',
  `sponsor_id` bigint(224) NOT NULL COMMENT '任务发起人id，关联student_id',
  `create_date` bigint(13) NOT NULL COMMENT '发起时间，时间戳的字符串',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通知\\任务基本信息表';

--
-- Dumping data for table `t_club_mission`
--

INSERT INTO `t_club_mission` (`mission_id`, `club_id`, `graduate_id`, `attribution`, `type`, `sign_type`, `parent_id`, `address`, `deadline`, `sponsor_id`, `create_date`, `is_del`) VALUES
(1, 1, 1, '这是第一个活动，上山打老虎', 1, 1, 0, '广大华软--东门广场', '1491836724154', 1, 1491836724154, 0),
(2, 1, 1, '女生节摆摊活动', 1, 1, 0, '广大华软--喷泉广场', '1491836724154', 1, 1491836724154, 0),
(3, 1, 1, '今天天气转凉，请注意预防发烧~少穿衣！主席永远爱你们(づ￣ 3￣)づ', 0, 1, 0, '', '1491836724154', 2, 1491836724154, 0),
(4, 1, 1, '今晚八点半在教学楼 E202 召开第一次动员大会！请各位务必准时到场！！', 2, 1, 0, '广大华软 -- D101 教室', '1491836724154', 2, 1491836724154, 0),
(5, 2, 1, '由于连夜大暴雨，特此通知今天需要继续上课，各位同学请准时上课，安排好时间，上课路上注意安全', 0, 1, 0, '', '1491836724154', 1, 1491836724154, 0),
(6, 1, 1, '今晚全体成员和各社团主席在 D101 教室召开新学期社团招新动员大会。', 2, 1, 0, '广大华软 -- D101 教室', '1491836724154', 1, 1491836724154, 0),
(7, 1, 1, '先要买酒壮胆', 1, 1, 1, '广大华软--东门广场', '1491836724154', 1, 1491836724154, 0),
(8, 1, 1, '第二步需要买好砍猪刀', 1, 1, 1, '广大华软--东门广场', '1491836724154', 1, 1491836724154, 0),
(9, 1, 1, '买菜刀第一步：到菜市场寻找合适的卖家', 1, 1, 8, '广大华软--东门广场', '1491836724154', 1, 1491836724154, 0),
(10, 1, 1, '买菜刀第二步：买好菜刀之后需要进行打磨', 1, 1, 8, '广大华软--东门广场', '1491836724154', 1, 1491836724154, 0),
(11, 31, 17, '为了进一步贯彻落实党的十七大精神和团中央陆昊书记电视电话会议精神，切实做好下阶段学校团队工作，经研究，决定召开椒江区中小学团队工作会议', 0, 1, 0, '广大华软', '1491836724154', 1, 1491836724154, 0),
(17, 31, 17, '测试添加一个通知，发给LOL协会！！请大家注意查收！', 0, 1, 0, NULL, '0', 1, 1492502049733, 0),
(18, 31, 17, '今天天气炎热！请大家注意休息！下午的活动可以晚点来，不急！', 0, 1, 0, NULL, '0', 1, 1492503046166, 0),
(19, 31, 17, '今天在大办公室开会！主要内容涉及关于【萌社】的商业模式该何去何从！请大家做好准备', 2, 1, 0, '学院大办公室啦啦', '1492534030483', 1, 1492534165636, 0),
(20, 31, 17, '4月20日晚餐的任务！！大家务必准备好，有贵客到需要款待！', 1, 1, 0, NULL, '1492677392048', 1, 1492684729394, 0),
(21, 31, 17, '上街买菜，菜谱如下【猪肉 500g；生菜 300g；葱、香菜 1元；小白菜 200g；鸡蛋 6颗】', 1, 1, 20, NULL, '1492677392048', 1, 1492684729394, 0),
(22, 31, 17, '厨房卫生、工具准备，内容包括【刀具、炊具等等洗净摆放好；餐桌抹干净，餐椅摆好位置】', 1, 1, 20, NULL, '1492677392048', 1, 1492684729394, 0),
(23, 31, 17, 'LOL俱乐部 | 内部篮球赛任务分配', 1, 0, 0, NULL, '1492677392048', 1, 1492685317027, 0),
(24, 31, 17, '场内秩序维持和后勤工作', 1, 0, 23, NULL, '1492677392048', 1, 1492685317027, 0),
(25, 31, 17, '摄像负责', 1, 0, 23, NULL, '1492677392048', 1, 1492685317027, 0),
(26, 31, 17, 'LOL俱乐部公益在行动 | 扫大街', 1, 0, 0, NULL, '1492677392048', 1, 1492685427456, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE IF NOT EXISTS `t_comment` (
  `comment_id` bigint(224) NOT NULL COMMENT '自增id',
  `student_id` bigint(224) NOT NULL COMMENT '学生id',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '评论内容',
  `create_date` bigint(13) NOT NULL COMMENT '创建日期，时间戳的字符串',
  `source_type` int(2) NOT NULL COMMENT '评论来源，1活动，2任务',
  `source_id` bigint(224) NOT NULL COMMENT '来源id，如果是任务就是任务的id，以此类推',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '评论的状态，0未审核，1审核通过，2审核失败；默认为0未审核',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除；默认为0'
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='评论基本信息表';

--
-- Dumping data for table `t_comment`
--

INSERT INTO `t_comment` (`comment_id`, `student_id`, `content`, `create_date`, `source_type`, `source_id`, `status`, `is_del`) VALUES
(1, 1, '我曹~居然还能评论活动', 1491029331999, 1, 1, 1, 0),
(7, 4, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 1, 1, 1, 0),
(8, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 1, 1, 0),
(9, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(10, 2, '我曹！！这公司太™萌了！！萌盟！！妈的好有爱哈哈哈好喜欢', 1491029331999, 2, 3, 1, 0),
(11, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 4, 1, 0),
(12, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 5, 1, 0),
(13, 9, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 6, 1, 0),
(14, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 7, 1, 0),
(15, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 8, 1, 0),
(16, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 9, 1, 0),
(17, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 10, 1, 0),
(18, 18, '这个活动是好活动！！我喜欢', 1491029331999, 2, 3, 1, 0),
(19, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 3, 1, 0),
(20, 38, 'TM我爱上了这家公司的法人！！么么哒(づ￣ 3￣)づ', 1491029331999, 2, 3, 1, 0),
(21, 14, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 4, 1, 0),
(22, 16, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 5, 1, 0),
(23, 2, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 6, 1, 0),
(24, 5, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 7, 1, 0),
(25, 2, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 8, 1, 0),
(26, 8, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 9, 1, 0),
(27, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 10, 1, 0),
(28, 18, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 11, 1, 0),
(29, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(30, 1, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 1, 1, 0),
(31, 2, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(32, 3, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(33, 4, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(34, 5, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(35, 6, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(36, 7, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(37, 8, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(38, 9, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(39, 10, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(40, 11, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(41, 12, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(42, 13, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(43, 14, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(44, 15, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(45, 16, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(46, 17, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(47, 18, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(48, 19, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(49, 20, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(50, 21, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0),
(51, 22, '™神奇的微社，，哈哈哈为什么你们不叫社社哈哈哈', 1491029331999, 2, 2, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_school`
--

DROP TABLE IF EXISTS `t_school`;
CREATE TABLE IF NOT EXISTS `t_school` (
  `school_id` bigint(224) NOT NULL COMMENT '自增id',
  `name` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '学校\\院系\\专业名字',
  `parent_id` bigint(224) NOT NULL DEFAULT '0' COMMENT '父级id，t_school.id',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1未删除'
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学校、院系、专业关系表';

--
-- Dumping data for table `t_school`
--

INSERT INTO `t_school` (`school_id`, `name`, `parent_id`, `is_del`) VALUES
(1, '广州大学华软软件学院', 0, 0),
(2, '软件工程系', 1, 0),
(3, '国际经贸系', 1, 0),
(4, '计算机系', 1, 0),
(5, '电子系', 1, 0),
(6, '财会系', 1, 0),
(7, '外语系', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_student`
--

DROP TABLE IF EXISTS `t_student`;
CREATE TABLE IF NOT EXISTS `t_student` (
  `student_id` bigint(224) NOT NULL COMMENT '自增id',
  `school_id` bigint(224) DEFAULT NULL COMMENT 't_school外键，学校id',
  `mobile` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '手机号码',
  `nick_name` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户密码',
  `avatar_url` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '头像地址',
  `birthday` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '生日，时间戳的字符串形式保存',
  `qrcode_url` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '个人二维码地址',
  `class_name` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '班级名称',
  `graduate_year` int(4) DEFAULT NULL COMMENT '年级，例如2014级的话存2014',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '学生状态，0未认证，1已认证，2已毕业，3认证失败，4冻结',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生基本个人信息表';

--
-- Dumping data for table `t_student`
--

INSERT INTO `t_student` (`student_id`, `school_id`, `mobile`, `nick_name`, `real_name`, `password`, `avatar_url`, `birthday`, `qrcode_url`, `class_name`, `graduate_year`, `status`, `is_del`) VALUES
(1, 2, '13570222223', 'padakill', '潘', '113bdda69031c0e135f702c36cc1e7b3', 'http://pic.3h3.com/up/2012-12/20121224142610096090.jpg', NULL, NULL, '软件测试1班', 2012, 1, 0),
(2, 4, '13522228122', 'zzy', '源', '2ce5d10473401fddab0dafa557021abb', 'http://p2.wmpic.me/article/2015/03/16/1426483393_DXGAJIiR.jpeg', NULL, NULL, '软件测试1班', 2012, 1, 0),
(3, 2, '123456711198', 'fpp', '方盼盼', '64f96afe2a5ae316f2837e76d6bb4a50', 'http://www.yangshuai.net/wp-content/uploads/2012/01/812.jpg', NULL, NULL, '软件测试1班', 2016, 1, 0),
(4, 3, '123451111190', 'lcc', '刘畅畅', 'ab1bc2c618cc01e04865fa642b3c50d1', 'http://pic24.nipic.com/20121017/10993561_165851054340_2.jpg', NULL, NULL, '国际经贸系1班', 2016, 1, 0),
(5, 3, '13576087650', 'fcc', '方常常', 'f9b06b67bc44d794311967413e8e1bbb', 'http://mtx.baibaidu.com/upload/10/13/10/15/xs3paor3eyl.jpg', NULL, NULL, '国际经贸系2班', 2016, 1, 0),
(6, 7, '12987065789', 'algd', '阿里嘎多', 'ed85d992ff9bd9cb6a422117a41a0dba', 'http://www.chinanews.com/cr/2014/0611/2961532466.jpg', NULL, NULL, '翻译一班', 2016, 1, 0),
(7, 7, '10977937659', 'Double L', '李拉拉', '072a7cf63225e5cf005071dbcffd6c82', 'http://www.rensheng2.com/upload/2015/12/18/6cf0b76e-797e-4c06-b868-7a2f62de156b.jpg', NULL, NULL, '商务英语2班', 2012, 1, 0),
(8, 2, '10876893064', '张NN', '张娜娜', '8ba6848668c24675906349e5280f6f36', 'http://upload.chinaz.com/2014/0825/1408954661429.jpg', NULL, NULL, '软件金融一班', 2012, 1, 0),
(9, 3, '30987654918', 'LDD', '林大大', '4d046df7d5dd71e69afe12cb023a35c5', 'http://images.liqucn.com/h017/h44/img201408251620430_info440X440.jpg', NULL, NULL, '大佐科技', NULL, 1, 0),
(10, 4, '10976802876', '达佐', '达佐', '553d3bf4382686e9b9c0b399c4a1095a', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRR4QgDwL1bi0JGjM1e9UMsI49p6GajDuduYxvrCuMUb4StxggU', NULL, NULL, NULL, NULL, 1, 0),
(11, 5, '87690283761', '林Zed', '林大头', '7c60f0b24dc083f5a8697b2b8d47abc8', 'http://img.t.388g.com/allimg/c130402/1364Z0c1113F-544O8.jpg', NULL, NULL, NULL, NULL, 1, 0),
(12, 3, '18261118771', '点都德', '点达佐', '03b278bd482b184c19662a4dad6aa6aa', 'http://img2.100bt.com/upload/ttq/20121226/1356506263880.jpg', NULL, NULL, NULL, NULL, 1, 0),
(13, 5, '19877788922', '3L', '刘大龄', '5476f37b4eca582e63bcc213e0efe03b', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSm7OOwsHyjhDjsepQwi98RyeDGpptAB-Dl9JPnnJpNnsMMP_6j', NULL, NULL, NULL, NULL, 0, 0),
(14, 6, '08922223781', '灰姑娘', '放大做的', 'c11e420956458c1a35dfc6cde1717c6b', 'http://img.qq745.com/uploads/allimg/150507/0S4051P3-1.jpg', NULL, NULL, NULL, NULL, 0, 0),
(15, 4, '82726373812', '张擦灰姑娘擦还能', '长长长', '67717408b9bf60ad57d671fa87057452', 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTHiRVGvj5Rv1wafjhDMs_oGXxPWQmb9SnbmoFl4cbMDBc7l6z1', NULL, NULL, NULL, NULL, 0, 0),
(16, 6, '98767890892', '本大人', '本拉登', 'b7b65757a5e7e44d78b5a45ae18c580c', 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTHiRVGvj5Rv1wafjhDMs_oGXxPWQmb9SnbmoFl4cbMDBc7l6z1', NULL, NULL, NULL, NULL, 1, 0),
(17, 4, '67899928762', '爱鸡巴', '奥巴马', '1485fe76fcaf5f2fa98f1a4d2fd3dbd1', 'http://i1.sinaimg.cn/gm/2013/0106/U6792P115DT20130106135625.jpg', NULL, NULL, NULL, NULL, 1, 0),
(18, 4, '76543456782', 'jiba', '黄鸡吧', '983325dab75c7d3a69af09cd1b5e7a03', 'http://img32.mtime.cn/up/2012/12/23/030201.11164602_500.jpg', NULL, NULL, NULL, NULL, 1, 0),
(19, 5, '13987111234', NULL, NULL, 'f22b19b013f3f92ec6a458411bce100f', 'http://img1.gamersky.com/image2013/01/20130108v_5/gamersky_002small_004_20131810276AE.jpg', NULL, NULL, NULL, NULL, 0, 0),
(20, 6, '13434565679', NULL, NULL, '0dc862e972ebce92fe50bffacdbf1f3b', 'http://pic2.ooopic.com/10/81/60/92b1OOOPIC6a.jpg', NULL, NULL, NULL, NULL, 0, 0),
(21, 2, '13987954300', NULL, NULL, 'e6516f55c9a597708e773c5d9662140d', 'http://www.qq7b.com/uploads/allimg/130607/1-13060F91046.jpg', NULL, NULL, NULL, NULL, 0, 0),
(22, NULL, '13987323335', NULL, NULL, 'bf0ee77ea274c7e3cf926e29f2889cb8', 'http://img.gx8899.com/uploads/allimg/150324/111415E96-5.jpg', NULL, NULL, NULL, NULL, 0, 0),
(23, 3, '26738987654', '鸡巴大', '拉', '811895dc8f3de0a1a77176acf15cd48a', 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQCBU3va3ri58I4BMmiy3mTrOGoCe_HD1PG25CmPKnIPbNudYyY', NULL, NULL, NULL, NULL, 0, 0),
(24, NULL, '13570522222', NULL, NULL, '811895dc8f3de0a1a77176acf15cd48a', 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRALGS3q0G17SWs0NpwFncajM8IBQyPBDrZXgoFxYWXWGbs1jbN', NULL, NULL, NULL, NULL, 0, 0),
(25, NULL, '13573338666', NULL, NULL, NULL, 'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcS-NUUMwdmeIRPpWug5-e1oNyDudBzEGoIkUIzYh_sRQVJyqPBF', NULL, NULL, NULL, NULL, 0, 0),
(35, NULL, '18333382091', NULL, NULL, '64edd2019e1437f05f6fbafd92738cf3', 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcS2DcqwN1FhhNl-I8CVSPez0SU1gw_gYoOIrN__-t4OguPr3H6K', NULL, NULL, NULL, NULL, 0, 0),
(38, NULL, '186444478706', '大佐', '佐', '62f0d7b07ac74b37a0a0a95bef1db1b0', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRNNlk464HI1ya9Df8vl3epcEagakInbyW_2eeeKvQkDehbQzf', NULL, NULL, NULL, 2012, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_student_activity_relation`
--

DROP TABLE IF EXISTS `t_student_activity_relation`;
CREATE TABLE IF NOT EXISTS `t_student_activity_relation` (
  `stu_act_rel_id` bigint(224) NOT NULL COMMENT '自增id',
  `activity_id` bigint(224) NOT NULL COMMENT '活动id，t_club_activity.id',
  `student_id` bigint(224) NOT NULL COMMENT '学生id，t_student.id',
  `is_apply` int(1) NOT NULL DEFAULT '0' COMMENT '是否已经报名，0未报名，1预报名，2已报名；默认为0',
  `is_sign` int(1) NOT NULL DEFAULT '0' COMMENT '是否已经签到，1：已签到，0：未签到',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除；默认为0',
  `create_date` bigint(15) NOT NULL DEFAULT '0' COMMENT '创建日期时间戳',
  `sign_date` bigint(15) NOT NULL DEFAULT '0' COMMENT '签到日期时间戳',
  `apply_date` int(1) NOT NULL DEFAULT '0' COMMENT '报名签到时间戳'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生、活动关系表';

--
-- Dumping data for table `t_student_activity_relation`
--

INSERT INTO `t_student_activity_relation` (`stu_act_rel_id`, `activity_id`, `student_id`, `is_apply`, `is_sign`, `is_del`, `create_date`, `sign_date`, `apply_date`) VALUES
(1, 17, 14, 0, 0, 0, 1492659364606, 0, 0),
(2, 17, 18, 0, 0, 0, 1492659364606, 0, 0),
(3, 17, 1, 0, 0, 0, 1492659364606, 0, 0),
(4, 17, 2, 0, 0, 0, 1492659364606, 0, 0),
(5, 17, 4, 0, 0, 0, 1492659364606, 0, 0),
(6, 17, 11, 0, 0, 0, 1492659364606, 0, 0),
(7, 17, 23, 0, 0, 0, 1492659364606, 0, 0),
(8, 17, 38, 0, 0, 0, 1492659364606, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_student_club_graduate_relation`
--

DROP TABLE IF EXISTS `t_student_club_graduate_relation`;
CREATE TABLE IF NOT EXISTS `t_student_club_graduate_relation` (
  `stu_clu_gra_rel_id` bigint(224) NOT NULL COMMENT '自增id',
  `graduate_id` bigint(224) NOT NULL COMMENT '届数id，t_club_graduate.id',
  `student_id` bigint(224) NOT NULL COMMENT '学生id，t_student.id',
  `status` int(2) NOT NULL COMMENT '当前学生在此届社团的状态，0已退出，1仍在职，2拒绝加入',
  `department_id` bigint(224) DEFAULT NULL COMMENT '对应的社团部门id',
  `job_id` bigint(224) DEFAULT NULL COMMENT '对应的社团职位id',
  `super_admin` int(1) NOT NULL COMMENT '是否为当届的超级管理员，相当于创建者',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生、届数关系表';

--
-- Dumping data for table `t_student_club_graduate_relation`
--

INSERT INTO `t_student_club_graduate_relation` (`stu_clu_gra_rel_id`, `graduate_id`, `student_id`, `status`, `department_id`, `job_id`, `super_admin`, `is_del`) VALUES
(1, 1, 1, 2, 3, 6, 1, 0),
(2, 2, 1, 1, 1, 1, 0, 0),
(3, 5, 2, 1, 3, 5, 0, 0),
(4, 3, 2, 1, 3, 5, 0, 0),
(5, 6, 3, 1, 1, 2, 0, 0),
(6, 7, 4, 1, 4, 2, 0, 0),
(7, 8, 5, 1, 3, 1, 0, 0),
(8, 9, 6, 1, 2, 3, 0, 0),
(9, 10, 7, 1, 3, 4, 0, 0),
(10, 11, 8, 1, 2, 4, 0, 0),
(11, 12, 9, 1, 3, 5, 0, 0),
(12, 13, 10, 1, 2, 1, 0, 0),
(13, 14, 11, 1, 3, 1, 0, 0),
(14, 15, 12, 1, 2, 4, 0, 0),
(15, 16, 13, 1, 3, 3, 0, 0),
(16, 17, 14, 1, 4, 5, 0, 0),
(17, 12, 15, 1, 2, 5, 0, 0),
(18, 9, 16, 1, 2, 3, 0, 0),
(19, 9, 17, 1, 2, 4, 0, 0),
(20, 17, 18, 1, 3, 1, 0, 0),
(21, 17, 1, 1, 2, 5, 1, 0),
(22, 17, 2, 1, 2, 5, 0, 0),
(23, 17, 4, 1, 2, 5, 0, 0),
(24, 17, 11, 1, 2, 5, 0, 0),
(25, 17, 23, 1, 2, 2, 0, 0),
(29, 22, 1, 1, NULL, NULL, 1, 0),
(30, 17, 38, 1, 2, 3, 0, 0),
(31, 1, 38, 1, 4, 6, 0, 0),
(32, 1, 21, 1, 1, 2, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_student_club_relation`
--

DROP TABLE IF EXISTS `t_student_club_relation`;
CREATE TABLE IF NOT EXISTS `t_student_club_relation` (
  `stu_clu_rel_id` bigint(224) NOT NULL COMMENT '自增id',
  `student_id` bigint(224) NOT NULL COMMENT '学生id，对应t_student.id',
  `club_id` bigint(224) NOT NULL COMMENT '社团id，对应t_club.id',
  `is_enter` int(2) NOT NULL DEFAULT '0' COMMENT '是否已经进入社团，0未进入，1已进入，2已拒绝',
  `is_follow` int(2) NOT NULL DEFAULT '0' COMMENT '是否关注社团，0未关注，1已关注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生、社团关系表';

-- --------------------------------------------------------

--
-- Table structure for table `t_student_mission_relation`
--

DROP TABLE IF EXISTS `t_student_mission_relation`;
CREATE TABLE IF NOT EXISTS `t_student_mission_relation` (
  `stu_mis_rel_id` bigint(224) NOT NULL COMMENT '自增id',
  `student_id` bigint(224) NOT NULL COMMENT '学生id，t_student.id',
  `mission_id` bigint(224) NOT NULL COMMENT '任务id，t_mission.id',
  `is_leader` int(1) NOT NULL DEFAULT '0' COMMENT '是否为负责人，0不是负责人，1是负责人；默认为0',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '当前任务状态，0未确认，1已确认，2已完成，3请假；默认为0未确认',
  `is_sign` int(1) NOT NULL DEFAULT '0' COMMENT '是否签到，0未签到，1已签到；默认为签到',
  `comment` varchar(224) COLLATE utf8_bin DEFAULT NULL COMMENT '任务\\通知描述，例如请假理由等',
  `create_date` bigint(13) NOT NULL COMMENT '创建日期，为时间戳的字符串',
  `sign_date` bigint(13) NOT NULL DEFAULT '0' COMMENT '签到日期，为时间戳的字符串',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生、任务\\通知关系表';

--
-- Dumping data for table `t_student_mission_relation`
--

INSERT INTO `t_student_mission_relation` (`stu_mis_rel_id`, `student_id`, `mission_id`, `is_leader`, `status`, `is_sign`, `comment`, `create_date`, `sign_date`, `is_del`) VALUES
(1, 1, 1, 0, 0, 0, '', 1491836724154, 0, 0),
(2, 2, 1, 0, 0, 0, '', 1491836724154, 0, 0),
(3, 1, 3, 0, 0, 0, '', 1491836724154, 0, 0),
(4, 1, 5, 0, 1, 0, '', 1491836724154, 0, 0),
(5, 2, 3, 0, 0, 0, '', 1491836724154, 0, 0),
(6, 1, 4, 0, 0, 0, '这是test', 1491836724154, 0, 0),
(7, 2, 4, 0, 1, 1, '', 1491836724154, 0, 0),
(8, 1, 6, 1, 1, 0, '', 1491836724154, 0, 0),
(9, 2, 2, 0, 0, 0, '', 1491836724154, 0, 0),
(10, 1, 8, 0, 0, 0, '', 1491836724154, 0, 0),
(11, 35, 7, 0, 0, 0, '', 1491836724154, 0, 0),
(12, 2, 8, 0, 0, 0, '', 1491836724154, 0, 0),
(13, 2, 7, 0, 0, 0, '', 1491836724154, 0, 0),
(14, 1, 9, 0, 0, 0, '', 1491836724154, 0, 0),
(15, 1, 10, 0, 0, 0, '', 1491836724154, 0, 0),
(16, 5, 4, 0, 0, 0, 'studentId = 2', 1491836724154, 0, 0),
(21, 38, 3, 0, 0, 0, '', 1491836724154, 0, 0),
(22, 38, 5, 0, 0, 0, '', 1491836724154, 0, 0),
(23, 38, 1, 0, 0, 0, '', 1491836724154, 0, 0),
(24, 38, 4, 0, 3, 0, '我要打飞机，没空参加会议', 1491836724154, 0, 0),
(25, 38, 6, 1, 0, 0, '', 1491836724154, 0, 0),
(26, 38, 2, 0, 0, 0, '', 1491836724154, 0, 0),
(27, 1, 2, 0, 0, 0, '', 1491836724154, 0, 0),
(28, 1, 11, 0, 0, 0, '', 1491836724154, 0, 0),
(37, 14, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(38, 18, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(39, 12, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(40, 2, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(41, 4, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(42, 11, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(43, 23, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(44, 38, 17, 0, 0, 0, NULL, 1492502049733, 0, 0),
(45, 14, 18, 0, 1, 0, NULL, 1492503046166, 1492503046166, 0),
(46, 18, 18, 0, 0, 0, NULL, 1492503046166, 0, 0),
(47, 12, 18, 0, 0, 0, NULL, 1492503046166, 0, 0),
(48, 2, 18, 0, 0, 0, NULL, 1492503046166, 0, 0),
(49, 4, 18, 0, 0, 0, NULL, 1492503046166, 0, 0),
(50, 11, 18, 0, 0, 0, NULL, 1492503046166, 0, 0),
(51, 23, 18, 0, 0, 0, NULL, 1492503046166, 0, 0),
(52, 38, 18, 0, 1, 0, NULL, 1492503700166, 1492503046166, 0),
(53, 14, 19, 1, 0, 0, NULL, 1492534165636, 0, 0),
(54, 18, 19, 0, 0, 0, NULL, 1492534165636, 0, 0),
(55, 12, 19, 0, 0, 0, NULL, 1492534165636, 0, 0),
(56, 2, 19, 0, 0, 0, NULL, 1492534165636, 0, 0),
(57, 4, 19, 0, 0, 0, NULL, 1492534165636, 0, 0),
(58, 11, 19, 0, 0, 0, NULL, 1492534165636, 0, 0),
(59, 23, 19, 0, 0, 0, NULL, 1492534165636, 0, 0),
(60, 38, 19, 1, 0, 0, NULL, 1492534165636, 0, 0),
(61, 1, 19, 1, 0, 0, NULL, 1492534165636, 0, 0),
(62, 1, 21, 0, 0, 0, NULL, 1492684729394, 0, 0),
(63, 2, 21, 0, 0, 0, NULL, 1492684729394, 0, 0),
(64, 4, 21, 0, 0, 0, NULL, 1492684729394, 0, 0),
(65, 38, 22, 0, 0, 0, NULL, 1492684729394, 0, 0),
(66, 23, 22, 0, 0, 0, NULL, 1492684729394, 0, 0),
(67, 1, 24, 0, 0, 0, NULL, 1492685317027, 0, 0),
(68, 2, 24, 0, 0, 0, NULL, 1492685317027, 0, 0),
(69, 4, 24, 0, 0, 0, NULL, 1492685317027, 0, 0),
(70, 38, 25, 0, 0, 0, NULL, 1492685317027, 0, 0),
(71, 23, 25, 0, 0, 0, NULL, 1492685317027, 0, 0),
(72, 1, 26, 0, 0, 0, NULL, 1492685427456, 0, 0),
(73, 2, 26, 0, 0, 0, NULL, 1492685427456, 0, 0),
(74, 4, 26, 0, 0, 0, NULL, 1492685427456, 0, 0),
(75, 38, 26, 0, 0, 0, NULL, 1492685427456, 0, 0),
(76, 38, 9, 0, 0, 0, '', 1491836724154, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `t_student_option_relation`
--

DROP TABLE IF EXISTS `t_student_option_relation`;
CREATE TABLE IF NOT EXISTS `t_student_option_relation` (
  `stu_opt_rel_id` bigint(224) NOT NULL COMMENT '自增id',
  `student_id` bigint(224) NOT NULL COMMENT '学生id，对应t_student.id',
  `vote_id` bigint(224) NOT NULL COMMENT '投票id，对应t_vote.id',
  `vote_option_ids` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '选中的id，多个的话用逗号隔开存储',
  `status` int(2) NOT NULL COMMENT '状态，0未操作，1已投票'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生、投票选项关系';

-- --------------------------------------------------------

--
-- Table structure for table `t_token`
--

DROP TABLE IF EXISTS `t_token`;
CREATE TABLE IF NOT EXISTS `t_token` (
  `uid` bigint(224) NOT NULL COMMENT '用户id，对应的是t_student.id',
  `token` varchar(200) COLLATE utf8_bin NOT NULL COMMENT 'token值',
  `caller` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'caller值，一般是固定指定的值',
  `create_date` bigint(13) NOT NULL COMMENT 'token的创建时间戳'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='token表格';

--
-- Dumping data for table `t_token`
--

INSERT INTO `t_token` (`uid`, `token`, `caller`, `create_date`) VALUES
(21, 'MzE5ODBlNDJlNGQ0OGQ1ZDcwNjI3M2ZmNjBjNTA5ODVfZGF0ZT0xNDkxODE4NzIyOTQx', 'chrome_test', 1491818722941),
(1, 'MTg4NDY4ZjQ1YzUxYmVhMGZlOWFlNDg1MTZmOWE0NDJfZGF0ZT0xNDkyNDEzMjUyMjk5', 'chrome_test', 1492413252299),
(28, 'Y2ZiOGVjZmM1YzBkNjIwNDAzNTAzOTg1Mzg2ZTIwNmRfZGF0ZT0xNDkxNjY2NjU5OTQ5', 'ios_test', 1491666659949),
(29, 'NjM2ZjYyNjVhYTBiMjhkNmYyN2E2NjM5MTNkZjI1MjVfZGF0ZT0xNDkxNjY3NjEzNDQ0', 'ios_test', 1491667613444),
(30, 'YjNhNjEzNzUyMWZjZjRjMGIxYzdhMjJkNmVjZTcwODVfZGF0ZT0xNDkxNjY3Nzg1NDcw', 'ios_test', 1491667785470),
(31, 'MjU3NmYxMzYxMGEyMzk3NWNmMjljYzIzNzE1MDBkMmJfZGF0ZT0xNDkxNjY3ODU5MjMx', 'ios_test', 1491667859231),
(32, 'ZjY4ZGU0NTQ0ZWYzNWE0NThkNWQ4YjRmYzcxMWYzZWFfZGF0ZT0xNDkxNjY4MDE4ODcw', 'ios_test', 1491668018870),
(33, 'M2ZjYWM0NGJmNDM0NzFkYjE0YWE2YzM2YTdmNGI1NTBfZGF0ZT0xNDkxNjY4MTY1ODc5', 'ios_test', 1491668165879),
(34, 'NDcxNDhjNmNjYmY0YmY5MGJiMmQ2Zjc4ZTNlYzYwMjdfZGF0ZT0xNDkxNjY4Mjg4NjYy', 'ios_test', 1491668288662),
(35, 'ZmQ0ZDAyNzE4YThhMTkwYzM4NWJlNGY1MWIwZTJlOGZfZGF0ZT0xNDkxNzM1NjQ4NjE4', 'ios_test', 1491735648618),
(36, 'ZGU5MTgyZjYyYmUwZTdkNmM0MjZkMTlmNjgwNGVhOWNfZGF0ZT0xNDkxNzM4Mzk5MjA0', 'ios_test', 1491738399204),
(37, 'OWEwOGM5OTA5ZmJlZGQ0MTI2ZTBlNmUwZWQyMzhhYmFfZGF0ZT0xNDkxNzUxOTQyNDg4', 'ios_test', 1491751942488),
(38, 'YjU3NTJjMWRlMDE4MWQ4NDAwMmY4Y2FmMmFiMjM2OWJfZGF0ZT0xNDkxODk3OTQ0NDIy', 'ios_test', 1491897944422),
(38, 'NmQ1NTY2MjY2MTUxZDM4ZjE0YTM5ZWJhYmE5NTIwMDRfZGF0ZT0xNDkxODk4NzM5MDk4', 'chrome_test', 1491898739098),
(1, 'OTAyZjU4Y2E5NzJiYzVlYmY5YTA4YjhjNGI4MGIyZGJfZGF0ZT0xNDkyNTY2NDU5MDky', 'ios_test', 1492566459092);

-- --------------------------------------------------------

--
-- Table structure for table `t_vote`
--

DROP TABLE IF EXISTS `t_vote`;
CREATE TABLE IF NOT EXISTS `t_vote` (
  `vote_id` bigint(224) NOT NULL COMMENT '自增id',
  `club_id` bigint(224) NOT NULL COMMENT '社团id，对应t_club.id',
  `subject` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '投票主题',
  `option_count` int(10) NOT NULL DEFAULT '1' COMMENT '可选个数，默认为选一个',
  `is_undefined` int(2) NOT NULL DEFAULT '0' COMMENT '是否为不定项选择，0定向选择，1不定项选择',
  `create_date` bigint(13) NOT NULL COMMENT '创建时间，时间戳的字符串',
  `deadline` bigint(13) NOT NULL COMMENT '截止时间，时间戳的字符串',
  `is_anonymous` int(2) NOT NULL DEFAULT '0' COMMENT '是否匿名投票，0公开，1匿名；默认为0公开',
  `is_public` int(2) NOT NULL DEFAULT '0' COMMENT '是否公开所有人可以投票，0社团内，1所有人；默认为0社团内',
  `is_termination` int(2) NOT NULL DEFAULT '0' COMMENT '是否终止投票，0继续投票，1终止投票；默认为0继续投票',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='投票主题表';

-- --------------------------------------------------------

--
-- Table structure for table `t_vote_option`
--

DROP TABLE IF EXISTS `t_vote_option`;
CREATE TABLE IF NOT EXISTS `t_vote_option` (
  `vote_option_id` bigint(224) NOT NULL COMMENT '自增id',
  `vote_id` bigint(224) NOT NULL COMMENT '投票id，对应t_vote.id',
  `content` varchar(224) COLLATE utf8_bin NOT NULL COMMENT '选项内容',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0未删除，1未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='投票选项内容表';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_club`
--
ALTER TABLE `t_club`
  ADD PRIMARY KEY (`club_id`),
  ADD KEY `school_id` (`school_id`);

--
-- Indexes for table `t_club_activity`
--
ALTER TABLE `t_club_activity`
  ADD PRIMARY KEY (`club_activity_id`),
  ADD KEY `club_id` (`club_id`),
  ADD KEY `sponsor_id` (`sponsor_id`);

--
-- Indexes for table `t_club_authority`
--
ALTER TABLE `t_club_authority`
  ADD PRIMARY KEY (`club_authority_id`);

--
-- Indexes for table `t_club_department`
--
ALTER TABLE `t_club_department`
  ADD PRIMARY KEY (`department_id`);

--
-- Indexes for table `t_club_graduate`
--
ALTER TABLE `t_club_graduate`
  ADD PRIMARY KEY (`club_graduate_id`),
  ADD KEY `club_id` (`club_id`);

--
-- Indexes for table `t_club_honor`
--
ALTER TABLE `t_club_honor`
  ADD PRIMARY KEY (`honor_id`),
  ADD KEY `club_id` (`club_id`);

--
-- Indexes for table `t_club_job`
--
ALTER TABLE `t_club_job`
  ADD PRIMARY KEY (`job_id`);

--
-- Indexes for table `t_club_mission`
--
ALTER TABLE `t_club_mission`
  ADD PRIMARY KEY (`mission_id`),
  ADD KEY `club_id` (`club_id`),
  ADD KEY `sponsor_id` (`sponsor_id`),
  ADD KEY `graudate_id` (`graduate_id`);

--
-- Indexes for table `t_comment`
--
ALTER TABLE `t_comment`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `source_id` (`source_id`),
  ADD KEY `student_id_2` (`student_id`);

--
-- Indexes for table `t_school`
--
ALTER TABLE `t_school`
  ADD PRIMARY KEY (`school_id`);

--
-- Indexes for table `t_student`
--
ALTER TABLE `t_student`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `school_id` (`school_id`);

--
-- Indexes for table `t_student_activity_relation`
--
ALTER TABLE `t_student_activity_relation`
  ADD PRIMARY KEY (`stu_act_rel_id`),
  ADD KEY `activity_id` (`activity_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `t_student_club_graduate_relation`
--
ALTER TABLE `t_student_club_graduate_relation`
  ADD PRIMARY KEY (`stu_clu_gra_rel_id`),
  ADD KEY `graduate_id` (`graduate_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `department_id` (`department_id`),
  ADD KEY `job_id` (`job_id`);

--
-- Indexes for table `t_student_club_relation`
--
ALTER TABLE `t_student_club_relation`
  ADD PRIMARY KEY (`stu_clu_rel_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `club_id` (`club_id`);

--
-- Indexes for table `t_student_mission_relation`
--
ALTER TABLE `t_student_mission_relation`
  ADD PRIMARY KEY (`stu_mis_rel_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `mission_id` (`mission_id`);

--
-- Indexes for table `t_student_option_relation`
--
ALTER TABLE `t_student_option_relation`
  ADD PRIMARY KEY (`stu_opt_rel_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `vote_id` (`vote_id`);

--
-- Indexes for table `t_vote`
--
ALTER TABLE `t_vote`
  ADD PRIMARY KEY (`vote_id`),
  ADD KEY `club_id` (`club_id`);

--
-- Indexes for table `t_vote_option`
--
ALTER TABLE `t_vote_option`
  ADD PRIMARY KEY (`vote_option_id`),
  ADD KEY `vote_id` (`vote_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `t_club`
--
ALTER TABLE `t_club`
  MODIFY `club_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=45;
--
-- AUTO_INCREMENT for table `t_club_activity`
--
ALTER TABLE `t_club_activity`
  MODIFY `club_activity_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `t_club_authority`
--
ALTER TABLE `t_club_authority`
  MODIFY `club_authority_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `t_club_department`
--
ALTER TABLE `t_club_department`
  MODIFY `department_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `t_club_graduate`
--
ALTER TABLE `t_club_graduate`
  MODIFY `club_graduate_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `t_club_honor`
--
ALTER TABLE `t_club_honor`
  MODIFY `honor_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `t_club_job`
--
ALTER TABLE `t_club_job`
  MODIFY `job_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `t_club_mission`
--
ALTER TABLE `t_club_mission`
  MODIFY `mission_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `t_comment`
--
ALTER TABLE `t_comment`
  MODIFY `comment_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `t_school`
--
ALTER TABLE `t_school`
  MODIFY `school_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `t_student`
--
ALTER TABLE `t_student`
  MODIFY `student_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `t_student_activity_relation`
--
ALTER TABLE `t_student_activity_relation`
  MODIFY `stu_act_rel_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `t_student_club_graduate_relation`
--
ALTER TABLE `t_student_club_graduate_relation`
  MODIFY `stu_clu_gra_rel_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `t_student_club_relation`
--
ALTER TABLE `t_student_club_relation`
  MODIFY `stu_clu_rel_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id';
--
-- AUTO_INCREMENT for table `t_student_mission_relation`
--
ALTER TABLE `t_student_mission_relation`
  MODIFY `stu_mis_rel_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id',AUTO_INCREMENT=77;
--
-- AUTO_INCREMENT for table `t_student_option_relation`
--
ALTER TABLE `t_student_option_relation`
  MODIFY `stu_opt_rel_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id';
--
-- AUTO_INCREMENT for table `t_vote`
--
ALTER TABLE `t_vote`
  MODIFY `vote_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id';
--
-- AUTO_INCREMENT for table `t_vote_option`
--
ALTER TABLE `t_vote_option`
  MODIFY `vote_option_id` bigint(224) NOT NULL AUTO_INCREMENT COMMENT '自增id';
--
-- Constraints for dumped tables
--

--
-- Constraints for table `t_club`
--
ALTER TABLE `t_club`
  ADD CONSTRAINT `t_club_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `t_school` (`school_id`);

--
-- Constraints for table `t_club_activity`
--
ALTER TABLE `t_club_activity`
  ADD CONSTRAINT `t_club_activity_ibfk_3` FOREIGN KEY (`club_id`) REFERENCES `t_club` (`club_id`),
  ADD CONSTRAINT `t_club_activity_ibfk_4` FOREIGN KEY (`sponsor_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_club_graduate`
--
ALTER TABLE `t_club_graduate`
  ADD CONSTRAINT `t_club_graduate_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `t_club` (`club_id`);

--
-- Constraints for table `t_club_honor`
--
ALTER TABLE `t_club_honor`
  ADD CONSTRAINT `t_club_honor_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `t_club` (`club_id`);

--
-- Constraints for table `t_club_mission`
--
ALTER TABLE `t_club_mission`
  ADD CONSTRAINT `t_club_mission_ibfk_3` FOREIGN KEY (`club_id`) REFERENCES `t_club` (`club_id`),
  ADD CONSTRAINT `t_club_mission_ibfk_4` FOREIGN KEY (`sponsor_id`) REFERENCES `t_student` (`student_id`),
  ADD CONSTRAINT `t_club_mission_ibfk_5` FOREIGN KEY (`graduate_id`) REFERENCES `t_club_graduate` (`club_graduate_id`);

--
-- Constraints for table `t_comment`
--
ALTER TABLE `t_comment`
  ADD CONSTRAINT `t_comment_ibfk_6` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_student`
--
ALTER TABLE `t_student`
  ADD CONSTRAINT `t_student_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `t_school` (`school_id`);

--
-- Constraints for table `t_student_activity_relation`
--
ALTER TABLE `t_student_activity_relation`
  ADD CONSTRAINT `t_student_activity_relation_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `t_club_activity` (`club_activity_id`),
  ADD CONSTRAINT `t_student_activity_relation_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_student_club_graduate_relation`
--
ALTER TABLE `t_student_club_graduate_relation`
  ADD CONSTRAINT `t_student_club_graduate_relation_ibfk_5` FOREIGN KEY (`department_id`) REFERENCES `t_club_department` (`department_id`),
  ADD CONSTRAINT `t_student_club_graduate_relation_ibfk_6` FOREIGN KEY (`job_id`) REFERENCES `t_club_job` (`job_id`),
  ADD CONSTRAINT `t_student_club_graduate_relation_ibfk_7` FOREIGN KEY (`graduate_id`) REFERENCES `t_club_graduate` (`club_graduate_id`),
  ADD CONSTRAINT `t_student_club_graduate_relation_ibfk_8` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_student_club_relation`
--
ALTER TABLE `t_student_club_relation`
  ADD CONSTRAINT `t_student_club_relation_ibfk_2` FOREIGN KEY (`club_id`) REFERENCES `t_club` (`club_id`),
  ADD CONSTRAINT `t_student_club_relation_ibfk_3` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_student_mission_relation`
--
ALTER TABLE `t_student_mission_relation`
  ADD CONSTRAINT `t_student_mission_relation_ibfk_2` FOREIGN KEY (`mission_id`) REFERENCES `t_club_mission` (`mission_id`),
  ADD CONSTRAINT `t_student_mission_relation_ibfk_3` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_student_option_relation`
--
ALTER TABLE `t_student_option_relation`
  ADD CONSTRAINT `t_student_option_relation_ibfk_2` FOREIGN KEY (`vote_id`) REFERENCES `t_vote` (`vote_id`),
  ADD CONSTRAINT `t_student_option_relation_ibfk_3` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`student_id`);

--
-- Constraints for table `t_vote`
--
ALTER TABLE `t_vote`
  ADD CONSTRAINT `t_vote_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `t_club` (`club_id`);

--
-- Constraints for table `t_vote_option`
--
ALTER TABLE `t_vote_option`
  ADD CONSTRAINT `t_vote_option_ibfk_1` FOREIGN KEY (`vote_id`) REFERENCES `t_vote` (`vote_id`);
