-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: onsite
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.12.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Area`
--

DROP TABLE IF EXISTS `Area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Area` (
  `areaId` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Report_reportId` bigint(20) NOT NULL,
  PRIMARY KEY (`areaId`),
  UNIQUE KEY `areaId_UNIQUE` (`areaId`),
  KEY `fk_Area_Report1` (`Report_reportId`),
  CONSTRAINT `fk_Area_Report1` FOREIGN KEY (`Report_reportId`) REFERENCES `Report` (`reportId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Area`
--

LOCK TABLES `Area` WRITE;
/*!40000 ALTER TABLE `Area` DISABLE KEYS */;
INSERT INTO `Area` VALUES (1,'comment1','name1','number1',NULL,2),(2,'comment1','name2','number1',NULL,2),(3,'Testing Area Insert','Area1','1',NULL,3),(4,'Continued Test of Total Project Commit','NewArea','1',NULL,4),(5,'Testing unified commit to database','MOArea45','67',NULL,5),(6,'Testing multiObject Save','NewArea','56',NULL,6),(7,'testing on iPad','PadArea','13',NULL,7),(8,'Testing MultiObject Save','NewArea','7',NULL,8),(9,'Testing','SecondReportFirstArea','5',NULL,9),(10,'Testing DirtyFlags','DFArea','3',NULL,10),(11,'Dirty Flag Testing','DFAreaNow','9',NULL,11);
/*!40000 ALTER TABLE `Area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Asset`
--

DROP TABLE IF EXISTS `Asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Asset` (
  `assetId` bigint(20) NOT NULL AUTO_INCREMENT,
  `appraisalFlag` tinyint(4) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `initvalue` double DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `purchaseDate` date DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Area_areaId` bigint(20) NOT NULL,
  PRIMARY KEY (`assetId`),
  UNIQUE KEY `assetId_UNIQUE` (`assetId`),
  KEY `fk_Asset_Area1` (`Area_areaId`),
  CONSTRAINT `fk_Asset_Area1` FOREIGN KEY (`Area_areaId`) REFERENCES `Area` (`areaId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Asset`
--

LOCK TABLES `Asset` WRITE;
/*!40000 ALTER TABLE `Asset` DISABLE KEYS */;
INSERT INTO `Asset` VALUES (1,0,'description1',0,'name1',NULL,'type1',NULL,1),(2,0,'description1',0,'name1',NULL,'type1',NULL,1),(3,0,'description1',0,'name2',NULL,'type1',NULL,1),(4,0,'description1',0,'name3',NULL,'type1',NULL,1);
/*!40000 ALTER TABLE `Asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Company`
--

DROP TABLE IF EXISTS `Company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Company` (
  `companyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zipcode` varchar(5) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`),
  UNIQUE KEY `companyId_UNIQUE` (`companyId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Company`
--

LOCK TABLES `Company` WRITE;
/*!40000 ALTER TABLE `Company` DISABLE KEYS */;
INSERT INTO `Company` VALUES (1,'My Company5','7777 Greenridge Trail','Lithonia','NC','30058','jforn99@gmail.com','6664448888',NULL),(2,'My Company777','7777 Greenridge Trail','Lithonia','NC','30058','jforn99@gmail.com','6664448888',NULL),(3,'AppProved Software Corp','460 Lahontan Pass','Suwanee','GA','30024','jwmiller382@gmail.com','7703556820',NULL),(4,'My CompanyZZZZ','7777 Greenridge Trail','Lithonia','NC','30058','jforn99@gmail.com','6664448888',NULL),(5,'My Company55','7777 Greenridge Trail','Lithonia','NC','30058','jforn99@gmail.com','6664448888',NULL),(6,'jnt','1010','norcross','ga','30003','j.n.thompson@me.com','1111111111',NULL);
/*!40000 ALTER TABLE `Company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Note`
--

DROP TABLE IF EXISTS `Note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Note` (
  `noteId` bigint(20) NOT NULL AUTO_INCREMENT,
  `displayPic` varchar(45) DEFAULT NULL,
  `itemResolved` tinyint(4) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `note` varchar(45) DEFAULT NULL,
  `picCounter` int(11) DEFAULT NULL,
  `picOrientation` int(11) DEFAULT NULL,
  `picScale` float DEFAULT NULL,
  `thePic` binary(1) DEFAULT NULL,
  `trackableActionItem` tinyint(4) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Area_areaId` bigint(20) NOT NULL,
  PRIMARY KEY (`noteId`),
  UNIQUE KEY `noteId_UNIQUE` (`noteId`),
  KEY `fk_Note_Area1` (`Area_areaId`),
  CONSTRAINT `fk_Note_Area1` FOREIGN KEY (`Area_areaId`) REFERENCES `Area` (`areaId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Note`
--

LOCK TABLES `Note` WRITE;
/*!40000 ALTER TABLE `Note` DISABLE KEYS */;
INSERT INTO `Note` VALUES (1,NULL,1,1,2.6,'note for notes',232,500,2.9,NULL,0,NULL,1),(2,NULL,0,0,0,'Note2',0,0,0,NULL,0,NULL,3),(3,NULL,0,0,0,'Note1',0,0,0,NULL,0,NULL,3),(4,NULL,0,0,0,'Note3',0,0,0,NULL,0,NULL,3),(5,NULL,0,0,0,'NNotes2',0,0,0,NULL,0,NULL,4),(6,NULL,0,0,0,'NNote3',0,0,0,NULL,0,NULL,4),(7,NULL,0,0,0,'NNote1',0,0,0,NULL,0,NULL,4),(8,NULL,0,0,0,'NNote4',0,0,0,NULL,0,NULL,4),(9,NULL,0,0,0,'NNote5',0,0,0,NULL,0,NULL,4),(10,NULL,0,0,0,'MONote14',0,0,0,NULL,0,NULL,5),(11,NULL,0,0,0,'MONote11',0,0,0,NULL,0,NULL,5),(12,NULL,0,0,0,'MONote12',0,0,0,NULL,0,NULL,5),(13,NULL,0,0,0,'MONote16',0,0,0,NULL,0,NULL,5),(14,NULL,0,0,0,'MONote15',0,0,0,NULL,0,NULL,5),(15,NULL,0,0,0,'MONote13',0,0,0,NULL,0,NULL,5),(16,NULL,0,0,0,'Note3',0,0,0,NULL,0,NULL,6),(17,NULL,0,0,0,'Note2',0,0,0,NULL,0,NULL,6),(18,NULL,0,0,0,'Note1',0,0,0,NULL,0,NULL,6),(19,NULL,0,0,0,'Note1',0,0,0,NULL,0,NULL,7),(20,NULL,0,0,0,'Note3',0,0,0,NULL,0,NULL,7),(21,NULL,0,0,0,'Note2',0,0,0,NULL,0,NULL,7),(22,NULL,0,0,0,'Note1',0,0,0,NULL,0,NULL,8),(23,NULL,0,0,0,'Note4',0,0,0,NULL,0,NULL,8),(24,NULL,0,0,0,'Note3',0,0,0,NULL,0,NULL,8),(25,NULL,0,0,0,'Note2',0,0,0,NULL,0,NULL,8),(26,NULL,0,34.04655,-84.11398,'Note10',0,0,0,NULL,0,NULL,8),(27,NULL,0,34.04655,-84.11398,'Note8',0,0,0,NULL,0,NULL,8),(28,NULL,0,34.04655,-84.11398,'note7',0,0,0,NULL,0,NULL,8),(29,NULL,0,34.04655,-84.11398,'Note9',0,0,0,NULL,0,NULL,8),(30,NULL,0,34.04655,-84.1142,'N5',0,0,0,NULL,0,NULL,9),(31,NULL,0,34.04655,-84.1142,'N3',0,0,0,NULL,0,NULL,9),(32,NULL,0,34.04655,-84.1142,'N2',0,0,0,NULL,0,NULL,9),(33,NULL,0,34.04655,-84.1142,'Note1',0,0,0,NULL,0,NULL,9),(34,NULL,0,34.04655,-84.1142,'N4',0,0,0,NULL,0,NULL,9),(35,NULL,0,34.0465,-84.11414,'N4',0,0,0,NULL,0,NULL,10),(36,NULL,0,34.0465,-84.11414,'N2',0,0,0,NULL,0,NULL,10),(37,NULL,0,34.0465,-84.11414,'N3',0,0,0,NULL,0,NULL,10),(38,NULL,0,34.0465,-84.11414,'Note1',0,0,0,NULL,0,NULL,10),(39,NULL,0,34.04654,-84.1141,'N2',0,0,0,NULL,0,NULL,11),(40,NULL,0,34.04654,-84.1141,'N3',0,0,0,NULL,0,NULL,11),(41,NULL,0,34.04654,-84.1141,'N1',0,0,0,NULL,0,NULL,11),(42,NULL,0,34.04654,-84.1141,'N7',0,0,0,NULL,0,NULL,11),(43,NULL,0,34.04654,-84.1141,'N6',0,0,0,NULL,0,NULL,11),(44,NULL,0,34.04654,-84.1141,'N5',0,0,0,NULL,0,NULL,11);
/*!40000 ALTER TABLE `Note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person` (
  `personId` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `subscriptionType` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` varchar(1) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`personId`),
  UNIQUE KEY `inspectorId_UNIQUE` (`personId`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person`
--

LOCK TABLES `Person` WRITE;
/*!40000 ALTER TABLE `Person` DISABLE KEYS */;
INSERT INTO `Person` VALUES (1,'email99@gmail.com','subscriptionType','0909',NULL,'2013-08-04 00:09:00'),(2,'jwmiller382@gmail.com','subscriptionType','password0909',NULL,'2013-08-04 00:20:58'),(3,'jwmiller382@me.com','subscriptionType','passWord123',NULL,'2013-08-05 01:09:24'),(4,'jwmiller382@foo.com','subscriptionType','passWord143',NULL,'2013-08-05 01:12:23'),(5,'xxemail99@gmail.com','subscriptionType','password0909',NULL,'2013-08-05 02:35:14'),(6,'xxemai777l99@gmail.com','subscriptionType','password0909',NULL,'2013-08-05 23:06:47'),(7,'email11199@gmail.com','subscriptionType','password0909',NULL,'2013-08-15 18:19:07'),(8,'jwmiller123@foobar.com','subscriptionType','testing999',NULL,'2013-08-15 18:28:43'),(9,'email1119ccc9@gmail.com','subscriptionType','password0909',NULL,'2013-08-16 01:20:47'),(10,'email1119ddd9@gmail.com','subscriptionType','password0909',NULL,'2013-08-16 18:32:37'),(11,'email1119xxx9@gmail.com','subscriptionType','password0909',NULL,'2013-08-16 18:33:17'),(12,'email1119xx9@gmail.com','subscriptionType','password0909',NULL,'2013-08-16 18:41:23'),(13,'email1119c9@gmail.com','subscriptionType','password0909',NULL,'2013-08-16 20:47:12'),(14,'xxxxx@fffff.com','subscriptionType','password0909',NULL,'2013-08-16 22:36:36');
/*!40000 ALTER TABLE `Person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person_HAS_Company`
--

DROP TABLE IF EXISTS `Person_HAS_Company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person_HAS_Company` (
  `personId` bigint(20) NOT NULL,
  `companyId` bigint(20) NOT NULL,
  PRIMARY KEY (`personId`,`companyId`),
  KEY `fk_Person_has_Company_Company1` (`companyId`),
  KEY `fk_Person_has_Company_Person1` (`personId`),
  CONSTRAINT `fk_Person_has_Company_Company1` FOREIGN KEY (`companyId`) REFERENCES `Company` (`companyId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_has_Company_Person1` FOREIGN KEY (`personId`) REFERENCES `Person` (`personId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person_HAS_Company`
--

LOCK TABLES `Person_HAS_Company` WRITE;
/*!40000 ALTER TABLE `Person_HAS_Company` DISABLE KEYS */;
INSERT INTO `Person_HAS_Company` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1);
/*!40000 ALTER TABLE `Person_HAS_Company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person_HAS_Project`
--

DROP TABLE IF EXISTS `Person_HAS_Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person_HAS_Project` (
  `personId` bigint(20) NOT NULL,
  `projectId` bigint(20) NOT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`personId`,`projectId`),
  KEY `fk_Inspector_has_Project_Project1` (`projectId`),
  KEY `fk_Inspector_has_Project_Inspector1` (`personId`),
  CONSTRAINT `fk_Inspector_has_Project_Inspector1` FOREIGN KEY (`personId`) REFERENCES `Person` (`personId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inspector_has_Project_Project1` FOREIGN KEY (`projectId`) REFERENCES `Project` (`projectId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person_HAS_Project`
--

LOCK TABLES `Person_HAS_Project` WRITE;
/*!40000 ALTER TABLE `Person_HAS_Project` DISABLE KEYS */;
INSERT INTO `Person_HAS_Project` VALUES (1,1,NULL),(1,2,NULL),(1,10,NULL),(1,16,NULL),(1,17,NULL),(1,18,NULL),(1,20,NULL),(1,21,NULL),(1,22,NULL),(1,23,NULL),(1,24,NULL),(1,25,NULL),(1,26,NULL),(1,27,NULL),(1,28,NULL),(1,30,NULL),(2,1,NULL),(4,1,NULL),(11,9,NULL),(12,9,NULL),(14,1,'2013-08-16 22:36:36'),(14,9,NULL);
/*!40000 ALTER TABLE `Person_HAS_Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `projectId` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(30) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `countryCode` varchar(2) DEFAULT NULL,
  `county` varchar(45) DEFAULT NULL,
  `subAddress` varchar(45) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zipcode` varchar(5) DEFAULT NULL,
  `displayPic` varchar(25) DEFAULT NULL,
  `latcoord` float DEFAULT NULL,
  `longcoord` float DEFAULT NULL,
  `neighborhood` varchar(45) DEFAULT NULL,
  `projectName` varchar(45) DEFAULT NULL,
  `projectNumber` varchar(45) DEFAULT NULL,
  `uniqueRoomName` varchar(45) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Company_companyId` bigint(20) NOT NULL,
  PRIMARY KEY (`projectId`,`Company_companyId`),
  UNIQUE KEY `id_UNIQUE` (`projectId`),
  KEY `fk_Project_Company1` (`Company_companyId`),
  CONSTRAINT `fk_Project_Company1` FOREIGN KEY (`Company_companyId`) REFERENCES `Company` (`companyId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (1,'7777 Greenridge Trail','Lithonia','USA','US','Dekalb','Apt M','GA',NULL,NULL,0,0,'Greenridge','TestProject1','1234567890','Room One',NULL,1),(2,'7777 Greenridge Trail','Lithonia','USA','US','Dekalb','Apt M','GA',NULL,NULL,0,0,'Greenridge','TestProject12','123456789','Room One',NULL,1),(3,'7777 Greenridge Trail','Lithonia','USA','US','Dekalb','Apt M','GA',NULL,NULL,0,0,'Greenridge','Test','999','Room One',NULL,1),(4,'7777 Greenridge Trail','Lithonia','USA','US','Dekalb','Apt M','GA',NULL,NULL,0,0,'Greenridge','Test99999','111111999','Room One',NULL,1),(5,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project1','7',NULL,NULL,1),(6,'161 Kelton St','Boston',NULL,NULL,NULL,NULL,'MA','02134',NULL,0,0,NULL,'AEC Migration $$$','77',NULL,NULL,1),(7,'141 Wedgewood Rd','Reston',NULL,NULL,NULL,NULL,'VA','23168',NULL,0,0,NULL,'Project 3','57',NULL,NULL,1),(8,'4865 Deep Creek Rd','Fremont',NULL,NULL,NULL,NULL,'CA','97536',NULL,0,0,NULL,'Project5','37',NULL,NULL,1),(9,'6765 Wolford Ct','Duluth',NULL,NULL,NULL,NULL,'GA','30097',NULL,0,0,NULL,'Project 7','33',NULL,NULL,1),(10,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project11','8',NULL,NULL,1),(11,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project22','22',NULL,NULL,1),(12,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project25','25',NULL,NULL,1),(13,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project31','31',NULL,NULL,1),(14,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project29','29',NULL,NULL,1),(15,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project41','41',NULL,NULL,1),(16,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'Project42','42',NULL,NULL,1),(17,'460 Lahontan Pass','Suwanee',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'MyProject1','36',NULL,NULL,1),(18,'6765 Wolford Court','Duluth',NULL,NULL,NULL,NULL,'GA','30024',NULL,0,0,NULL,'NewProject2','2',NULL,NULL,1),(19,'4865 Deep Creek Rd','Fremont',NULL,NULL,NULL,NULL,'CA','93285',NULL,0,0,NULL,'MultiObjectCommitTest','3',NULL,NULL,1),(20,'11474 Mossy Creek Lane','Reston',NULL,NULL,NULL,NULL,'VA','30241',NULL,0,0,NULL,'MOTest2','237',NULL,NULL,1),(21,'5527 Vernon Rd','Jacksonville',NULL,NULL,NULL,NULL,'FL','32209',NULL,0,0,NULL,'MOTest3','78853',NULL,NULL,1),(22,'5527 Vernon Rd','Jacksonville',NULL,NULL,NULL,NULL,'FL','32209',NULL,0,0,NULL,'MOProject4','723',NULL,NULL,1),(23,'460 Lahontan Pass','Johns Creek',NULL,NULL,NULL,NULL,'GA','30024',NULL,34.0466,-84.1142,NULL,'TestProject','329',NULL,NULL,1),(24,'460 Lahontan Pass','Johns Creek',NULL,NULL,NULL,NULL,'GA','30024',NULL,34.0465,-84.1142,NULL,'TestIPadProject2','779',NULL,NULL,1),(25,'450 Lahontan Pass','Johns Creek',NULL,NULL,NULL,NULL,'GA','30024',NULL,34.0465,-84.1141,NULL,'DirtyFlagProject','961',NULL,NULL,1),(26,'450 Lahontan Pass','Johns Creek',NULL,NULL,NULL,NULL,'GA','30024',NULL,34.0465,-84.1141,NULL,'DirtyFlagProject','99664',NULL,NULL,1),(27,'460 Lahontan Pass','Johns Creek',NULL,NULL,NULL,NULL,'GA','30024',NULL,34.0465,-84.1141,NULL,'DFProject2','974125',NULL,NULL,1),(28,'460 Lahontan Pass','Johns Creek',NULL,NULL,NULL,NULL,'GA','30024',NULL,34.0465,-84.1141,NULL,'DFProject2','974167',NULL,NULL,1),(29,'7777 Greenridge Trail','Lithonia','USA','US','Dekalb','Apt M','GA',NULL,NULL,0,0,'Greenridge','Test','997619','Room One',NULL,1),(30,'250 Williams St','Atlanta',NULL,NULL,NULL,NULL,'GA','30303',NULL,33.7619,-84.3912,NULL,'Liberia Opportunity','4',NULL,NULL,1);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Report`
--

DROP TABLE IF EXISTS `Report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Report` (
  `reportId` bigint(20) NOT NULL AUTO_INCREMENT,
  `constructionphase` varchar(45) DEFAULT NULL,
  `peopleOnSite` int(11) DEFAULT NULL,
  `rname` varchar(45) DEFAULT NULL,
  `rtype` varchar(45) DEFAULT NULL,
  `voiceData` binary(1) DEFAULT NULL,
  `weatherData` binary(1) DEFAULT NULL,
  `timeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Project_projectId` bigint(20) NOT NULL,
  PRIMARY KEY (`reportId`),
  UNIQUE KEY `reportId_UNIQUE` (`reportId`),
  KEY `fk_Report_Project` (`Project_projectId`),
  CONSTRAINT `fk_Report_Project` FOREIGN KEY (`Project_projectId`) REFERENCES `Project` (`projectId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Report`
--

LOCK TABLES `Report` WRITE;
/*!40000 ALTER TABLE `Report` DISABLE KEYS */;
INSERT INTO `Report` VALUES (1,'starting',23,'report1','M',NULL,NULL,NULL,1),(2,'starting',23,'report2','MMM',NULL,NULL,NULL,1),(3,'Intermediate',37,'Report1','SiteReport',NULL,NULL,NULL,17),(4,'towardEnd',5,'NewReport2','Punchlist',NULL,NULL,NULL,18),(5,'theEnd',97,'MOReport57','Punchlist',NULL,NULL,NULL,20),(6,'start',92,'MOReportNew','PunchList',NULL,NULL,NULL,20),(7,'started',3,'testpadReport','Punchlist',NULL,NULL,NULL,23),(8,'started',5,'AReport','Punchlist',NULL,NULL,NULL,24),(9,'End',9,'BReport','Punchlist',NULL,NULL,NULL,24),(10,'TheEnd',7,'DFReport','Punchlist',NULL,NULL,NULL,26),(11,'start',6,'DFReport2','Punchlist',NULL,NULL,NULL,28),(12,'medium',65,'DFReport3','Punchlist',NULL,NULL,NULL,28);
/*!40000 ALTER TABLE `Report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserPreference`
--

DROP TABLE IF EXISTS `UserPreference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserPreference` (
  `userPreferenceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyLogo` binary(1) DEFAULT NULL,
  `companyName` varchar(45) DEFAULT NULL,
  `consumerKey` varchar(45) DEFAULT NULL,
  `consumerSecret` varchar(45) DEFAULT NULL,
  `deletedb` tinyint(4) DEFAULT NULL,
  `dropboxdirectory` varchar(45) DEFAULT NULL,
  `dropboxid` varchar(45) DEFAULT NULL,
  `dropboxpasswd` varchar(45) DEFAULT NULL,
  `emaildefault` varchar(45) DEFAULT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(30) DEFAULT NULL,
  `recordLimit` varchar(45) DEFAULT NULL,
  `titleOfPosition` varchar(45) DEFAULT NULL,
  `trailVersion` tinyint(4) DEFAULT NULL,
  `timeStamp` date DEFAULT NULL,
  PRIMARY KEY (`userPreferenceId`),
  UNIQUE KEY `userPreferenceId_UNIQUE` (`userPreferenceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserPreference`
--

LOCK TABLES `UserPreference` WRITE;
/*!40000 ALTER TABLE `UserPreference` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserPreference` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-09-11 13:40:46
