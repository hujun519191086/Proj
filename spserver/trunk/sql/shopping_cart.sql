/*
Navicat MySQL Data Transfer

Source Server         : server
Source Server Version : 50714
Source Host           : 192.168.1.252:3306
Source Database       : shopping_cart

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2016-09-23 21:20:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for 购物车_0
-- ----------------------------
DROP TABLE IF EXISTS `购物车_0`;
CREATE TABLE `购物车_0` (
  `pid` bigint(20) NOT NULL,
  `gid` bigint(20) NOT NULL,
  `buyCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`,`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 购物车_0
-- ----------------------------
INSERT INTO `购物车_0` VALUES ('1', '0', '30');
INSERT INTO `购物车_0` VALUES ('111', '999', '222');

-- ----------------------------
-- Table structure for 购物车_734382929089188
-- ----------------------------
DROP TABLE IF EXISTS `购物车_734382929089188`;
CREATE TABLE `购物车_734382929089188` (
  `pid` bigint(20) NOT NULL,
  `gid` bigint(20) NOT NULL,
  `buyCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`,`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 购物车_734382929089188
-- ----------------------------
INSERT INTO `购物车_734382929089188` VALUES ('752008119387328512', '778493548391206912', '1');
INSERT INTO `购物车_734382929089188` VALUES ('752008119387328512', '778497452566810624', '1');

-- ----------------------------
-- Table structure for 购物车_752821510255268
-- ----------------------------
DROP TABLE IF EXISTS `购物车_752821510255268`;
CREATE TABLE `购物车_752821510255268` (
  `pid` bigint(20) NOT NULL,
  `gid` bigint(20) NOT NULL,
  `buyCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`,`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 购物车_752821510255268
-- ----------------------------
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778493548391206912', '1');
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778503637944668160', '1');
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778504532048646144', '1');
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778506915570946048', '4');
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778511338456125440', '7');
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778511401408434176', '1');
INSERT INTO `购物车_752821510255268` VALUES ('770889226501394432', '778513793294176256', '1');
