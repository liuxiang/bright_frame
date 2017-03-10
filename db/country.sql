/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : bright

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-03-08 17:45:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `countryname` varchar(255) DEFAULT NULL,
  `countrycode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` (`id`, `countryname`, `countrycode`) VALUES ('1', '中国', 'code_1');
INSERT INTO `country` (`id`, `countryname`, `countrycode`) VALUES ('2', '外国', 'code_2');
INSERT INTO `country` (`id`, `countryname`, `countrycode`) VALUES ('3', '国3', 'code_3');
INSERT INTO `country` (`id`, `countryname`, `countrycode`) VALUES ('4', '国4', 'code_4');
INSERT INTO `country` (`id`, `countryname`, `countrycode`) VALUES ('5', '国5', 'code_5');
