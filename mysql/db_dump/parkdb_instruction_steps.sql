CREATE DATABASE  IF NOT EXISTS `parkdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `parkdb`;
-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: parkdb
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

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
-- Table structure for table `instruction_steps`
--

DROP TABLE IF EXISTS `instruction_steps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instruction_steps` (
  `id` varchar(45) NOT NULL,
  `plant_id` varchar(45) NOT NULL,
  `instruction_id` varchar(45) NOT NULL,
  `task` text NOT NULL,
  `report` text,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_instruction_steps_instructions1_idx` (`instruction_id`),
  KEY `fk_instruction_steps_plants1_idx` (`plant_id`),
  CONSTRAINT `fk_instruction_steps_instructions1` FOREIGN KEY (`instruction_id`) REFERENCES `instructions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_instruction_steps_plants1` FOREIGN KEY (`plant_id`) REFERENCES `plants` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instruction_steps`
--

LOCK TABLES `instruction_steps` WRITE;
/*!40000 ALTER TABLE `instruction_steps` DISABLE KEYS */;
INSERT INTO `instruction_steps` VALUES ('0e4575ef-ddb5-461d-8d49-2be08cbd580a','a6f40940-a8c0-4975-a365-c8a6e30d04c2','a7eabb2e-f884-4bac-9fd8-33a2774bc11d','Clean','Убрал','NEW'),('10bd8ca1-7e29-498a-ac33-6e6656cf5683','a6f40940-a8c0-4975-a365-c8a6e30d04c3','4394f8dd-07e1-4300-b4e1-2d60cd244b81','Полить дубы','Полил','DONE_VERIFIED'),('8ad8c760-3529-4d8f-9aa6-d67195c087aa','a6f40940-a8c0-4975-a365-c8a6e30d04c2','4394f8dd-07e1-4300-b4e1-2d60cd244b81','Полить розу лучше','Полил','DONE_VERIFIED'),('b681fef9-c98a-465f-beeb-da9d29b90919','a6f40940-a8c0-4975-a365-c8a6e30d04c3','a7eabb2e-f884-4bac-9fd8-33a2774bc11d','Clean better','Cleaned and watered much better','NEW'),('e9cd8525-75c8-4736-bd0a-dcfa5cd846a4','a6f40940-a8c0-4975-a365-c8a6e30d04c2','136ec54f-2f91-45e4-906e-bbde33160702','water','Watered','DONE_VERIFIED'),('f39ce3c6-058a-4dbe-9a6d-90c31de75c36','a6f40940-a8c0-4975-a365-c8a6e30d04c3','136ec54f-2f91-45e4-906e-bbde33160702','Удобрить','Замесил говно(удоп)','DONE_VERIFIED');
/*!40000 ALTER TABLE `instruction_steps` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-20 14:29:58
