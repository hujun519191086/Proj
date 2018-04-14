/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : spservice

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2016-10-02 01:53:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chipsinfo
-- ----------------------------
DROP TABLE IF EXISTS `chipsinfo`;
CREATE TABLE `chipsinfo` (
  `_id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `userinfo` varchar(10) DEFAULT NULL,
  `been_completed` varchar(10) DEFAULT NULL,
  `revealed_time` time DEFAULT NULL,
  `article_address` varchar(255) DEFAULT NULL,
  `pic_adr` varchar(255) DEFAULT NULL,
  `opening_time` time DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `total_number` varchar(10) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chipsinfo
-- ----------------------------
INSERT INTO `chipsinfo` VALUES ('0000000001', null, null, null, null, null, null, 'yangxf', null, null, '1616546');
INSERT INTO `chipsinfo` VALUES ('0000000002', null, null, null, null, null, null, 'null', null, null, 'null');
INSERT INTO `chipsinfo` VALUES ('0000000003', null, null, null, null, null, null, '\"hello\"', null, null, '\"aga\"');
INSERT INTO `chipsinfo` VALUES ('0000000004', null, null, null, null, null, null, '\"hello11\"', null, null, '\"aga11\"');
INSERT INTO `chipsinfo` VALUES ('0000000005', null, null, null, null, null, null, 'hello00', null, null, 'aga11');

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `_id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'login table id',
  `mail` varchar(255) NOT NULL,
  `logintime` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `weight` float(20,0) unsigned zerofill DEFAULT '00000000000000000000',
  `high` float(20,0) unsigned zerofill DEFAULT '00000000000000000000',
  `registtime` varchar(255) DEFAULT '0',
  `verificationOutTime` varchar(255) DEFAULT NULL,
  `verificationCode` varchar(255) DEFAULT NULL,
  `personid` int(255) DEFAULT NULL,
  `county` varchar(10) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phonenum` varchar(255) DEFAULT NULL,
  `personstatus` int(10) unsigned DEFAULT '4',
  `deviceid` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT '"n"',
  `city` varchar(20) DEFAULT NULL,
  `pid` varchar(20) NOT NULL DEFAULT '0',
  `province` varchar(10) DEFAULT NULL,
  `address` text,
  PRIMARY KEY (`_id`,`mail`,`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('0000000013', 'abc@13.com', null, null, '00000000000000000000', '00000000000000000000', '1467517096480', null, 'i6dr5x', null, null, null, null, '7', null, 'asdf123', null, '1467517096480', null, null);
INSERT INTO `login` VALUES ('0000000014', 'hshsbs@123.com', null, null, '00000000000000000000', '00000000000000000000', '0', null, '6ld7od', null, null, null, null, '5', null, null, null, '0000000000', null, null);
INSERT INTO `login` VALUES ('0000000015', 'XXXX@email.com', null, null, '00000000000000000000', '00000000000000000000', '0', null, 'bk1o', null, null, null, null, '5', null, null, null, '0000000000', null, null);
INSERT INTO `login` VALUES ('0000000017', 'mark_yuan@yeah.net', null, null, '00000000000000000000', '00000000000000000000', '0', null, 'a6q46', null, null, null, null, '5', null, null, null, '0000000000', null, null);
INSERT INTO `login` VALUES ('0000000018', 'abcde@13.com', null, null, '00000000000000000000', '00000000000000000000', '1468046515867', null, 'd8yc5', null, null, null, null, '5', null, 'asdf12312', null, '-1960584453', null, null);
INSERT INTO `login` VALUES ('0000000019', 'dsfasd@113.com', null, null, '00000000000000000000', '00000000000000000000', '1468046821835', null, '4ho478', null, null, null, null, '5', null, 'asf12231244', null, '-1609824305', null, null);
INSERT INTO `login` VALUES ('0000000020', '123@q.com', null, null, '00000000000000000000', '00000000000000000000', '1468049131891', null, '5260', null, null, null, null, '5', null, 'tewkhasildf', null, '751678663976194048', null, null);
INSERT INTO `login` VALUES ('0000000021', '615294397@qq.com', '1468127877636', null, '00000000000000000000', '00000000000000000000', '1468127681954', null, '872o0', null, null, null, null, '7', '12321231', '123456@', null, '752008119387328512', null, null);
INSERT INTO `login` VALUES ('0000000024', 'x1085404092@163.com', null, null, '00000000000000000000', '00000000000000000000', '1472629288684', null, 'wcj5m', null, null, 'x1085404092@163.com', null, '7', null, '13336621182', null, '770889226501394432', null, null);
INSERT INTO `login` VALUES ('0000000026', 'zhou61226122@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1475060209944', null, '8h9uq', null, null, '低调点', '13456789373', '7', null, '123zxc', null, '781085249265897472', null, '临海');
INSERT INTO `login` VALUES ('0000000032', 'ningboyizhong92@163.com', null, null, '00000000000000000000', '00000000000000000000', '1475128215678', null, 'lz6g', null, null, 'ningboyizhong92@163.com', null, '7', null, 'password', null, '781370485988036608', null, null);
INSERT INTO `login` VALUES ('0000000037', '1085404092@qq.com', null, null, '00000000000000000000', '00000000000000000000', '0', null, '7lz9', null, null, null, null, '5', null, '\"n\"', null, '0', null, null);
INSERT INTO `login` VALUES ('0000000038', '664519909@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1475212839866', null, '38q12c', null, null, '664519909@qq.com', null, '7', null, 'hujian520', null, '781725425558261760', null, null);
INSERT INTO `login` VALUES ('0000000039', 'hujian520866@qq.com', null, null, '00000000000000000000', '00000000000000000000', '0', null, 'wx54u7', null, null, null, null, '5', null, '\"n\"', null, '0', null, null);
INSERT INTO `login` VALUES ('0000000040', 'fg94313070go@163.com', null, null, '00000000000000000000', '00000000000000000000', '1475211327451', null, 'y0196', null, null, 'fg94313070go@163.com', null, '7', null, '123zxc', null, '781719082029977600', null, null);
INSERT INTO `login` VALUES ('0000000041', '625686295@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1475212778032', null, 'tc5313', null, null, '625686295@qq.com', null, '7', null, 'chuan1995..', null, '781725166207668224', null, null);
INSERT INTO `login` VALUES ('0000000042', '2961610012@qq.com', null, null, '00000000000000000000', '00000000000000000000', '0', null, 't2275p', null, null, null, null, '5', null, '\"n\"', null, '0', null, null);
INSERT INTO `login` VALUES ('0000000043', 'xizihux@163.com', null, null, '00000000000000000000', '00000000000000000000', '1475214740785', null, '240x', null, null, 'xizihux@163.com', null, '7', null, 'temp1234', null, '781733398594621440', null, null);
INSERT INTO `login` VALUES ('0000000044', '1162195541@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1475221919801', null, '2thq4', null, null, '1162195541@qq.com', null, '7', null, 'zc5201314000', null, '781763509565952000', null, null);
INSERT INTO `login` VALUES ('0000000045', '466167032@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1475310636467', null, '0kuq1l', null, null, '466167032@qq.com', null, '7', null, 'y123456', null, '782135614233022464', null, null);
