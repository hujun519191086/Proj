/*
Navicat MySQL Data Transfer

Source Server         : server
Source Server Version : 50714
Source Host           : 192.168.1.252:3306
Source Database       : spservice

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2016-09-29 22:29:37
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

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
INSERT INTO `login` VALUES ('0000000025', '1085404092@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1474859915116', null, '71y16', null, null, '1085404092@qq.com', null, '7', null, '13336621182', null, '780245151867637760', null, null);
INSERT INTO `login` VALUES ('0000000026', 'zhou61226122@qq.com', null, null, '00000000000000000000', '00000000000000000000', '1475060209944', null, '8h9uq', null, null, '低调点', '13456789373', '7', null, '123zxc', null, '781085249265897472', null, '临海');
INSERT INTO `login` VALUES ('0000000032', 'ningboyizhong92@163.com', null, null, '00000000000000000000', '00000000000000000000', '1475128215678', null, 'lz6g', null, null, 'ningboyizhong92@163.com', null, '7', null, 'password', null, '781370485988036608', null, null);