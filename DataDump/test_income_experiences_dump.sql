-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test_income_experiences
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Table structure for table `financial_story`
--

DROP TABLE IF EXISTS `financial_story`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `financial_story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(2000) NOT NULL,
  `date` date NOT NULL,
  `visible` tinyint(1) NOT NULL,
  `profile_user` int(11) NOT NULL,
  `editor` int(11) DEFAULT NULL,
  `unsuitable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `financial_story_user` (`profile_user`),
  KEY `financial_story_editor` (`editor`),
  CONSTRAINT `financial_story_editor` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `financial_story_user` FOREIGN KEY (`profile_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financial_story`
--

LOCK TABLES `financial_story` WRITE;
/*!40000 ALTER TABLE `financial_story` DISABLE KEYS */;
INSERT INTO `financial_story` VALUES (1,'We made it work in our 4-generation home.','2018-01-01',1,4,4,0),(2,'It was a great year. Please check out my pyramid scheme.','2018-03-04',0,8,8,1),(3,'It was a great year.','2018-03-05',1,8,1,0),(4,'We had a job loss and a foreclosure on our home.','2019-02-03',1,1,1,0);
/*!40000 ALTER TABLE `financial_story` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goals_description`
--

DROP TABLE IF EXISTS `goals_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goals_description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goals_description`
--

LOCK TABLES `goals_description` WRITE;
/*!40000 ALTER TABLE `goals_description` DISABLE KEYS */;
INSERT INTO `goals_description` VALUES (5,'Income allowed for new or expanding financial goals.'),(3,'Many goals were met.'),(4,'Most or all goals were easily met.'),(2,'Unmet goals caused frustration.'),(1,'Unmet goals caused insecurity or high stress.');
/*!40000 ALTER TABLE `goals_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goals_unmet`
--

DROP TABLE IF EXISTS `goals_unmet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goals_unmet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `savings` tinyint(1) DEFAULT NULL,
  `career_ed` tinyint(1) DEFAULT NULL,
  `needs_quality` tinyint(1) DEFAULT NULL,
  `donations` tinyint(1) DEFAULT NULL,
  `recreation` tinyint(1) DEFAULT NULL,
  `travel` tinyint(1) DEFAULT NULL,
  `services` tinyint(1) DEFAULT NULL,
  `other` tinyint(1) DEFAULT NULL,
  `survey_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `goals_unmet_survey` (`survey_id`),
  CONSTRAINT `goals_unmet_survey` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goals_unmet`
--

LOCK TABLES `goals_unmet` WRITE;
/*!40000 ALTER TABLE `goals_unmet` DISABLE KEYS */;
/*!40000 ALTER TABLE `goals_unmet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income_skew`
--

DROP TABLE IF EXISTS `income_skew`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `income_skew` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income_skew`
--

LOCK TABLES `income_skew` WRITE;
/*!40000 ALTER TABLE `income_skew` DISABLE KEYS */;
INSERT INTO `income_skew` VALUES (1,'little or no impact'),(2,'some impact'),(3,'strong impact');
/*!40000 ALTER TABLE `income_skew` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `needs_description`
--

DROP TABLE IF EXISTS `needs_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `needs_description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `needs_description`
--

LOCK TABLES `needs_description` WRITE;
/*!40000 ALTER TABLE `needs_description` DISABLE KEYS */;
INSERT INTO `needs_description` VALUES (5,'All needs were comfortably met.'),(4,'Needs were generally met.'),(1,'Severely unmet needs caused permanent harm.'),(3,'Unmet needs caused discomfort.'),(2,'Unmet needs caused illness or decreased ability at work or school.');
/*!40000 ALTER TABLE `needs_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `needs_unmet`
--

DROP TABLE IF EXISTS `needs_unmet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `needs_unmet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food` tinyint(1) DEFAULT NULL,
  `housing` tinyint(1) DEFAULT NULL,
  `utilities` tinyint(1) DEFAULT NULL,
  `health_care` tinyint(1) DEFAULT NULL,
  `clothing` tinyint(1) DEFAULT NULL,
  `transportation` tinyint(1) DEFAULT NULL,
  `child_care` tinyint(1) DEFAULT NULL,
  `other` tinyint(1) DEFAULT NULL,
  `survey_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `needs_unmet_survey` (`survey_id`),
  CONSTRAINT `needs_unmet_survey` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `needs_unmet`
--

LOCK TABLES `needs_unmet` WRITE;
/*!40000 ALTER TABLE `needs_unmet` DISABLE KEYS */;
/*!40000 ALTER TABLE `needs_unmet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(4,'advanced user'),(5,'blocked user'),(3,'data user'),(2,'new user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `family_size` int(11) NOT NULL,
  `income` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `needs_unmet_id` int(11) NOT NULL,
  `goals_unmet_id` int(11) NOT NULL,
  `needs_description_id` int(11) NOT NULL,
  `goals_description_id` int(11) NOT NULL,
  `income_skew_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `survey_goals_description` (`goals_description_id`),
  KEY `survey_goals_unmet` (`goals_unmet_id`),
  KEY `survey_income_skew` (`income_skew_id`),
  KEY `survey_needs_description` (`needs_description_id`),
  KEY `survey_needs_unmet` (`needs_unmet_id`),
  KEY `survey_user` (`user_id`),
  CONSTRAINT `survey_goals_description` FOREIGN KEY (`goals_description_id`) REFERENCES `goals_description` (`id`),
  CONSTRAINT `survey_income_skew` FOREIGN KEY (`income_skew_id`) REFERENCES `income_skew` (`id`),
  CONSTRAINT `survey_needs_description` FOREIGN KEY (`needs_description_id`) REFERENCES `needs_description` (`id`),
  CONSTRAINT `survey_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_role` (`role_id`),
  CONSTRAINT `user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','donthackme',1),(2,'jean','password1',2),(3,'chris','password2',3),(4,'jen','password3',4),(5,'sam','password4',3),(6,'kj','password5',3),(7,'ryan','password6',4),(8,'mary','password7',3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-12 11:37:25
