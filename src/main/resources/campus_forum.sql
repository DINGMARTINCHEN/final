-- MySQL dump 10.13  Distrib 9.0.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: campus_forum
-- ------------------------------------------------------
-- Server version	9.0.0

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
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
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
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `user_id` int DEFAULT NULL,
  `board_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `attachment` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `views` int DEFAULT '0',
  `pinned` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `board_id` (`board_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`board_id`) REFERENCES `boards` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'考试技巧','认真复习',1,1,'2025-12-14 12:29:31','',0,0),(2,'Java Swing布局问题求助！GridBagLayout用不好','大佬们，谁能教教GridBagLayout怎么精准控制组件位置？我做界面总是乱七八糟😭 附上我的代码截图求指点！',8,1,'2025-12-19 13:37:42','layout_error.png',2,0),(3,'MySQL连接总是报“No suitable driver found”怎么办？','用JDBC连接本地MySQL，一直报这个错，已经加了mysql-connector-jar了还是不行，求解决方案！',9,1,'2025-12-19 13:37:42',NULL,1,0),(4,'IntelliJ IDEA调试技巧分享','分享几个超实用的调试技巧：条件断点、变量观看、Evaluate Expression，神器！新手必看~',10,1,'2025-12-19 13:37:42','debug_tips.jpg',2,0),(5,'Git分支管理最佳实践','多人协作时怎么避免冲突？推荐rebase还是merge？来讨论讨论',10,1,'2025-12-19 13:37:42',NULL,2,0),(6,'求推荐好用的代码格式化插件','VSCode有Prettier，IDEA有什么类似的高效插件？',5,1,'2025-12-19 13:37:42',NULL,1,0),(7,'Python爬虫被反爬怎么办？','用requests爬某网站老是被封IP，有没有大佬教教绕过方法？',6,1,'2025-12-19 13:37:42',NULL,0,0),(8,'期末复习资料分享合集（软件工程+数据结构）','整理了王老师的软件工程PPT和课后答案，还有数据结构思维导图，私信我发网盘链接！互助学习✊',8,2,'2025-12-19 13:37:53','review_materials.zip',0,0),(9,'高数挂科的举手🙋‍♂️ 有没有补考经验分享？','上次高数没过，这次求救！谁有重点章节总结或历年真题？',7,2,'2025-12-19 13:37:53',NULL,0,0),(10,'英语四级过线技巧','分享我的备考经验：刷真题+背作文模板+每天听力30分钟，裸考420过！',6,2,'2025-12-19 13:37:53','cet4_tips.pdf',0,0),(11,'考研党集合！报哪个学校哪个专业？','23届的学长学姐们，来交流交流经验吧，我纠结软工还是人工智能',2,2,'2025-12-19 13:37:53',NULL,0,0),(12,'如何高效记笔记？推荐工具和方法','我用Notion+手写板，超级好用！欢迎分享你们的笔记神器',3,2,'2025-12-19 13:37:53','notion_template.png',0,0),(13,'线性代数证明题太难了求方法','总觉得证明题无从下手，有没有大佬总结的解题套路？',1,2,'2025-12-19 13:37:53',NULL,2,0),(14,'最近好看的剧推荐！','刷完《隐秘的角落》太烧脑了！还有《庆余年2》超期待，大家最近在追什么？',4,3,'2025-12-19 13:38:04','drama_poster.jpg',0,0),(15,'王者荣耀新赛季上分技巧','新赛季冲传奇了，有没有大佬带带？中路法师玩家求组队🎮',5,3,'2025-12-19 13:38:04',NULL,0,0),(16,'校园附近好吃的美食打卡','学校南门新开的那家麻辣烫绝了！10元管饱，还有免费小米辣🌶️ 坐标分享',9,3,'2025-12-19 13:38:04','food_photo.jpg',0,0),(17,'听歌推荐！这个月单曲循环榜','1. 周杰伦新歌 2. 林俊杰《交换余生》 3. Taylor Swift新专辑，来交换歌单吧~',7,3,'2025-12-19 13:38:04','playlist.png',0,0),(18,'周末去哪玩？徐州周边一日游推荐','云龙湖、彭祖园还是去淮海战役纪念馆？求投票！',8,3,'2025-12-19 13:38:04',NULL,0,0),(19,'宿舍断网了怎么办？紧急求助！','晚上突然没网，打游戏卡成PPT，谁有校园网故障报修电话？',6,4,'2025-12-19 13:38:20',NULL,0,0),(20,'失物招领：教学楼捡到校园卡','在A区教学楼3楼捡到一张校园卡，姓名“李晓明”，失主速来认领！',3,4,'2025-12-19 13:38:20','campus_card.jpg',0,0),(21,'社团招新！编程爱好者协会欢迎你','每周五晚上机房活动，一起刷LeetCode、做项目，欢迎大一新生加入！',5,4,'2025-12-19 13:38:20','club_poster.png',0,0),(22,'图书馆自习室位置推荐','三楼靠窗位置超安静，光线好，推荐！避开一楼太吵了',7,4,'2025-12-19 13:38:20',NULL,0,0),(23,'校园快递代取有偿服务','每天固定时间去菜鸟驿站，可代取顺丰/京东，5元一次，私信联系',3,4,'2025-12-19 13:38:20',NULL,0,0),(24,'宿舍卫生检查要来了，求救！','我们宿舍太乱了，有没有速通打扫技巧？明天就检查😱',8,4,'2025-12-19 13:38:20',NULL,0,0),(30,'过程考核3','<html>\r\n  <head>\r\n    \r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      &#27714;&#36807;&#31243;&#32771;&#26680;&#19977;&#30340;&#35774;&#35745;&#31867;&#22270;\r\n    </p>\r\n  </body>\r\n</html>',4,1,'2025-12-21 12:42:52','C:\\Users\\阮江宇\\OneDrive\\图片\\过程考核3设计类图.png',12,0),(31,'我爱学习','<html>\r\n  <head>\r\n    \r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <font size=\"6\" color=\"#ff0000\"><b><i>&#25105;&#29233;&#23398;&#20064;</i></b></font>\r\n    </p>\r\n  </body>\r\n</html>',9,1,'2025-12-21 12:52:11','C:\\Users\\阮江宇\\OneDrive\\图片\\分析类图-家电能耗异常波动系统.png',24,0),(33,'下周的天气如何','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      &#19979;&#21608;&#30340;&#22825;&#27668;&#22914;&#20309;&#65292;&#36866;&#21512;&#20986;&#21435;&#26053;&#28216;&#21527;&#65311;\r\n    </p>\r\n  </body>\r\n</html>',7,1,'2025-12-22 14:40:21','',16,0),(35,'求高数复习资料','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <b><font size=\"6\">&#25105;&#24819;&#35201;&#24448;&#24180;&#30340;&#39640;&#25968;&#35797;&#21367;</font></b>\r\n    </p>\r\n  </body>\r\n</html>',8,1,'2025-12-23 14:12:52','',5,0),(36,'版块要求','<html>\r\n  <head>\r\n    \r\n  </head>\r\n  <body style=\"padding-top: 15px; padding-right: 15px; padding-bottom: 15px; padding-left: 15px\">\r\n    <p style=\"margin-top: 0\">\r\n      <font color=\"#ff0000\">&#26412;&#29256;&#22359;&#20026;&#23398;&#20064;&#20132;&#27969;&#20351;&#29992;&#35831;&#22823;&#23478;&#21457;&#24067;&#26377;&#31649;&#23398;&#20064;&#30340;&#24086;&#23376;&#35874;&#35874;</font>\r\n    </p>\r\n  </body>\r\n</html>',1,1,'2025-12-23 14:17:28','',15,1),(38,'[公告] 欢迎大家踊跃讨论','<div style=\'background-color:#f0f8ff; padding:10px; border-left:4px solid #007bff;\'><html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      &#27426;&#36814;&#22823;&#23478;&#36362;&#36291;&#21457;&#24086;\r\n    </p>\r\n  </body>\r\n</html></div>',1,1,'2025-12-23 15:06:10','',7,1),(39,'[置顶] 欢迎大家分享生活','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \r\n    </p>\r\n  </body>\r\n</html>',1,2,'2025-12-23 17:55:37','',0,0),(40,'[置顶] 欢迎大家讨论社团生活','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \r\n    </p>\r\n  </body>\r\n</html>',1,3,'2025-12-23 17:57:04','',0,0),(41,'网络实验','<html>\r\n  <head>\r\n    \r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      &#27714;&#35813;&#32593;&#32476;&#23454;&#39564;&#25351;&#23548;&#65288;&#22312;&#38468;&#20214;&#24403;&#20013;&#65289;\r\n    </p>\r\n  </body>\r\n</html>',1,1,'2025-12-23 22:51:07','C:\\Users\\阮江宇\\OneDrive\\桌面\\homework\\网络实验\\实验指导  实验一（1014）.pdf',2,0),(42,'111','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      11111\r\n    </p>\r\n  </body>\r\n</html>',3,1,'2025-12-23 22:53:47','',5,0),(43,'今天记号','<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      <font size=\"6\">&#20170;&#22825;&#20960;&#21495;</font>\r\n    </p>\r\n  </body>\r\n</html>',1,1,'2025-12-24 00:02:35','C:\\Users\\阮江宇\\OneDrive\\桌面\\homework\\2025年下半年英语四级笔试准考证(321281200402226979).pdf',1,0);
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
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `replies_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `replies_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replies`
--

LOCK TABLES `replies` WRITE;
/*!40000 ALTER TABLE `replies` DISABLE KEYS */;
INSERT INTO `replies` VALUES (1,'下周天气挺好的非常适合旅游',33,2,'2025-12-22 15:09:26'),(2,'收到',36,4,'2025-12-23 18:01:39'),(3,'好的',35,4,'2025-12-23 18:02:10'),(4,'收到',36,3,'2025-12-23 22:55:11'),(5,'收到',36,10,'2025-12-23 22:56:00');
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
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'user',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','123456','admin','123@163.com'),(2,'zhangsan','1234','moderator',NULL),(3,'lisi','1234','student',NULL),(4,'wangwu','1234','manage','<null>'),(5,'xiaoming','1234','admin','123@163.com'),(6,'xiaowang','1234','student',NULL),(7,'zhangwei','1234','student',NULL),(8,'sunli','1234','student',NULL),(9,'linjie','1234','student',NULL),(10,'gaoyue','1234','student','456@163.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- 创建点赞表
CREATE TABLE IF NOT EXISTS post_likes (
      id INT NOT NULL AUTO_INCREMENT,
      user_id INT NOT NULL,
      post_id INT NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      PRIMARY KEY (id),
      UNIQUE KEY unique_like (user_id, post_id),  -- 防止重复点赞
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 添加索引优化查询
CREATE INDEX idx_post_likes_post_id ON post_likes(post_id);
CREATE INDEX idx_post_likes_user_id ON post_likes(user_id);

-- Dump completed on 2025-12-24 16:55:00
