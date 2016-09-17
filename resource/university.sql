/*
Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-09-13 09:34:02
 */

SET NAMES utf8;
CREATE DATABASE IF NOT EXISTS `university` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `university`;

DROP TABLE IF EXISTS `university_info`;
CREATE TABLE `university_info`(
  `id` int(32) unsigned NOT NULL auto_increment,
  `name` varchar(256) DEFAULT NULL ,
  `mainUrl` varchar(512) DEFAULT NULL ,
  `region` varchar(256) DEFAULT NULL ,
  `type` char(1) DEFAULT NULL,
  `is211` CHAR (1) DEFAULT NULL ,
  `is985` CHAR (1) DEFAULT NULL ,
  PRIMARY KEY (`id`)
)engine=InnoDB default charset=utf8;
