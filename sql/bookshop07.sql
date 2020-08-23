/*
 Navicat Premium Data Transfer

 Source Server         : MySQL57
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : bookshop07

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 23/08/2020 12:32:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 0);

-- ----------------------------
-- Table structure for askbook
-- ----------------------------
DROP TABLE IF EXISTS `askbook`;
CREATE TABLE `askbook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `press` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `user_id` int(2) NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of askbook
-- ----------------------------
INSERT INTO `askbook` VALUES (8, 'http://gofastdfs.yancq.top:10342/group1/image/33.jpg', '剑来', '烽火戏诸侯', '百花文艺出版社', '仙侠好书', 2, '2020-04-13 21:57:43', 0);
INSERT INTO `askbook` VALUES (9, 'http://gofastdfs.yancq.top:10342/group1/image/24.jpg', '自在独行', '贾平凹', '长江文艺出版社', '孤独的思考中学会更热爱世界', 3, '2020-04-13 22:36:18', 0);
INSERT INTO `askbook` VALUES (10, 'http://gofastdfs.yancq.top:10342/group1/image/25.jpg', '我在等风，也在等你', '白凝', '现代出版社', '听爱情“朗诵者”的肺腑之言，邂逅更好的爱情。', 3, '2020-04-13 22:38:31', 0);
INSERT INTO `askbook` VALUES (11, 'http://gofastdfs.yancq.top:10342/group1/image/26.jpg', '我把活着欢喜过了', '梁秋实', '江苏凤凰文艺出版社', '活只有一种英雄主义，那就是认清生活的真相之后依然热爱生活。', 2, '2020-04-13 22:39:42', 0);
INSERT INTO `askbook` VALUES (12, 'http://gofastdfs.yancq.top:10342/group1/image/27.jpg', '厚积落叶听雨声', '朱光潜', '江苏文艺出版社', '始终温和地坐在岁月里，听雨落，听风起', 3, '2020-04-13 22:40:46', 0);
INSERT INTO `askbook` VALUES (13, 'http://gofastdfs.yancq.top:10342/group1/image/28.jpg', '边城', '沈从文', '北京十月文艺出版社', '中国现代文学牧歌传说中的佳作', 1, '2020-04-13 22:42:10', 0);
INSERT INTO `askbook` VALUES (14, 'http://gofastdfs.yancq.top:10342/group1/image/29.jpg', '湘行散记', '沈从文', '江苏人民出版社', '重寻湘西世界的自由朴野之美！', 1, '2020-04-13 22:43:38', 0);
INSERT INTO `askbook` VALUES (15, 'http://gofastdfs.yancq.top:10342/group1/image/30.jpg', '围城', '钱钟书', '人民文学出版社', '用幽默度过艰难时世，以智趣造就岁月风华。', 3, '2020-04-13 22:45:13', 0);
INSERT INTO `askbook` VALUES (16, 'http://gofastdfs.yancq.top:10342/group1/image/31.jpg', '许三观卖血记', '余华', '作家出版社', '人在面对厄运时求生的欲望。', 2, '2020-04-13 22:46:43', 0);
INSERT INTO `askbook` VALUES (17, 'http://gofastdfs.yancq.top:10342/group1/image/34.jpg', '市场逻辑', '张维迎', '西北大学出版社', '看透市场的底层逻辑，读懂中国40年经济腾飞的历史与未来', 5, '2020-04-22 21:27:17', 0);
INSERT INTO `askbook` VALUES (18, 'http://gofastdfs.yancq.top:10342/group1/image/21.jpg', '皮囊', '烽火戏诸侯', '人民文学出版社', '科幻大作，值得一看', 1, '2020-05-20 15:41:03', 1);
INSERT INTO `askbook` VALUES (19, 'http://gofastdfs.yancq.top:10342/group1/image/21.jpg', '雪中悍刀行', '烽火戏诸侯', '人民文学出版社', '科幻大作，值得一看', 1, '2020-05-20 15:52:18', 1);
INSERT INTO `askbook` VALUES (20, 'http://gofastdfs.yancq.top:10342/group1/image/23.jpg', '皮囊', '蔡崇达', '人民文学出版社', '科幻大作，值得一看', 1, '2020-05-20 15:58:17', 1);
INSERT INTO `askbook` VALUES (21, 'http://gofastdfs.yancq.top:10342/group1/image/23.jpg', '皮囊', '蔡崇达', '人民文学出版社', '科幻大作，值得一看', 1, '2020-05-20 16:08:16', 1);
INSERT INTO `askbook` VALUES (22, 'http://gofastdfs.yancq.top:10342/group1/image/40.jpg', '慢煮生活', '汪曾祺', '江苏凤凰文艺出版社', '无论何时都认真生活', 1, '2020-05-22 23:20:19', 0);
INSERT INTO `askbook` VALUES (23, 'http://gofastdfs.yancq.top:10342/group1/image/41.jpg', '围城', '钱钟书', '人民出版社', '好书', 1, '2020-05-24 17:04:48', 1);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cid` int(10) NULL DEFAULT NULL,
  `book_type` int(1) NULL DEFAULT NULL COMMENT '0:未上架；1：已上架；2：审核未通过；3：已售',
  `price` double(10, 2) NULL DEFAULT NULL,
  `original_price` double(10, 2) NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `press` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `degree` double(2, 1) NULL DEFAULT NULL,
  `publish_date` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_id`(`id`) USING BTREE,
  INDEX `fk_book_category`(`cid`) USING BTREE,
  INDEX `fk_book_user`(`uid`) USING BTREE,
  INDEX `index_bookname`(`name`) USING BTREE,
  INDEX `index_cid`(`cid`) USING BTREE,
  INDEX `index_uid`(`uid`) USING BTREE,
  CONSTRAINT `fk_book_category` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_book_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Java编程思想', 2, 1, 52.00, 108.00, 1, 'Bruce Eckel', '机械工业出版社', '第4版', 9.5, '2016-11', '四大名著啊！能不看吗', '2018-04-01 06:22:46', 0);
INSERT INTO `book` VALUES (2, '明朝那些事儿（全9册）', 2, 1, 99.00, 178.00, 1, '当年明月', '北京联合出版公司', '平装', 8.5, '2017-05', '没买多久，急用钱，低价甩', '2018-04-06 07:23:15', 0);
INSERT INTO `book` VALUES (3, '恶意', 1, 3, 9.90, 28.00, 2, '东野圭吾', '南海出版公司', '平装', 9.0, '2008-09', '书架太多书了 出给东野圭吾书迷', '2018-04-06 08:24:25', 0);
INSERT INTO `book` VALUES (4, '白夜行', 1, 1, 12.00, 49.00, 2, '东野圭吾', '南海出版公司', '平装', 8.0, '2008-09', '书架太多书了 出给东野圭吾书迷', '2018-04-12 08:30:36', 0);
INSERT INTO `book` VALUES (5, '简爱', 1, 3, 13.80, 26.00, 2, '夏洛特·勃朗特', '世界图书出版公司', '平装', 8.0, '2011-10', '不适合我看', '2018-04-12 08:31:41', 0);
INSERT INTO `book` VALUES (6, '芒果街上的小屋', 1, 3, 10.80, 25.00, 2, '桑德拉·希斯内罗丝、潘帕', '译林出版社', '平装', 7.0, '2006-06', '买来垫泡面了', '2018-04-12 08:32:46', 0);
INSERT INTO `book` VALUES (7, '活着', 1, 1, 12.10, 26.00, 2, '余华 ', '上海文艺出版社', '平装', 8.0, '2004-05', '好书 真的', '2018-04-12 08:24:17', 0);
INSERT INTO `book` VALUES (8, '羊脂球', 1, 1, 11.20, 25.00, 2, '莫泊桑', ' 北京燕山出版社', '平装', 6.0, '2005-07', '莫泊桑 著 / 北京燕山出版社 / 2005-07 / 平装', '2018-04-12 08:25:24', 0);
INSERT INTO `book` VALUES (9, 'Spring实战', 7, 1, 33.60, 79.00, 3, 'Craig Walls', '人民邮电出版社', '第4版', 9.0, '2016-04', 'Java必看经典书籍！', '2018-04-12 08:31:29', 0);
INSERT INTO `book` VALUES (10, 'JavaScript高级程序设计', 7, 1, 31.00, 79.00, 2, 'Nicholas C.Zakas', '人民邮电出版社', '第3版', 8.0, '2012-03', 'Nicholas C.Zakas 著 / 人民邮电出版社 / 2012-03 / 第3版', '2018-04-12 08:36:34', 0);
INSERT INTO `book` VALUES (11, '算法', 7, 1, 28.00, 89.90, 3, 'Robert Sedgewick', '人民邮电出版社', '第4版', 8.6, '2012-10', '算法书啃不下去了 我已经放弃了', '2018-04-12 08:36:41', 0);
INSERT INTO `book` VALUES (12, 'Python基础教程', 7, 1, 34.00, 49.00, 2, 'Magnus Lie Hetland', '人民邮电出版社', '第3版', 7.5, '2018-02', 'python', '2018-04-12 08:37:49', 0);
INSERT INTO `book` VALUES (13, '史记', 2, 1, 42.00, 99.00, 2, '司马迁', '光明日报出版社', '精装', 8.5, '2015-04', '司马迁 著 / 光明日报出版社 / 2015-04 / 精装', '2018-04-12 08:38:55', 0);
INSERT INTO `book` VALUES (14, '朱镕基答记者问', 3, 1, 9.00, 59.00, 2, '《朱镕基答记者问》编辑组', '人民出版社', '平装', 5.0, '2009-08', '《朱镕基答记者问》编辑组 著 / 人民出版社 / 2009-08 / 平装', '2018-04-12 08:40:01', 0);
INSERT INTO `book` VALUES (15, '邓小平时代', 3, 1, 32.00, 40.00, 3, '傅高义', '生活·读书·新知三联书店', '平装', 8.0, '2013-01', '傅高义 著 / 生活·读书·新知三联书店 / 2013-01 / 平装', '2018-04-12 08:42:11', 0);
INSERT INTO `book` VALUES (16, '天才在左 疯子在右', 4, 1, 17.80, 36.00, 2, '高铭', '武汉大学出版社', '平装', 8.0, '2010-02', '高铭 著 / 武汉大学出版社 / 2010-02 / 平装', '2018-04-12 08:42:24', 0);
INSERT INTO `book` VALUES (17, '蔡康永的说话之道', 4, 1, 8.80, 29.00, 2, '蔡康永', '沈阳出版社', '平装', 7.4, '2010-10', '蔡康永 著 / 沈阳出版社 / 2010-10 / 平装', '2018-04-12 08:44:29', 0);
INSERT INTO `book` VALUES (18, '货币战争', 5, 1, 12.00, 34.00, 2, '宋鸿兵', '中信出版社', '平装', 6.0, '2007-06', '宋鸿兵 著 / 中信出版社 / 2007-06 / 平装', '2018-04-12 08:46:34', 0);
INSERT INTO `book` VALUES (19, '菊与刀', 5, 1, 5.00, 19.00, 2, '鲁思·本尼迪克特', '商务印书馆', '平装', 7.0, '1990-06', '鲁思·本尼迪克特 著 / 商务印书馆 / 1990-06 / 平装', '2018-04-12 08:47:40', 0);
INSERT INTO `book` VALUES (20, '科比：黄金年代', 6, 1, 22.00, 49.00, 2, '张佳玮', '金城出版社', '平装', 7.0, '2016-06', '张佳玮 著 / 金城出版社 / 2016-06 / 平装', '2018-04-12 08:44:46', 0);
INSERT INTO `book` VALUES (21, '梅者如西', 6, 1, 12.00, 34.00, 2, '冯逸明', '北京时代华文书局', '平装', 7.0, '2017-07', '冯逸明 著 / 北京时代华文书局 / 2017-07 / 平装', '2018-04-12 08:49:52', 0);
INSERT INTO `book` VALUES (62, '精通Spring4.x', 7, 1, 30.00, 79.00, 2, '陈雄华', '电子工业出版社', '平装', 9.9, '2016-11', '保证正版', '2018-04-12 08:26:16', 0);
INSERT INTO `book` VALUES (64, '我们仨', 2, 1, 10.00, 18.80, 3, '杨绛', '生活·读书·新知三联书店', '平装', 9.0, '2003-07', '杨绛的书', '2018-04-12 08:26:21', 0);
INSERT INTO `book` VALUES (65, '史蒂夫·乔布斯传', 2, 1, 20.00, 68.00, 3, '沃尔特·艾萨克森', '中信出版社', '平装', 8.0, '2011-10', '乔布斯大神', '2018-04-12 08:26:24', 0);
INSERT INTO `book` VALUES (66, '万历十五年', 2, 1, 4.00, 12.80, 3, '黄仁宇', '生活·读书·新知三联书店', '平装', 4.0, '1997-05', '老书了', '2018-04-12 08:26:27', 0);
INSERT INTO `book` VALUES (67, '毛泽东选集 第一卷', 3, 1, 10.00, 40.00, 3, '毛泽东', '人民出版社', '平装', 6.0, '2003-07', '毛主席', '2018-04-12 08:26:31', 0);
INSERT INTO `book` VALUES (68, '孙子兵法', 3, 1, 10.00, 20.00, 3, '孙武', '上海古籍出版社', '平装', 7.0, '2006-07', '军事爱好者可入', '2018-04-12 08:26:33', 0);
INSERT INTO `book` VALUES (69, '三十六计', 3, 1, 2.00, 6.00, 3, '赵立', '吉林文史出版社', '平装', 4.0, '2006-05', '用来压泡面了', '2018-04-12 08:26:36', 0);
INSERT INTO `book` VALUES (70, '金刚经', 4, 1, 6.00, 16.00, 3, '鸠摩罗什', '吉林文史出版社', '平装', 9.0, '2009-01', '好！', '2018-04-12 08:26:40', 0);
INSERT INTO `book` VALUES (71, '断舍离', 4, 1, 10.00, 32.00, 3, '山下英子', '广西科学技术出版社', '平装', 9.0, '2014-05', '基本全新', '2018-04-12 08:26:42', 0);
INSERT INTO `book` VALUES (72, '人性的弱点全集', 4, 1, 10.00, 25.00, 3, '戴尔·卡内基', '中国发展出版社', '平装', 8.0, '2008-01', '好书啊', '2018-04-12 08:26:45', 0);
INSERT INTO `book` VALUES (73, '人类简史：从动物到上帝', 5, 1, 24.00, 68.00, 3, '尤瓦尔·赫拉利', '中信出版社', '平装', 9.0, '2014-11', '毕业便宜甩', '2018-04-12 08:26:47', 0);
INSERT INTO `book` VALUES (74, '丑陋的中国人', 5, 1, 6.00, 22.00, 3, '柏杨', '古吴轩出版社', '平装', 7.0, '2006-02', '老书了', '2018-04-12 08:26:49', 0);
INSERT INTO `book` VALUES (75, '郎咸平说', 5, 1, 10.00, 32.00, 3, '郎咸平', '东方出版社', '平装', 6.0, '2010-09', '我也不知道我们的日子为什么这么难', '2018-04-12 08:26:52', 0);
INSERT INTO `book` VALUES (76, '孩子你慢慢来', 6, 3, 10.00, 28.00, 3, '龙应台', '生活·读书·新知三联书店', '平装', 9.0, '2009-12', '好书推荐', '2018-04-12 08:26:55', 0);
INSERT INTO `book` VALUES (77, '男人这东西', 6, 1, 8.00, 28.00, 3, '渡边淳一', '作家出版社', '平装', 7.0, '2010-03', '渡边大神的书', '2018-04-12 08:26:58', 0);
INSERT INTO `book` VALUES (78, '荒野生存', 6, 1, 19.00, 39.00, 3, '乔恩·克拉考尔', '中国人民大学出版社', '平装', 7.0, '2008-08', '已改编成同名电影', '2018-04-12 08:27:02', 0);
INSERT INTO `book` VALUES (79, '管理学', 8, 1, 40.00, 98.00, 3, '罗宾斯', '中国人民大学出版社', '平装', 9.0, '2004-04', '管理学考研必备啊！', '2018-04-12 08:27:05', 0);
INSERT INTO `book` VALUES (80, '剑桥雅思真题9', 8, 1, 5.00, 15.00, 2, '无', '科学出版社', '平装', 9.9, '2014-03', '全新没写过，已分手雅思', '2018-04-12 08:27:07', 0);
INSERT INTO `book` VALUES (81, '剑桥雅思11', 8, 1, 15.00, 8.00, 2, '无', '中国人民大学出版社', '平装', 9.9, '2015-09', '全新没写过的', '2018-04-12 08:27:10', 0);
INSERT INTO `book` VALUES (82, '顾家北手把手教你雅思写作', 8, 1, 20.00, 55.60, 3, '顾家北', '中国人民大学出版社', '平装', 8.0, '2017-09', '有一点点笔记', '2018-04-12 08:27:13', 0);
INSERT INTO `book` VALUES (83, '雅思王听力语料库', 8, 1, 16.00, 37.50, 3, '王陆', '中国人民大学出版社', '平装', 8.0, '2017-06', '接近全新啦', '2018-04-12 08:27:15', 0);
INSERT INTO `book` VALUES (84, '时间简史', 9, 1, 15.00, 45.00, 2, '史蒂芬·霍金', '湖南科学技术出版社', '平装', 8.0, '2015-04', '霍金大大啊', '2018-04-12 08:27:18', 0);
INSERT INTO `book` VALUES (85, '万物简史', 9, 1, 11.00, 36.80, 2, '比尔·布莱森', '接力出版社', '平装', 6.0, '2005-02', '很好的书', '2018-04-12 08:27:20', 0);
INSERT INTO `book` VALUES (86, '昆虫记', 9, 1, 9.00, 19.00, 2, '法布尔', '作家出版社', '平装', 2.0, '2000-06', '老书了', '2018-04-12 08:27:23', 0);
INSERT INTO `book` VALUES (87, '黄帝内经', 9, 1, 10.00, 30.00, 3, '王冰', '中医古籍出版社', '平装', 1.0, '2003-11', '好', '2018-04-12 08:27:25', 0);
INSERT INTO `book` VALUES (88, '本草纲目', 9, 1, 3.00, 19.00, 3, '李时珍', '北京出版社', '平装', 6.0, '2007-01', '有价值！', '2018-04-12 08:27:29', 0);
INSERT INTO `book` VALUES (89, '三体', 1, 1, 20.00, 56.80, 1, '刘慈欣', '人民文学出版社', '精装版', 9.0, '2020-01', '科幻大作，值得一看', '2020-04-12 15:19:30', 0);
INSERT INTO `book` VALUES (92, '雪中悍刀行', 1, 1, 20.00, 68.00, 1, '烽火戏诸侯', '人民文学出版社', '精装版', 9.0, '2020-01', '武侠好书', '2020-04-12 16:10:05', 0);
INSERT INTO `book` VALUES (93, '乖，摸摸头', 1, 1, 20.00, 56.80, 1, '大冰', '百花文艺出版社', '精装版', 9.0, '2020-01', '大冰诚意之作', '2020-04-13 14:17:09', 0);
INSERT INTO `book` VALUES (94, '同级生', 1, 1, 20.00, 56.80, 1, '东野圭吾', '北京十月文艺出版社', '精装版', 9.0, '2020-01', '日本作家东野圭吾短篇小说', '2020-04-13 14:20:55', 0);
INSERT INTO `book` VALUES (95, '悲剧人偶', 1, 3, 20.00, 56.80, 1, '东野圭吾', '北京十月文艺出版社', '精装版', 9.0, '2020-01', '悬疑小说', '2020-04-13 14:21:42', 0);
INSERT INTO `book` VALUES (96, '皮囊', 1, 1, 20.00, 46.90, 1, '蔡崇达', '天津人民出版社', '精装版', 9.0, '2020-01', '我们彼此相遇，卸下皮囊，以心相交', '2020-04-13 21:57:15', 0);
INSERT INTO `book` VALUES (98, '画家之眼', 2, 1, 35.60, 39.80, 5, '安德魯·路米斯', '北京联合出版公司', '精装版', 9.0, '2016-12', '带你看出不一样的世界。', '2020-04-23 16:18:02', 0);
INSERT INTO `book` VALUES (99, '我爱这热闹的生活', 1, 1, 20.00, 56.80, 5, '叶广芩', '江西人民出版社', '精装版', 9.0, '2020-04', '富有感染力的散文版《城南旧事》', '2020-04-23 16:20:34', 0);
INSERT INTO `book` VALUES (100, '秦俑两千年', 2, 1, 20.00, 56.80, 5, '爱德华 · 伯曼', '百花洲文艺出版社', '精装版', 9.0, '2020-02', '关于秦俑的一切想象、现实与未知！', '2020-04-23 16:22:23', 0);
INSERT INTO `book` VALUES (101, '妙手莲心', 9, 1, 20.00, 56.80, 5, '文泉杰', '中国中医药出版社', '精装版', 9.0, '2020-03', '科幻大作，值得一看', '2020-04-23 16:24:00', 0);
INSERT INTO `book` VALUES (102, '龙族', 1, 1, 20.00, 56.80, 1, '江南', '长江出版社', '精装版', 9.0, '2020-01', '青春文学', '2020-05-05 22:04:20', 0);
INSERT INTO `book` VALUES (103, '雪中悍刀行', 1, 0, 20.00, 56.80, 1, '烽火戏诸侯', '人民文学出版社', '精装版', 9.0, '2020-04', '武侠好书', '2020-05-20 15:23:01', 1);
INSERT INTO `book` VALUES (104, '围城', 1, 0, 20.00, 40.00, 1, '钱钟书', '人民文艺出版社', '精装版', 9.0, '2020-03', '读遍十方简', '2020-05-23 23:15:42', 0);

-- ----------------------------
-- Table structure for bookimage
-- ----------------------------
DROP TABLE IF EXISTS `bookimage`;
CREATE TABLE `bookimage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bookimage
-- ----------------------------
INSERT INTO `bookimage` VALUES (1, 4, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/1.jpg');
INSERT INTO `bookimage` VALUES (2, 11, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/10.jpg');
INSERT INTO `bookimage` VALUES (3, 12, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/11.jpg');
INSERT INTO `bookimage` VALUES (4, 2, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/12.jpg');
INSERT INTO `bookimage` VALUES (5, 13, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/13.jpg');
INSERT INTO `bookimage` VALUES (6, 14, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/14.jpg');
INSERT INTO `bookimage` VALUES (7, 15, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/15.jpg');
INSERT INTO `bookimage` VALUES (8, 16, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/16.jpg');
INSERT INTO `bookimage` VALUES (9, 17, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/17.jpg');
INSERT INTO `bookimage` VALUES (10, 18, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/18.jpg');
INSERT INTO `bookimage` VALUES (11, 19, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/19.jpg');
INSERT INTO `bookimage` VALUES (12, 5, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/2.jpg');
INSERT INTO `bookimage` VALUES (13, 20, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/20.jpg');
INSERT INTO `bookimage` VALUES (14, 21, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/21.jpg');
INSERT INTO `bookimage` VALUES (15, 6, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/3.jpg');
INSERT INTO `bookimage` VALUES (16, 7, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/4.jpg');
INSERT INTO `bookimage` VALUES (17, 8, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/5.jpg');
INSERT INTO `bookimage` VALUES (18, 62, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/58.jpg');
INSERT INTO `bookimage` VALUES (19, 1, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/6.jpg');
INSERT INTO `bookimage` VALUES (20, 64, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/60.jpg');
INSERT INTO `bookimage` VALUES (21, 65, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/61.jpg');
INSERT INTO `bookimage` VALUES (22, 66, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/62.jpg');
INSERT INTO `bookimage` VALUES (23, 67, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/63.jpg');
INSERT INTO `bookimage` VALUES (24, 68, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/64.jpg');
INSERT INTO `bookimage` VALUES (25, 69, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/65.jpg');
INSERT INTO `bookimage` VALUES (26, 70, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/66.jpg');
INSERT INTO `bookimage` VALUES (27, 71, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/67.jpg');
INSERT INTO `bookimage` VALUES (28, 72, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/68.jpg');
INSERT INTO `bookimage` VALUES (29, 73, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/69.jpg');
INSERT INTO `bookimage` VALUES (30, 3, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/7.jpg');
INSERT INTO `bookimage` VALUES (31, 74, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/70.jpg');
INSERT INTO `bookimage` VALUES (32, 75, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/71.jpg');
INSERT INTO `bookimage` VALUES (33, 76, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/72.jpg');
INSERT INTO `bookimage` VALUES (34, 77, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/73.jpg');
INSERT INTO `bookimage` VALUES (35, 78, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/74.jpg');
INSERT INTO `bookimage` VALUES (36, 79, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/75.jpg');
INSERT INTO `bookimage` VALUES (37, 80, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/76.jpg');
INSERT INTO `bookimage` VALUES (38, 81, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/77.jpg');
INSERT INTO `bookimage` VALUES (39, 82, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/78.jpg');
INSERT INTO `bookimage` VALUES (40, 83, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/79.jpg');
INSERT INTO `bookimage` VALUES (41, 9, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/8.jpg');
INSERT INTO `bookimage` VALUES (42, 84, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/80.jpg');
INSERT INTO `bookimage` VALUES (43, 85, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/81.jpg');
INSERT INTO `bookimage` VALUES (44, 86, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/82.jpg');
INSERT INTO `bookimage` VALUES (45, 87, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/83.jpg');
INSERT INTO `bookimage` VALUES (46, 88, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/84.jpg');
INSERT INTO `bookimage` VALUES (47, 10, 'http://bookshop.yancq.top:10340/bookshop/img/book-list/article/9.jpg');
INSERT INTO `bookimage` VALUES (48, 89, 'http://gofastdfs.yancq.top:10342/group1/image/20.jpg');
INSERT INTO `bookimage` VALUES (51, 92, 'http://gofastdfs.yancq.top:10342/group1/image/32.jpg');
INSERT INTO `bookimage` VALUES (52, 93, 'http://gofastdfs.yancq.top:10342/group1/image/19.jpg');
INSERT INTO `bookimage` VALUES (53, 94, 'http://gofastdfs.yancq.top:10342/group1/image/21.jpg');
INSERT INTO `bookimage` VALUES (54, 95, 'http://gofastdfs.yancq.top:10342/group1/image/22.jpg');
INSERT INTO `bookimage` VALUES (55, 96, 'http://gofastdfs.yancq.top:10342/group1/image/23.jpg');
INSERT INTO `bookimage` VALUES (57, 98, 'http://gofastdfs.yancq.top:10342/group1/image/35.jpg');
INSERT INTO `bookimage` VALUES (58, 99, 'http://gofastdfs.yancq.top:10342/group1/image/36.jpg');
INSERT INTO `bookimage` VALUES (59, 100, 'http://gofastdfs.yancq.top:10342/group1/image/37.jpg');
INSERT INTO `bookimage` VALUES (60, 101, 'http://gofastdfs.yancq.top:10342/group1/image/38.jpg');
INSERT INTO `bookimage` VALUES (61, 102, 'http://gofastdfs.yancq.top:10342/group1/image/39.jpg');
INSERT INTO `bookimage` VALUES (62, 103, 'http://gofastdfs.yancq.top:10342/group1/image/33.jpg');
INSERT INTO `bookimage` VALUES (63, 104, 'http://gofastdfs.yancq.top:10342/group1/image/41.jpg');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (3, 67, 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '小说 / 文学 / 语言文学');
INSERT INTO `category` VALUES (2, '历史 / 地理 / 艺术');
INSERT INTO `category` VALUES (3, '政治 / 法律 / 军事');
INSERT INTO `category` VALUES (4, '哲学 / 心理 / 宗教');
INSERT INTO `category` VALUES (5, '经济 / 社科 / 综合');
INSERT INTO `category` VALUES (6, '童书 / 生活 / 体育');
INSERT INTO `category` VALUES (7, '工程技术 / 互联网');
INSERT INTO `category` VALUES (8, '教材 / 教辅 / 考试');
INSERT INTO `category` VALUES (9, '自然科学 / 医药卫生');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (20, '1250364808440713216', 4);
INSERT INTO `order_info` VALUES (21, '1253233934934872064', 94);
INSERT INTO `order_info` VALUES (22, '1253239657836187648', 98);
INSERT INTO `order_info` VALUES (23, '1253239786668429312', 99);
INSERT INTO `order_info` VALUES (24, '1253562091529441280', 6);
INSERT INTO `order_info` VALUES (25, '1253562091529441280', 5);
INSERT INTO `order_info` VALUES (26, '1253962887802785792', 6);
INSERT INTO `order_info` VALUES (27, '1253967900071563264', 7);
INSERT INTO `order_info` VALUES (28, '1263006923838590976', 6);
INSERT INTO `order_info` VALUES (29, '1263011497320255488', 4);
INSERT INTO `order_info` VALUES (30, '1263014309529784320', 5);
INSERT INTO `order_info` VALUES (31, '1263015859169923072', 7);
INSERT INTO `order_info` VALUES (32, '1263018384610693120', 7);
INSERT INTO `order_info` VALUES (33, '1263831047498502144', 95);
INSERT INTO `order_info` VALUES (34, '1264209256245235712', 4);
INSERT INTO `order_info` VALUES (35, '1264389718372651008', 76);
INSERT INTO `order_info` VALUES (36, '1264401852276019200', 3);
INSERT INTO `order_info` VALUES (37, '1264481189373284352', 5);
INSERT INTO `order_info` VALUES (38, '1264482108437565440', 6);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `total_price` decimal(10, 2) NULL DEFAULT NULL,
  `buyer_id` int(10) NULL DEFAULT NULL,
  `seller_id` int(10) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT 0 COMMENT '0：未付款；1：待发货；2：已发货；3：交易成功；4：交易取消',
  `date` datetime(0) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1250364808440713216', 22.00, 1, 2, 4, '2020-05-20 18:06:16', 0);
INSERT INTO `orders` VALUES ('1253233934934872064', 30.00, 5, 1, 4, '2020-05-19 16:07:09', 0);
INSERT INTO `orders` VALUES ('1253239657836187648', 45.60, 1, 5, 4, '2020-05-18 16:29:53', 0);
INSERT INTO `orders` VALUES ('1253239786668429312', 30.00, 1, 5, 4, '2020-05-17 16:30:24', 0);
INSERT INTO `orders` VALUES ('1253562091529441280', 34.60, 1, 2, 4, '2020-05-16 13:51:07', 0);
INSERT INTO `orders` VALUES ('1253962887802785792', 20.80, 1, 2, 4, '2020-05-14 16:23:45', 0);
INSERT INTO `orders` VALUES ('1253967900071563264', 22.10, 1, 2, 4, '2020-05-15 16:43:40', 0);
INSERT INTO `orders` VALUES ('1263006923838590976', 20.80, 1, 2, 4, '2020-05-20 15:21:31', 0);
INSERT INTO `orders` VALUES ('1263011497320255488', 22.00, 1, 2, 4, '2020-05-21 15:39:41', 0);
INSERT INTO `orders` VALUES ('1263014309529784320', 23.80, 1, 2, 4, '2020-05-21 15:50:52', 0);
INSERT INTO `orders` VALUES ('1263015859169923072', 22.10, 1, 2, 4, '2020-05-20 15:57:01', 0);
INSERT INTO `orders` VALUES ('1263018384610693120', 22.10, 1, 2, 4, '2020-05-20 16:07:03', 0);
INSERT INTO `orders` VALUES ('1263831047498502144', 30.00, 5, 1, 1, '2020-05-22 21:56:17', 0);
INSERT INTO `orders` VALUES ('1264209256245235712', 22.00, 1, 2, 4, '2020-05-23 22:59:09', 0);
INSERT INTO `orders` VALUES ('1264389718372651008', 20.00, 1, 3, 0, '2020-05-24 10:56:15', 0);
INSERT INTO `orders` VALUES ('1264401852276019200', 19.90, 1, 2, 1, '2020-05-24 11:44:28', 0);
INSERT INTO `orders` VALUES ('1264481189373284352', 23.80, 1, 2, 1, '2020-05-24 16:59:43', 0);
INSERT INTO `orders` VALUES ('1264482108437565440', 20.80, 1, 2, 1, '2020-05-24 17:03:22', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '100001', 'li', 'e10adc3949ba59abbe56e057f20f883e', 'f', '17633753229', '2561976508@qq.com', '河南省信阳市', '16级软件工程', 0);
INSERT INTO `user` VALUES (2, '100002', 'wang', 'e10adc3949ba59abbe56e057f20f883e', 'f', '13212345678', '111111@qq.com', '河南省信阳市', '16级人力资源管理', 0);
INSERT INTO `user` VALUES (3, '100003', 'zhao', 'e10adc3949ba59abbe56e057f20f883e', 'm', '15812345678', '222222@qq.com', '河南省郑州市', '16级中文系', 0);
INSERT INTO `user` VALUES (4, '100004', 'yang', 'e10adc3949ba59abbe56e057f20f883e', 'm', '17633753229', '333333@qq.com', '河南省安阳市', '16级软件工程', 0);
INSERT INTO `user` VALUES (5, '100005', '张大炮', 'e10adc3949ba59abbe56e057f20f883e', 'm', '17633753229', '444444@qq.com', '河南省信阳市', '16级计算机系', 0);

SET FOREIGN_KEY_CHECKS = 1;
