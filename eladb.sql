CREATE DATABASE  IF NOT EXISTS `ela` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ela`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: ela
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `delete_time` datetime DEFAULT NULL,
  `avatar_url` varchar(1000) DEFAULT NULL,
  `create_time` varchar(45) NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user_id` bigint DEFAULT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'macdung1@gmail.com','$2a$10$JPtZPUTO0PydWnOchlsdJ.BhC2V5Qkq.ZoBmFl8btEAAfMl6tGcyu','Dung','Mac','2002-09-04','0977188002','Ha Noi','Toi la hoc vien Mac Dinh Dung sinh nam 2002',1,NULL,NULL,'2025-01-03 18:51:00','2025-01-04 12:07:17',1,'LEARNER'),(2,'macdung2@gmail.com','$2a$10$wVaR7lNbeJ6prDWafR6uMu50FRuXvW6YC9OrBAeqXvbaMBHzH/wpC',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'2025-01-04 11:13:24',NULL,NULL,'LEARNER'),(3,'moderator1@gmail.com','$2a$10$B.4ogGu31X087fDftwJFb.Xz6bUu8JBUjkj4p/eUYG5JmYidbRgYK',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'2025-01-04 16:38:55',NULL,NULL,'MODERATOR'),(4,'testlearner@gmail.com','$2a$10$CO6jUpr5y7X0fVaQkjBnIu5VijzvueAe1KU29MjE7EqdlMijds3sG','Dung','Mac','2002-09-04','0977188002','Ha Noi','Toi la hoc vien Mac Dinh Dung sinh nam 2002',1,NULL,'https://res.cloudinary.com/dg5gto7ns/image/upload/v1736254241/qna8zmdfw3udsv4waut0.jpg','2025-01-07 18:18:18','2025-01-07 19:50:47',4,'LEARNER'),(5,'macdinhdung@gmail.com','$2a$10$qifqr6fxeOVwB/767iF2kOPOeE69Fgs8HwOVvlMJPHYLcElDlxs0S',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'2025-01-08 15:58:25',NULL,NULL,'LEARNER'),(6,'hello@gmail.com','$2a$10$fJMVCEWH82CPLL24xI4dD.eJCb5cVvf.27LvF5o7Gfznax8jPTlKa',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'2025-01-08 16:26:53',NULL,NULL,'LEARNER'),(7,'email@gmail.com','$2a$10$uycvtgDbiHHuTRXdN6p3NejDXeDUQLU/GAxTJ42n3PzyklbLiqCR2',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,'2025-01-08 18:05:09',NULL,NULL,'LEARNER');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_id` bigint NOT NULL,
  `title` varchar(500) NOT NULL,
  `order_num` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `code` (
  `id` bigint NOT NULL,
  `p_id` bigint NOT NULL,
  `code` varchar(45) NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
INSERT INTO `code` VALUES (1,0,'CATEGORY','Danh mục'),(2,1,'Ngoại Ngữ','Ngoại Ngữ'),(3,1,'Lập trình - CNTT','Lập trình - CNTT'),(4,1,'Kinh Doanh - Khởi Nghiệp','Kinh Doanh - Khởi Nghiệp'),(5,1,'Đầu Tư','Đầu Tư'),(6,1,'Thiết Kế','Thiết Kế'),(7,1,'Nghệ Thuật - Đời Sống','Nghệ Thuật - Đời Sống'),(8,1,'Marketing','Marketing'),(9,1,'Phát Triển Bản Thân','Phát Triển Bản Thân'),(10,1,'Thể Thao - Sức Khỏe','Thể Thao - Sức Khỏe'),(11,1,'Hôn Nhân - Gia Đình','Hôn Nhân - Gia Đình'),(12,1,'Kiến Thức Chuyên Ngành','Kiến Thức Chuyên Ngành'),(13,1,'Tin Học Văn Phòng','Tin Học Văn Phòng'),(14,1,'Âm Nhạc','Âm Nhạc'),(15,1,'Nhiếp Ảnh - Dựng Phim','Nhiếp Ảnh - Dựng Phim');
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_id` bigint NOT NULL,
  `p_id` bigint NOT NULL,
  `account_id` bigint NOT NULL,
  `content` varchar(500) NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `author_account_id` bigint NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `profile_image` varchar(500) DEFAULT NULL,
  `root_price` int NOT NULL,
  `active` int DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` bigint NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user_id` bigint DEFAULT NULL,
  `type` int NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Làm Giàu Nhanh Chóng',1,'Làm Giàu Nhanh Chóng','https://res.cloudinary.com/dg5gto7ns/image/upload/v1736069920/dfg4zvnq8useejf6occ7.jpg',150000,0,'2025-01-04 22:55:12',1,'2025-01-05 16:38:41',1,1,4),(2,'Học Java Spring Boot 2025',1,'Học Java Spring Boot 2025',NULL,599000,0,'2025-01-04 22:56:07',1,NULL,NULL,0,0),(5,'Học Java Spring Boot 2026',1,'Học Java Spring Boot 2025','https://res.cloudinary.com/dg5gto7ns/image/upload/v1736067373/bxg1hco4ktf26qvazen5.jpg',599000,0,'2025-01-05 15:56:14',1,NULL,NULL,1,4);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_effect`
--

DROP TABLE IF EXISTS `course_effect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_effect` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_id` bigint NOT NULL,
  `effect` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_effect`
--

LOCK TABLES `course_effect` WRITE;
/*!40000 ALTER TABLE `course_effect` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_effect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `chapter_id` bigint NOT NULL,
  `title` varchar(500) NOT NULL,
  `description` varchar(5000) NOT NULL,
  `order_num` int NOT NULL,
  `active` int NOT NULL,
  `create_user_id` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_user_id` bigint DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson_attachment`
--

DROP TABLE IF EXISTS `lesson_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson_attachment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint NOT NULL,
  `file_name` varchar(500) NOT NULL,
  `file_url` varchar(500) NOT NULL,
  `file_size` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson_attachment`
--

LOCK TABLES `lesson_attachment` WRITE;
/*!40000 ALTER TABLE `lesson_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `lesson_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson_video`
--

DROP TABLE IF EXISTS `lesson_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson_video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint NOT NULL,
  `url` varchar(500) NOT NULL,
  `duration` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson_video`
--

LOCK TABLES `lesson_video` WRITE;
/*!40000 ALTER TABLE `lesson_video` DISABLE KEYS */;
/*!40000 ALTER TABLE `lesson_video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progress`
--

DROP TABLE IF EXISTS `progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `progress` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  `lesson_id` bigint NOT NULL,
  `completed` tinyint(1) NOT NULL,
  `progress_time` int DEFAULT NULL,
  `last_accessed` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progress`
--

LOCK TABLES `progress` WRITE;
/*!40000 ALTER TABLE `progress` DISABLE KEYS */;
/*!40000 ALTER TABLE `progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  `rating` int NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `account_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  `payment_method` int NOT NULL,
  `money` int NOT NULL,
  `status` int NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` bigint NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-08 19:58:26
