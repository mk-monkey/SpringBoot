/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务器
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 192.168.31.106
 Source Database       : mkmonkey

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 01/06/2018 22:19:28 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mk_massage`
-- ----------------------------
DROP TABLE IF EXISTS `mk_massage`;
CREATE TABLE `mk_massage` (
  `id` int(32) NOT NULL,
  `message` varchar(500) DEFAULT NULL,
  `senddate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mk_test`
-- ----------------------------
DROP TABLE IF EXISTS `mk_test`;
CREATE TABLE `mk_test` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
