/*
Navicat MySQL Data Transfer

Source Server         : mybatis
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : zgbdb

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2019-03-15 10:45:55
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `abnormalbase`
-- ----------------------------
DROP TABLE IF EXISTS `abnormalbase`;
CREATE TABLE `abnormalbase` (
  `UUID` varchar(36) NOT NULL,
  `NODEID` varchar(4) DEFAULT NULL,
  `NODENAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(20) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `CURDISTIME` int(5) DEFAULT NULL,
  `SLEEPTIME` int(5) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `abnormalrefer`
-- ----------------------------
DROP TABLE IF EXISTS `abnormalrefer`;
CREATE TABLE `abnormalrefer` (
  `UUID` varchar(36) NOT NULL,
  `NODEID` varchar(4) DEFAULT NULL,
  `NODENAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(20) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `CURDISTIME` int(5) DEFAULT NULL,
  `SLEEPTIME` int(5) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abnormalrefer
-- ----------------------------
-- ----------------------------
-- Table structure for `abnormaltag`
-- ----------------------------
DROP TABLE IF EXISTS `abnormaltag`;
CREATE TABLE `abnormaltag` (
  `UUID` varchar(36) NOT NULL,
  `TAGID` varchar(4) NOT NULL,
  `TAGNAME` varchar(20) DEFAULT NULL,
  `TAGNO` varchar(50) DEFAULT NULL,
  `COMPANYNO` varchar(12) DEFAULT NULL,
  `COMPANYNAME` varchar(20) DEFAULT NULL,
  `JOBTYPENO` varchar(4) DEFAULT NULL,
  `JOBTYPENAME` varchar(12) DEFAULT NULL,
  `REFERID` varchar(4) DEFAULT NULL,
  `REFERNAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(50) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `SLEEPMODE` int(5) DEFAULT NULL,
  `SLEEPTIME` int(5) DEFAULT NULL,
  `CURDISTIME` int(5) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abnormaltag
-- ----------------------------
-- ----------------------------
-- Table structure for `access`
-- ----------------------------
DROP TABLE IF EXISTS `access`;
CREATE TABLE `access` (
  `UUID` varchar(36) CHARACTER SET utf8 NOT NULL,
  `TAGID` varchar(4) CHARACTER SET utf8 NOT NULL,
  `REFERID` varchar(4) CHARACTER SET utf8 DEFAULT NULL,
  `TAGNAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `PERSONNO` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `IMAGE` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `REFERNAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `COMPANYNO` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `JOBTYPENO` varchar(4) CHARACTER SET utf8 DEFAULT NULL,
  `COMPANYNAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `JOBTYPENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `GROUPID` int(11) DEFAULT NULL,
  `GROUPNAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `REGIONID` int(11) DEFAULT NULL,
  `REGIONNAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `ACCESSTYPE` int(11) DEFAULT NULL,
  `ACCESSTIME` datetime NOT NULL,
  `RESTIME` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of access
-- ----------------------------

-- ----------------------------
-- Table structure for `accounts`
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `USER_PSW` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ROLE_ID` int(5) DEFAULT NULL,
  `USER_STATUS` int(1) DEFAULT NULL,
  `USER_REPORT` date DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT_ID`),
  UNIQUE KEY `aUserName` (`USER_NAME`),
  KEY `aRoleId` (`ROLE_ID`),
  KEY `aCreateName` (`CREATENAME`) USING BTREE,
  KEY `aUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `aRoleId` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ROLE_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of accounts
-- ----------------------------

-- ----------------------------
-- Table structure for `areacontroller`
-- ----------------------------
DROP TABLE IF EXISTS `areacontroller`;
CREATE TABLE `areacontroller` (
  `UUID` varchar(36) NOT NULL,
  `TAGID` varchar(4) NOT NULL,
  `TAGNAME` varchar(20) DEFAULT NULL,
  `TAGNO` varchar(50) DEFAULT NULL,
  `COMPANYNO` varchar(12) DEFAULT NULL,
  `COMPANYNAME` varchar(20) DEFAULT NULL,
  `JOBTYPENO` varchar(4) DEFAULT NULL,
  `JOBTYPENAME` varchar(12) DEFAULT NULL,
  `REFERID` varchar(4) DEFAULT NULL,
  `REFERNAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(50) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of areacontroller
-- ----------------------------

-- ----------------------------
-- Table structure for `companys`
-- ----------------------------
DROP TABLE IF EXISTS `companys`;
CREATE TABLE `companys` (
  `COMPANY_NO` varchar(20) CHARACTER SET utf8 NOT NULL,
  `COMPANY_NAME` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `PHONE` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `ADDRESS` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`COMPANY_NO`),
  KEY `cCreateName` (`CREATENAME`) USING BTREE,
  KEY `cUpdateName` (`UPDATENAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of companys
-- ----------------------------
INSERT INTO `companys` VALUES ('0006', '6号当铺', '15657498', '54628', '2019-03-06 18:06:50', 'Admin', null, null);

-- ----------------------------
-- Table structure for `dangernodes`
-- ----------------------------
DROP TABLE IF EXISTS `dangernodes`;
CREATE TABLE `dangernodes` (
  `NODE_ID` varchar(4) CHARACTER SET utf8 NOT NULL,
  `ISSIGTHRESHOLD` bit(1) DEFAULT NULL,
  `SIGTHRESHOLD` int(10) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `dCreateName` (`CREATENAME`) USING BTREE,
  KEY `dUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `dnodeId` FOREIGN KEY (`NODE_ID`) REFERENCES `nodes` (`NODE_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of dangernodes
-- ----------------------------

-- ----------------------------
-- Table structure for `groups`
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `GROUP_ID` int(4) NOT NULL AUTO_INCREMENT,
  `GROUP_NAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `GROUP_DES` varchar(150) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATEName` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`GROUP_ID`),
  KEY `gCreateName` (`CREATENAME`) USING BTREE,
  KEY `gUpdateName` (`UPDATEName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of groups
-- ----------------------------

-- ----------------------------
-- Table structure for `jobtypes`
-- ----------------------------
DROP TABLE IF EXISTS `jobtypes`;
CREATE TABLE `jobtypes` (
  `JOBTYPE_NO` varchar(4) CHARACTER SET utf8 NOT NULL,
  `JOBTYPE_NAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `COLOR` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`JOBTYPE_NO`),
  KEY `jCreateName` (`CREATENAME`) USING BTREE,
  KEY `jUpdateName` (`UPDATENAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of jobtypes
-- ----------------------------

-- ----------------------------
-- Table structure for `logs`
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `DES` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `IP` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `LOGTIME` datetime DEFAULT NULL,
  `ARGS` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=360 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of logs
-- ----------------------------

-- ----------------------------
-- Table structure for `lowpower`
-- ----------------------------
DROP TABLE IF EXISTS `lowpower`;
CREATE TABLE `lowpower` (
  `UUID` varchar(36) NOT NULL,
  `TAGID` varchar(4) NOT NULL,
  `TAGNAME` varchar(20) DEFAULT NULL,
  `TAGNO` varchar(50) DEFAULT NULL,
  `COMPANYNO` varchar(12) DEFAULT NULL,
  `COMPANYNAME` varchar(20) DEFAULT NULL,
  `JOBTYPENO` varchar(4) DEFAULT NULL,
  `JOBTYPENAME` varchar(12) DEFAULT NULL,
  `REFERID` varchar(4) DEFAULT NULL,
  `REFERNAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(50) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `MINLOWBATTERY` int(5) DEFAULT NULL,
  `CURBATTERY` int(5) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lowpower
-- ----------------------------

-- ----------------------------
-- Table structure for `nodes`
-- ----------------------------
DROP TABLE IF EXISTS `nodes`;
CREATE TABLE `nodes` (
  `NODE_ID` varchar(4) CHARACTER SET utf8 NOT NULL,
  `NODE_NAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `NODE_TYPE` int(1) NOT NULL DEFAULT '1',
  `NODE_X` int(11) DEFAULT NULL,
  `NODE_Y` int(11) DEFAULT NULL,
  `REGION_ID` int(11) NOT NULL,
  `REGION_WIDTH` float(10,2) DEFAULT NULL,
  `REGION_HEIGHT` float(10,2) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `nRegionId` (`REGION_ID`),
  KEY `nCreateName` (`CREATENAME`) USING BTREE,
  KEY `nUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `nRegionId` FOREIGN KEY (`REGION_ID`) REFERENCES `regions` (`REGION_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of nodes
-- ----------------------------

-- ----------------------------
-- Table structure for `notmove`
-- ----------------------------
DROP TABLE IF EXISTS `notmove`;
CREATE TABLE `notmove` (
  `UUID` varchar(36) NOT NULL,
  `TAGID` varchar(4) NOT NULL,
  `TAGNAME` varchar(20) DEFAULT NULL,
  `TAGNO` varchar(50) DEFAULT NULL,
  `COMPANYNO` varchar(12) DEFAULT NULL,
  `COMPANYNAME` varchar(20) DEFAULT NULL,
  `JOBTYPENO` varchar(4) DEFAULT NULL,
  `JOBTYPENAME` varchar(12) DEFAULT NULL,
  `REFERID` varchar(4) DEFAULT NULL,
  `REFERNAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(50) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `FIXEDNOTMOVETIME` int(5) DEFAULT NULL,
  `CURNOTMOVETIME` int(5) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notmove
-- ----------------------------

-- ----------------------------
-- Table structure for `permissions`
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `PERMISSION_ID` int(5) NOT NULL AUTO_INCREMENT,
  `PERMISSION_NAME` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `PERMISSION_RESOURCE` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`PERMISSION_ID`),
  UNIQUE KEY `permissionName` (`PERMISSION_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=1176 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for `personhelps`
-- ----------------------------
DROP TABLE IF EXISTS `personhelps`;
CREATE TABLE `personhelps` (
  `UUID` varchar(36) NOT NULL,
  `TAGID` varchar(4) NOT NULL,
  `TAGNAME` varchar(20) DEFAULT NULL,
  `TAGNO` varchar(50) DEFAULT NULL,
  `COMPANYNO` varchar(12) DEFAULT NULL,
  `COMPANYNAME` varchar(20) DEFAULT NULL,
  `JOBTYPENO` varchar(4) DEFAULT NULL,
  `JOBTYPENAME` varchar(12) DEFAULT NULL,
  `REFERID` varchar(4) DEFAULT NULL,
  `REFERNAME` varchar(20) DEFAULT NULL,
  `REGIONID` int(5) DEFAULT NULL,
  `REGIONNAME` varchar(50) DEFAULT NULL,
  `GROUPID` int(5) DEFAULT NULL,
  `GROUPNAME` varchar(20) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `HANDLE` bit(1) DEFAULT NULL,
  `HANDLETIME` datetime DEFAULT NULL,
  `CLEAR` bit(1) DEFAULT NULL,
  `CLEARTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personhelps
-- ----------------------------

-- ----------------------------
-- Table structure for `persons`
-- ----------------------------
DROP TABLE IF EXISTS `persons`;
CREATE TABLE `persons` (
  `PERSON_NO` varchar(50) CHARACTER SET utf8 NOT NULL,
  `PERSON_NAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `PERSON_TAGNO` varchar(4) CHARACTER SET utf8 DEFAULT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `IMAGE` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `COMPANY_NO` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `JOBTYPE_NO` varchar(4) CHARACTER SET utf8 DEFAULT NULL,
  `RESTIME` float(10,2) DEFAULT NULL,
  `STOPTIME` float(10,2) DEFAULT NULL,
  `DES` varchar(120) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`PERSON_NO`),
  KEY `pCompanyNo` (`COMPANY_NO`),
  KEY `pJobType` (`JOBTYPE_NO`),
  KEY `pCreateName` (`CREATENAME`) USING BTREE,
  KEY `pUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `pCompanyNo` FOREIGN KEY (`COMPANY_NO`) REFERENCES `companys` (`COMPANY_NO`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pJobType` FOREIGN KEY (`JOBTYPE_NO`) REFERENCES `jobtypes` (`JOBTYPE_NO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of persons
-- ----------------------------

-- ----------------------------
-- Table structure for `person_nodes`
-- ----------------------------
DROP TABLE IF EXISTS `person_nodes`;
CREATE TABLE `person_nodes` (
  `PERSON_NO` varchar(12) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `NODE_ID` varchar(4) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`PERSON_NO`,`NODE_ID`),
  KEY `pnCreateName` (`CREATENAME`) USING BTREE,
  KEY `pnUpdateName` (`UPDATENAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of person_nodes
-- ----------------------------

-- ----------------------------
-- Table structure for `regions`
-- ----------------------------
DROP TABLE IF EXISTS `regions`;
CREATE TABLE `regions` (
  `REGION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GROUP_ID` int(11) DEFAULT NULL,
  `REGION_NAME` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `IMAGE` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`REGION_ID`),
  KEY `groupId` (`GROUP_ID`),
  KEY `rCreateName` (`CREATENAME`) USING BTREE,
  KEY `rUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `groupId` FOREIGN KEY (`GROUP_ID`) REFERENCES `groups` (`GROUP_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of regions
-- ----------------------------

-- ----------------------------
-- Table structure for `reliableperson_nodes`
-- ----------------------------
DROP TABLE IF EXISTS `reliableperson_nodes`;
CREATE TABLE `reliableperson_nodes` (
  `PERSON_NO` varchar(50) CHARACTER SET utf8 NOT NULL,
  `NODE_ID` varchar(4) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PERSON_NO`,`NODE_ID`),
  KEY `reliable_NodeId` (`NODE_ID`),
  CONSTRAINT `reliable_NodeId` FOREIGN KEY (`NODE_ID`) REFERENCES `nodes` (`NODE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reliable_PersonNo` FOREIGN KEY (`PERSON_NO`) REFERENCES `persons` (`PERSON_NO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of reliableperson_nodes
-- ----------------------------

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `ROLE_ID` int(5) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `ROLE_DES` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `roleName` (`ROLE_NAME`),
  KEY `roCreateName` (`CREATENAME`) USING BTREE,
  KEY `roUpdateName` (`UPDATENAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of roles
-- ----------------------------

-- ----------------------------
-- Table structure for `role_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions` (
  `ROLE_ID` int(5) NOT NULL DEFAULT '0',
  `PERMISSION_ID` int(5) NOT NULL DEFAULT '0',
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`),
  KEY `rpPsId` (`PERMISSION_ID`),
  KEY `rpCreateName` (`CREATENAME`) USING BTREE,
  KEY `rpUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `rpPermissionId` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permissions` (`PERMISSION_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rpRoleId` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ROLE_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------

-- ----------------------------
-- Table structure for `safenodes`
-- ----------------------------
DROP TABLE IF EXISTS `safenodes`;
CREATE TABLE `safenodes` (
  `NODE_ID` varchar(4) CHARACTER SET utf8 NOT NULL,
  `ISALLTRIG` bit(1) DEFAULT NULL,
  `ISPERIODTRIG` bit(1) DEFAULT NULL,
  `TRIG_BEGINTIME` datetime DEFAULT NULL,
  `TRIG_ENDTIME` datetime DEFAULT NULL,
  `ISSIGTHRESHOLD` bit(1) DEFAULT NULL,
  `SIGTHRESHOLD` int(10) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `CREATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `UPDATENAME` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`NODE_ID`),
  KEY `sCreateName` (`CREATENAME`) USING BTREE,
  KEY `sUpdateName` (`UPDATENAME`) USING BTREE,
  CONSTRAINT `snodeId` FOREIGN KEY (`NODE_ID`) REFERENCES `nodes` (`NODE_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of safenodes
-- ----------------------------
