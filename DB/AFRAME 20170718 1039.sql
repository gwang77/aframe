-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.12


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema aframe
--

CREATE DATABASE IF NOT EXISTS aframe;
USE aframe;

--
-- Definition of table `tbl_acm_application`
--

DROP TABLE IF EXISTS `tbl_acm_application`;
CREATE TABLE `tbl_acm_application` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `base_url` varchar(500) DEFAULT NULL,
  `locked` char(1) NOT NULL,
  `create_by` varchar(100) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_application`
--

/*!40000 ALTER TABLE `tbl_acm_application` DISABLE KEYS */;
INSERT INTO `tbl_acm_application` (`id`,`app_id`,`name`,`base_url`,`locked`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1,'acm','ACM','','N','admin','2016-10-17 10:10:39','admin','2016-12-05 15:38:55',4);
/*!40000 ALTER TABLE `tbl_acm_application` ENABLE KEYS */;


--
-- Definition of table `tbl_acm_delegate`
--

DROP TABLE IF EXISTS `tbl_acm_delegate`;
CREATE TABLE `tbl_acm_delegate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_from` varchar(45) NOT NULL,
  `user_to` varchar(45) NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_delegate`
--

/*!40000 ALTER TABLE `tbl_acm_delegate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acm_delegate` ENABLE KEYS */;


--
-- Definition of table `tbl_acm_permission`
--

DROP TABLE IF EXISTS `tbl_acm_permission`;
CREATE TABLE `tbl_acm_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `app_id` varchar(100) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `request_method` varchar(10) NOT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_permission`
--

/*!40000 ALTER TABLE `tbl_acm_permission` DISABLE KEYS */;
INSERT INTO `tbl_acm_permission` (`id`,`name`,`app_id`,`url`,`request_method`,`permission`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1,'All','acm','/**','','*','SYS','2016-02-03 00:00:00','SYSTEM','2016-08-16 12:20:20',2);
/*!40000 ALTER TABLE `tbl_acm_permission` ENABLE KEYS */;


--
-- Definition of table `tbl_acm_role`
--

DROP TABLE IF EXISTS `tbl_acm_role`;
CREATE TABLE `tbl_acm_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(100) DEFAULT NULL,
  `app_id` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` char(1) DEFAULT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_tbl_acm_roles_role` (`role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_role`
--

/*!40000 ALTER TABLE `tbl_acm_role` DISABLE KEYS */;
INSERT INTO `tbl_acm_role` (`id`,`role`,`app_id`,`description`,`available`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1,'admin','acm','Admin','T','SYSTEM','2016-01-13 00:00:00','acm','2016-12-05 15:41:39',5);
/*!40000 ALTER TABLE `tbl_acm_role` ENABLE KEYS */;


--
-- Definition of table `tbl_acm_role_permission`
--

DROP TABLE IF EXISTS `tbl_acm_role_permission`;
CREATE TABLE `tbl_acm_role_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL,
  `permission_id` int(10) unsigned NOT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_role_permission`
--

/*!40000 ALTER TABLE `tbl_acm_role_permission` DISABLE KEYS */;
INSERT INTO `tbl_acm_role_permission` (`id`,`role_id`,`permission_id`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1,1,1,'acm','2016-12-05 15:41:40',NULL,NULL,1);
/*!40000 ALTER TABLE `tbl_acm_role_permission` ENABLE KEYS */;


--
-- Definition of table `tbl_acm_user`
--

DROP TABLE IF EXISTS `tbl_acm_user`;
CREATE TABLE `tbl_acm_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `user_type` varchar(10) NOT NULL,
  `realname` varchar(100) DEFAULT NULL,
  `staff_id` varchar(100) DEFAULT NULL,
  `id_number` varchar(100) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `locked` char(1) DEFAULT '0',
  `register_date` datetime NOT NULL,
  `reg_id` int(10) DEFAULT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_tbl_acm_users_username` (`username`) USING BTREE,
  KEY `Index_user_type` (`user_type`),
  KEY `Index_register_date` (`register_date`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_user`
--

