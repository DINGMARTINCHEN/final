-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: campus_forum
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boards`
--

DROP TABLE IF EXISTS `boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boards`
--

LOCK TABLES `boards` WRITE;
/*!40000 ALTER TABLE `boards` DISABLE KEYS */;
INSERT INTO `boards` VALUES (1,'技术支持','编程、技术问题讨论'),(2,'学习交流','课程、考试、资料分享'),(3,'休闲娱乐','游戏、电影、闲聊'),(4,'校园生活','食堂、宿舍、活动');
/*!40000 ALTER TABLE `boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_likes`
--

DROP TABLE IF EXISTS `post_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_likes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_like` (`user_id`,`post_id`),
  KEY `idx_post_likes_post_id` (`post_id`),
  KEY `idx_post_likes_user_id` (`user_id`),
  CONSTRAINT `post_likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `post_likes_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_likes`
--

LOCK TABLES `post_likes` WRITE;
/*!40000 ALTER TABLE `post_likes` DISABLE KEYS */;
INSERT INTO `post_likes` VALUES (2,1,12,'2025-12-25 15:02:41'),(3,11,14,'2025-12-25 15:07:27'),(85,11,20,'2025-12-25 15:15:31'),(86,11,19,'2025-12-25 15:15:37'),(87,11,18,'2025-12-25 15:17:13'),(88,11,110,'2025-12-25 15:27:44'),(89,11,105,'2025-12-25 15:37:29'),(90,11,116,'2025-12-25 15:38:16'),(91,11,118,'2025-12-25 16:19:50'),(92,11,92,'2025-12-25 16:27:05'),(93,11,6,'2025-12-25 16:29:16'),(94,11,12,'2025-12-25 16:29:23'),(95,1,95,'2025-12-25 16:39:02'),(97,1,1,'2025-12-25 16:43:09'),(98,11,95,'2025-12-26 00:20:16');
/*!40000 ALTER TABLE `post_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `user_id` int DEFAULT NULL,
  `board_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `attachment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `views` int DEFAULT '0',
  `pinned` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `board_id` (`board_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`board_id`) REFERENCES `boards` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'考试技巧','认真复习',1,1,'2025-12-01 01:30:00','',157,0),(2,'Java Swing布局问题求助！GridBagLayout用不好','大佬们，谁能教教GridBagLayout怎么精准控制组件位置？我做界面总是乱七八糟😭 附上我的代码截图求指点！',8,1,'2025-12-03 06:20:00','',289,0),(3,'MySQL连接总是报“No suitable driver found”怎么办？','用JDBC连接本地MySQL，一直报这个错，已经加了mysql-connector-jar了还是不行，求解决方案！',9,1,'2025-12-05 03:15:00',NULL,135,0),(4,'IntelliJ IDEA调试技巧分享','分享几个超实用的调试技巧：条件断点、变量观看、Evaluate Expression，神器！新手必看~',10,1,'2025-12-07 08:40:00','',424,0),(5,'Git分支管理最佳实践','多人协作时怎么避免冲突？推荐rebase还是merge？来讨论讨论',10,1,'2025-12-09 02:05:00',NULL,268,0),(6,'求推荐好用的代码格式化插件','VSCode有Prettier，IDEA有什么类似的高效插件？',5,1,'2025-12-10 07:30:00',NULL,180,0),(7,'Python爬虫被反爬怎么办？','用requests爬某网站老是被封IP，有没有大佬教教绕过方法？',6,1,'2025-12-12 05:25:00',NULL,95,0),(8,'期末复习资料分享合集（软件工程+数据结构）','整理了王老师的软件工程PPT和课后答案，还有数据结构思维导图，私信我发网盘链接！互助学习✊',8,2,'2025-12-14 11:10:00','',312,0),(9,'高数挂科的举手🙋‍♂️ 有没有补考经验分享？','上次高数没过，这次求救！谁有重点章节总结或历年真题？',7,2,'2025-12-16 00:45:00',NULL,189,0),(10,'英语四级过线技巧','分享我的备考经验：刷真题+背作文模板+每天听力30分钟，裸考420过！',6,2,'2025-12-18 04:30:00','',256,0),(11,'考研党集合！报哪个学校哪个专业？','23届的学长学姐们，来交流交流经验吧，我纠结软工还是人工智能',2,2,'2025-12-20 09:20:00',NULL,167,0),(12,'如何高效记笔记？推荐工具和方法','<html>\n  <head>\n    \n  </head>\n  <body>\n    &#25105;&#29992;Notion+&#25163;&#20889;&#26495;&#65292;&#36229;&#32423;&#22909;&#29992;&#65281;&#27426;&#36814;&#20998;&#20139;&#20320;<i>&#20204;&#30340;<b>&#31508;&#35760;&#31070;&#22120;</b></i>\n  </body>\n</html>',3,2,'2025-12-21 01:15:00','',241,0),(13,'线性代数证明题太难了求方法','总觉得证明题无从下手，有没有大佬总结的解题套路？',1,2,'2025-12-22 06:50:00',NULL,145,0),(14,'最近好看的剧推荐！','刷完《隐秘的角落》太烧脑了！还有《庆余年2》超期待，大家最近在追什么？',4,3,'2025-12-23 12:10:00','',325,0),(15,'王者荣耀新赛季上分技巧','新赛季冲传奇了，有没有大佬带带？中路法师玩家求组队🎮',5,3,'2025-12-24 03:30:00',NULL,279,0),(16,'校园附近好吃的美食打卡','学校南门新开的那家麻辣烫绝了！10元管饱，还有免费小米辣🌶️ 坐标分享',9,3,'2025-12-25 08:45:00','',198,0),(17,'听歌推荐！这个月单曲循环榜','1. 周杰伦新歌 2. 林俊杰《交换余生》 3. Taylor Swift新专辑，来交换歌单吧~',7,3,'2025-12-24 02:20:00','',156,0),(18,'周末去哪玩？徐州周边一日游推荐','云龙湖、彭祖园还是去淮海战役纪念馆？求投票！',8,3,'2025-12-24 05:15:00',NULL,129,0),(19,'宿舍断网了怎么办？紧急求助！','晚上突然没网，打游戏卡成PPT，谁有校园网故障报修电话？',6,4,'2025-09-28 10:30:00',NULL,94,0),(20,'失物招领：教学楼捡到校园卡','在A区教学楼3楼捡到一张校园卡，姓名“李晓明”，失主速来认领！',3,4,'2025-08-29 00:55:00','',171,0),(21,'社团招新！编程爱好者协会欢迎你','每周五晚上机房活动，一起刷LeetCode、做项目，欢迎大一新生加入！',5,4,'2025-12-25 07:10:00','',236,0),(22,'图书馆自习室位置推荐','三楼靠窗位置超安静，光线好，推荐！避开一楼太吵了',7,4,'2025-12-24 03:40:00',NULL,156,0),(23,'校园快递代取有偿服务','每天固定时间去菜鸟驿站，可代取顺丰/京东，5元一次，私信联系',3,4,'2025-07-01 06:25:00',NULL,112,0),(24,'宿舍卫生检查要来了，求救！','我们宿舍太乱了，有没有速通打扫技巧？明天就检查😱',8,4,'2025-11-02 01:50:00',NULL,145,0),(92,'期末复习求组队！一起刷高数题','这学期高数太难了，有没有同学想一起复习的？可以约图书馆或者线上视频一起学习，互相监督！',3,2,'2025-12-20 11:30:00',NULL,131,0),(93,'英语六级备考经验分享','刚考完六级，感觉这次发挥不错！分享一下我的备考计划：每天坚持背50个单词+每周两套真题+听力精听训练',5,2,'2025-12-18 06:20:00',NULL,90,0),(94,'专业课笔记交换贴','我有计科、软工、网工等专业课的详细笔记，想交换其他专业的笔记，互相学习！',7,2,'2025-12-22 02:45:00',NULL,157,0),(95,'考研自习室求推荐','准备考研了，学校附近哪家自习室环境好、价格实惠？求学长学姐推荐！',9,2,'2025-12-25 08:30:00',NULL,211,0),(96,'期末考试时间安排表','各学院期末考试时间已经出来了，大家要注意查看教务系统，合理安排复习时间哦！',1,2,'2025-12-15 01:00:00',NULL,312,0),(97,'Python数据分析项目求合作','我有一个电商数据分析的项目想法，需要会Python、pandas、matplotlib的同学一起合作，有想法的私聊！',6,1,'2025-12-19 05:45:00',NULL,145,0),(98,'Java课设求指导','Java课程设计要做一个小型管理系统，有没有大佬能给点思路或者代码参考？感激不尽！',8,1,'2025-12-21 07:20:00',NULL,178,0),(99,'宿舍WiFi信号差怎么解决？','我们宿舍在六楼角落，WiFi信号特别差，有什么好的解决办法吗？求技术大神指点！',4,1,'2025-12-23 12:10:00',NULL,94,0),(100,'推荐几款好用的编程软件','VSCode、IntelliJ IDEA、PyCharm都用过了，还有没有其他好用的编程工具推荐？',10,1,'2025-12-24 03:25:00',NULL,167,0),(101,'GitHub学生认证攻略','刚成功申请了GitHub学生包，分享一下申请流程和注意事项，有需要的同学可以参考！',2,1,'2025-12-24 06:50:00',NULL,232,0),(102,'学校食堂美食推荐榜','三食堂二楼的麻辣香锅yyds！二食堂早餐的煎饼果子也不错，大家还有什么推荐？',5,4,'2025-12-17 04:30:00',NULL,289,0),(103,'宿舍文化节活动报名','下个月要举办宿舍文化节啦！有才艺表演、装饰比赛等活动，想参加的宿舍快来报名！',1,4,'2025-12-10 02:00:00',NULL,157,0),(104,'校园卡丢了怎么办？','今天在图书馆把校园卡弄丢了，应该去哪里补办？需要什么材料？急！',3,4,'2025-09-28 08:45:00',NULL,87,0),(105,'体育选课求建议','羽毛球、篮球、乒乓球哪个比较好过？有没有推荐的老师和上课时间？',7,4,'2025-08-29 01:20:00',NULL,141,0),(106,'图书馆占座现象严重','最近图书馆占座现象越来越严重了，有的座位一整天都没人来，大家有什么好的建议吗？',9,4,'2025-11-30 06:15:00',NULL,198,0),(107,'周末电影推荐','这周打算在宿舍看电影，有没有什么好看的电影推荐？最近有什么新上映的好片？',4,3,'2025-12-16 11:40:00',NULL,167,0),(108,'游戏开黑群招人','王者荣耀、吃鸡、原神都有玩，建了个开黑群，想一起玩的同学可以加群，段位不限！',6,3,'2025-12-22 13:30:00',NULL,245,0),(109,'学校周边KTV哪家好？','这周末想和室友去唱歌，学校附近哪家KTV音质好、价格实惠？求推荐！',8,3,'2025-12-24 10:15:00',NULL,123,0),(110,'有没有喜欢打篮球的？','想组建一个篮球爱好者群，平时可以约球，周末也可以打比赛，有兴趣的同学留言！',10,3,'2025-12-25 08:50:00',NULL,196,0),(111,'分享几个校园拍照打卡点','图书馆后面的银杏林、教学楼天台、操场跑道都很出片！还有哪些地方适合拍照？',2,3,'2025-12-25 07:30:00',NULL,276,0),(112,'校园论坛使用规范','欢迎各位同学使用校园论坛！请大家文明发言，遵守网络礼仪，共同维护良好的交流环境。',1,1,'2025-12-01 00:00:00',NULL,456,0),(113,'网络安全知识普及','<html>\n  <head>\n    \n  </head>\n  <body>\n    &#36817;&#26399;&#32593;&#32476;&#35784;&#39575;&#26696;&#20214;&#22686;&#22810;&#65292;&#25552;&#37266;&#21516;&#23398;&#20204;&#27880;&#24847;&#20445;&#25252;&#20010;&#20154;&#20449;&#24687;&#65292;&#35880;&#38450;&#32593;&#32476;&#35784;&#39575;&#65281;\n  </body>\n</html>',1,1,'2025-12-05 01:00:00','',390,0),(114,'期末考试诚信倡议','<html>\n  <head>\n    \n  </head>\n  <body>\n    &#35802;&#20449;&#32771;&#35797;&#65292;&#20174;&#25105;&#20570;&#36215;&#65281;&#24076;&#26395;&#22823;&#23478;&#35748;&#30495;&#22797;&#20064;&#65292;&#20973;&#30495;&#25165;&#23454;&#23398;&#21462;&#24471;&#22909;&#25104;&#32489;&#12290;\n  </body>\n</html>',1,2,'2025-12-10 02:00:00','',425,0),(115,'寒假社会实践通知','寒假社会实践报名开始啦！有意向参加的同学请关注学院通知，及时报名。',1,4,'2025-12-15 06:00:00',NULL,367,0),(116,'大家好，我是新人','<html>\r\n  <head>\r\n    \r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <b><font size=\"6\">&#22823;&#23478;&#22909;</font> </b>\r\n    </p>\r\n    <p style=\"margin-top: 0\">\r\n      &#25105;&#26159;&#21018;&#26469;&#39057;&#36947;&#30340;&#26032;&#20154;&#65292;&#35831;&#22810;&#22810;&#20851;&#29031;\r\n    </p>\r\n  </body>\r\n</html>',11,2,'2025-12-25 15:38:11','',7,1),(117,'12','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <i>asd\r\n</i>    </p>\r\n    <p style=\"margin-top: 0\">\r\n      <i><font color=\"#ff0000\">asd</font></i>\r\n    </p>\r\n  </body>\r\n</html>',11,1,'2025-12-25 15:44:46','',1,0),(118,'俺来测试一下，俺是丁士程','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      hello&#65292;&#36825;&#26159;&#19968;&#20010;&#27979;&#35797;\r\n    </p>\r\n  </body>\r\n</html>',11,2,'2025-12-24 16:19:29','C:\\Users\\dsc17\\Pictures\\Screenshots\\屏幕截图 2025-12-06 225316.png',2,0),(119,'[置顶] 大家好，我是你们的管理员','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      &#25105;&#26159;&#26032;&#26469;&#30340;&#31649;&#29702;&#21592;&#65292;&#35831;&#22810;&#22810;&#20851;&#29031;&#65292;&#35874;&#35874;&#22823;&#23478;&#65281;\r\n    </p>\r\n  </body>\r\n</html>',1,1,'2025-12-25 16:37:58','',0,0),(120,'[公告] 这是一个公告帖子测试','<div style=\'background-color:#f0f8ff; padding:10px; border-left:4px solid #007bff;\'><html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <b><i><font color=\"#ff0000\" size=\"6\">&#27979;&#35797;</font></i></b>\r\n    </p>\r\n  </body>\r\n</html></div>',1,2,'2025-12-25 16:38:28','',0,0),(121,'测试一下帖子','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <b>&#27979;&#35797;\r\n</b>    </p>\r\n    <p style=\"margin-top: 0\">\r\n      <b>\r\n</b>    </p>\r\n    <p style=\"margin-top: 0\">\r\n      <i>&#27979;&#35797;</i>\r\n    </p>\r\n    <p style=\"margin-top: 0\">\r\n      \r\n    </p>\r\n    <p style=\"margin-top: 0\">\r\n      <font color=\"#ff0000\">&#27979;&#35797;\r\n</font>    </p>\r\n    <p style=\"margin-top: 0\">\r\n      <font color=\"#ff0000\">\r\n</font>    </p>\r\n    <p style=\"margin-top: 0\">\r\n      <font size=\"6\">&#27979;&#35797;</font>\r\n    </p>\r\n  </body>\r\n</html>',11,3,'2025-12-26 00:21:30','C:\\Users\\dsc17\\Pictures\\Screenshots\\屏幕截图 2025-12-06 225316.png',1,0);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `replies`
--

DROP TABLE IF EXISTS `replies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `replies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `replies_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `replies_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replies`
--

LOCK TABLES `replies` WRITE;
/*!40000 ALTER TABLE `replies` DISABLE KEYS */;
INSERT INTO `replies` VALUES (139,'wowowow',110,11,'2025-12-25 15:16:50'),(140,'好厉害',118,11,'2025-12-25 16:19:47'),(141,'我也不知道欸',95,1,'2025-12-25 16:39:16'),(142,'没用的家伙',95,11,'2025-12-26 00:20:08'),(143,'选王老师',105,11,'2025-12-26 00:20:44');
/*!40000 ALTER TABLE `replies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'user',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','123456','admin','123@163.com'),(2,'zhangsan','1234','moderator','zhangsan@student.edu.cn'),(3,'lisi','1234','user','lisi@student.edu.cn'),(4,'wangwu','1234','user','wangwu@student.edu.cn'),(5,'xiaoming','1234','moderator','xiaoming@student.edu.cn'),(6,'xiaowang','1234','user','xiaowang@student.edu.cn'),(7,'zhangwei','1234','user','zhangwei@student.edu.cn'),(8,'sunli','1234','user','sunli@student.edu.cn'),(9,'linjie','1234','user','linjie@student.edu.cn'),(10,'gaoyue','1234','user','gaoyue@student.edu.cn'),(11,'dsc','123','user','123'),(12,'student01','123456','user','student01@student.edu.cn'),(13,'student02','123456','user','student02@student.edu.cn'),(14,'student03','123456','user','student03@student.edu.cn'),(15,'student04','123456','user','student04@student.edu.cn');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'campus_forum'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-26  8:30:29