/*!40000 ALTER TABLE `tbl_acm_user` DISABLE KEYS */;
INSERT INTO `tbl_acm_user` (`id`,`username`,`password`,`salt`,`user_type`,`realname`,`staff_id`,`id_number`,`sex`,`birth_date`,`tel`,`email`,`locked`,`register_date`,`reg_id`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1,'admin','d0d752c1c88f6fdb9a41ced5a203f5ff','18f1a8ae3e63cd4b24faa88d3d560f94','BP','admin','','',NULL,'2016-05-18','12345678918','123456@139.com','N','2017-05-08 14:07:24',0,'SYSTEM','2016-02-01 00:00:00','SYSTEM','2016-06-15 17:32:08',25),
 (3,'test1','91a6e306f3fa99ace29811c318893484','fc4b3814037137373eb0a0c61a923cbb','BP',NULL,NULL,NULL,'M',NULL,NULL,NULL,'N','2017-05-08 14:07:24',NULL,'acm','2017-04-25 11:10:23',NULL,NULL,1),
 (4,'test2','737726f13075b20a7261822196a8a897','aab0b9b849420ab5875f700915e72ee8','BP','ff','sd',NULL,NULL,NULL,NULL,NULL,'N','2017-05-08 14:07:24',NULL,'acm','2017-04-25 11:17:38','acm','2017-04-25 11:19:07',3),
 (5,'open12345',NULL,NULL,'UW',NULL,NULL,NULL,'',NULL,NULL,NULL,'N','2017-05-08 14:07:24',NULL,'acm','2017-04-25 11:20:11',NULL,NULL,1),
 (6,'test3','21e503b755ce4d14dae1e21e9eaa62f5','e438c03bad8b177491f2e563527a07f7','BP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'N','2017-05-08 14:07:24',NULL,'acm','2017-04-25 13:59:22','SYSTEM','2017-04-25 14:09:19',2);
/*!40000 ALTER TABLE `tbl_acm_user` ENABLE KEYS */;


--
-- Definition of table `tbl_acm_user_role`
--

DROP TABLE IF EXISTS `tbl_acm_user_role`;
CREATE TABLE `tbl_acm_user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL DEFAULT '0',
  `role_id` int(10) unsigned NOT NULL DEFAULT '0',
  `create_by` varchar(20) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_acm_user_role`
--

/*!40000 ALTER TABLE `tbl_acm_user_role` DISABLE KEYS */;
INSERT INTO `tbl_acm_user_role` (`id`,`user_id`,`role_id`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1,1,1,'admin','2016-12-21 15:00:22',NULL,NULL,1);
/*!40000 ALTER TABLE `tbl_acm_user_role` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_audit_log`
--

DROP TABLE IF EXISTS `tbl_comm_audit_log`;
CREATE TABLE `tbl_comm_audit_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `action` varchar(10) DEFAULT NULL,
  `audit_date` datetime DEFAULT NULL,
  `b_img` text,
  `a_img` text,
  PRIMARY KEY (`id`),
  KEY `Index_Actioin` (`action`) USING BTREE,
  KEY `Index_Audit_Date` (`audit_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_audit_log`
--

/*!40000 ALTER TABLE `tbl_comm_audit_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comm_audit_log` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_audit_log_action`
--

DROP TABLE IF EXISTS `tbl_comm_audit_log_action`;
CREATE TABLE `tbl_comm_audit_log_action` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `action` varchar(50) DEFAULT NULL,
  `audit_date` datetime DEFAULT NULL,
  `params` text,
  PRIMARY KEY (`id`),
  KEY `Index_user_name` (`user_name`) USING BTREE,
  KEY `Index_action` (`action`) USING BTREE,
  KEY `Index_audit_Date` (`audit_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_audit_log_action`
--

/*!40000 ALTER TABLE `tbl_comm_audit_log_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comm_audit_log_action` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_code_int`
--

DROP TABLE IF EXISTS `tbl_comm_code_int`;
CREATE TABLE `tbl_comm_code_int` (
  `CODETYPE_ID` varchar(20) NOT NULL,
  `CODE_ID` varchar(20) NOT NULL,
  `CODE_DESC` varchar(200) DEFAULT NULL,
  `CODE_SEQ` int(11) DEFAULT NULL,
  `STATUS` char(1) NOT NULL,
  `EFFECTIVE_DT` date DEFAULT NULL,
  `EXPIRY_DT` date DEFAULT NULL,
  `UPDATED_BY` varchar(32) DEFAULT NULL,
  `UPDATED_DT` datetime DEFAULT NULL,
  `LOCALE` varchar(2) NOT NULL,
  `EDITABLE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`CODETYPE_ID`,`CODE_ID`,`LOCALE`),
  CONSTRAINT `tbl_comm_code_int_ibfk_1` FOREIGN KEY (`CODETYPE_ID`) REFERENCES `tbl_comm_codetype` (`CODETYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_code_int`
--

/*!40000 ALTER TABLE `tbl_comm_code_int` DISABLE KEYS */;
INSERT INTO `tbl_comm_code_int` (`CODETYPE_ID`,`CODE_ID`,`CODE_DESC`,`CODE_SEQ`,`STATUS`,`EFFECTIVE_DT`,`EXPIRY_DT`,`UPDATED_BY`,`UPDATED_DT`,`LOCALE`,`EDITABLE`) VALUES 
 ('acm_usertype','L','LDAP User',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','en','N'),
 ('acm_usertype','L','LDAP User',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('acm_usertype','P','Password User',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','en','N'),
 ('acm_usertype','P','密码用户',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_locale','en','English',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','en','N'),
 ('comm_locale','en','英文',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_locale','zh','Chinese',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','en','N'),
 ('comm_locale','zh','中文',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_sex','F','Female',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','en','Y'),
 ('comm_sex','F','女',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','Y'),
 ('comm_sex','M','Male',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','en','Y'),
 ('comm_sex','M','男',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','Y'),
 ('comm_status','A','Active',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','en','N'),
 ('comm_status','A','活跃的',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_status','I','In-Active',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','N'),
 ('comm_status','I','不活跃',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_sys_config','mail_sender_addr','',13,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','Y'),
 ('comm_sys_config','mail_sender_name','',14,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','Y'),
 ('comm_sys_config','mail_smtp_host','localhost',15,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','Y'),
 ('comm_sys_config','mail_smtp_pwd','Passwords',17,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','Y'),
 ('comm_sys_config','mail_smtp_username','Username',16,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','Y'),
 ('comm_sys_config','sys_audit_log','N',12,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','Y'),
 ('comm_sys_config','sys_version','1.0.0',3,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','N'),
 ('comm_true_false','F','FALSE',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 00:00:00','en','N'),
 ('comm_true_false','F','错误',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_true_false','T','TRUE',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 00:00:00','en','N'),
 ('comm_true_false','T','正确',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 15:41:42','zh','N'),
 ('comm_yes_no','N','No',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:14','en','N'),
 ('comm_yes_no','N','否',2,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','zh','N'),
 ('comm_yes_no','Y','Yes',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','en','N'),
 ('comm_yes_no','Y','是',1,'A','2011-03-09','2099-12-31','SYS','2011-03-09 12:43:15','zh','N');
/*!40000 ALTER TABLE `tbl_comm_code_int` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_codetype`
--

DROP TABLE IF EXISTS `tbl_comm_codetype`;
CREATE TABLE `tbl_comm_codetype` (
  `CODETYPE_ID` varchar(20) NOT NULL,
  `CODETYPE_DESC` varchar(66) DEFAULT NULL,
  `CODETYPE_TABLE` varchar(66) DEFAULT NULL,
  `READ_ONLY` char(1) DEFAULT NULL,
  `COL_CODETYPE_ID` varchar(66) DEFAULT NULL,
  `COL_CODE_ID` varchar(66) DEFAULT NULL,
  `COL_CODE_DESC` varchar(66) DEFAULT NULL,
  `COL_CODE_SEQ` varchar(66) DEFAULT NULL,
  `COL_STATUS` varchar(66) DEFAULT NULL,
  `COL_EFFECTIVE_DT` varchar(66) DEFAULT NULL,
  `COL_EXPIRY_DT` varchar(66) DEFAULT NULL,
  `EDIT_URL` varchar(120) DEFAULT NULL,
  `ADD_URL` varchar(120) DEFAULT NULL,
  `OWNER_GROUP` varchar(200) DEFAULT NULL,
  `UPDATED_BY` varchar(32) DEFAULT NULL,
  `UPDATED_DT` datetime DEFAULT NULL,
  `COL_CODE_LOCALE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`CODETYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_codetype`
--

/*!40000 ALTER TABLE `tbl_comm_codetype` DISABLE KEYS */;
INSERT INTO `tbl_comm_codetype` (`CODETYPE_ID`,`CODETYPE_DESC`,`CODETYPE_TABLE`,`READ_ONLY`,`COL_CODETYPE_ID`,`COL_CODE_ID`,`COL_CODE_DESC`,`COL_CODE_SEQ`,`COL_STATUS`,`COL_EFFECTIVE_DT`,`COL_EXPIRY_DT`,`EDIT_URL`,`ADD_URL`,`OWNER_GROUP`,`UPDATED_BY`,`UPDATED_DT`,`COL_CODE_LOCALE`) VALUES 
 ('acm_usertype','User Type','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'/codeadmin/viewedit_int.do','/codeadmin/viewedit_int.do','DEF-group-groupA','SYS','2100-03-09 00:00:00','LOCALE'),
 ('comm_locale','Locale','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'/codeadmin/viewedit_int.do','/codeadmin/viewedit_int.do','DEF-group-groupA','SYS','2100-03-09 00:00:00','LOCALE'),
 ('comm_sex','Sex','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'','','','SYS','2100-03-09 00:00:00','LOCALE'),
 ('comm_status','Status','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'/codeadmin/viewedit_int.do','/codeadmin/viewedit_int.do','DEF-group-groupA','SYS','2100-03-09 00:00:00','LOCALE'),
 ('comm_sys_config','System Config','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'/codeadmin/viewedit_int.do','/codeadmin/viewedit_int.do','DEF-group-groupA','SYS','2100-03-09 00:00:00','LOCALE'),
 ('comm_true_false','True / False','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'/codeadmin/viewedit_int.do','/codeadmin/viewedit_int.do','DEF-group-groupA','SYS','2100-03-09 00:00:00','LOCALE'),
 ('comm_yes_no','Yes / No','TBL_CODE_INT','Y','CODETYPE_ID','CODE_ID','CODE_DESC','CODE_SEQ','STATUS',NULL,NULL,'/codeadmin/viewedit_int.do','/codeadmin/viewedit_int.do','DEF-group-groupA','SYS','2100-03-09 00:00:00','LOCALE');
/*!40000 ALTER TABLE `tbl_comm_codetype` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_file`
--

DROP TABLE IF EXISTS `tbl_comm_file`;
CREATE TABLE `tbl_comm_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `path` varchar(500) DEFAULT NULL COMMENT '存储路径',
  `create_by` varchar(100) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `Index_file_name` (`file_name`),
  KEY `Index_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_file`
--

/*!40000 ALTER TABLE `tbl_comm_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comm_file` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_menu`
--

DROP TABLE IF EXISTS `tbl_comm_menu`;
CREATE TABLE `tbl_comm_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `caption` varchar(100) DEFAULT NULL,
  `style` varchar(45) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `permission` varchar(200) DEFAULT NULL,
  `available` char(1) DEFAULT NULL,
  `seq_no` int(3) unsigned NOT NULL,
  `parent_id` int(10) unsigned DEFAULT NULL,
  `create_by` varchar(200) NOT NULL,
  `create_dt` datetime NOT NULL,
  `update_by` varchar(200) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1064 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_menu`
--

/*!40000 ALTER TABLE `tbl_comm_menu` DISABLE KEYS */;
INSERT INTO `tbl_comm_menu` (`id`,`caption`,`style`,`url`,`permission`,`available`,`seq_no`,`parent_id`,`create_by`,`create_dt`,`update_by`,`update_dt`,`version`) VALUES 
 (1001,'menu_root','NULL','NULL','NULL','Y',0,0,'SYSTEM','2017-02-03 12:53:40',NULL,NULL,1),
 (1002,'aframeUIApp.label_menu_home','fa fa-home','layout.home','NULL','Y',1,1001,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1003,'aframeUIApp.label_menu_menu','fa fa-home','layout.menu-list','NULL','Y',2,1001,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1004,'aframeUIApp.label_menu_code','fa fa-unlock','NULL','NULL','Y',3,1001,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1005,'aframeUIApp.label_menu_audit_log','fa fa-home','layout.audit-list','NULL','Y',4,1001,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1006,'acmUIApp.label_menu_acm','fa fa-unlock','NULL','NULL','Y',5,1001,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1041,'aframeUIApp.label_menu_code_codetype','fa fa-list','layout.code-codeType','NULL','Y',1,1004,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1042,'aframeUIApp.label_menu_code_codeint','fa fa-list','layout.code-codeInt','NULL','Y',2,1004,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1061,'acmUIApp.label_menu_user','fa fa-user','layout.user-list','NULL','Y',1,1006,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1062,'acmUIApp.label_menu_application','fa fa-file-text','layout.application-list','NULL','Y',2,1006,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1),
 (1063,'acmUIApp.label_menu_deputize','fa fa-file-text','layout.delegate-list','NULL','Y',3,1006,'SYSTEM','2017-02-03 12:53:41',NULL,NULL,1);
/*!40000 ALTER TABLE `tbl_comm_menu` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_seqno`
--

DROP TABLE IF EXISTS `tbl_comm_seqno`;
CREATE TABLE `tbl_comm_seqno` (
  `key_name` varchar(50) NOT NULL,
  `max_id` bigint(20) NOT NULL,
  `max_id_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`key_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_seqno`
--

/*!40000 ALTER TABLE `tbl_comm_seqno` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comm_seqno` ENABLE KEYS */;


--
-- Definition of table `tbl_comm_statistic`
--

DROP TABLE IF EXISTS `tbl_comm_statistic`;
CREATE TABLE `tbl_comm_statistic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `item_key` varchar(45) NOT NULL,
  `item_desc` varchar(100) DEFAULT NULL,
  `item_count` int(10) unsigned DEFAULT NULL,
  `item_user_count` int(10) unsigned DEFAULT NULL,
  `seq_no` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_item_key` (`item_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_comm_statistic`
--

/*!40000 ALTER TABLE `tbl_comm_statistic` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comm_statistic` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
