-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: hoteldatabase
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `admin_table`
--

DROP TABLE IF EXISTS `admin_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_table` (
  `aid` varchar(10) NOT NULL,
  `aname` varchar(10) NOT NULL,
  `apasswd` varchar(15) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_table`
--

LOCK TABLES `admin_table` WRITE;
/*!40000 ALTER TABLE `admin_table` DISABLE KEYS */;
INSERT INTO `admin_table` VALUES ('A001','admin','123'),('A002','管理2','123');
/*!40000 ALTER TABLE `admin_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assess_table`
--

DROP TABLE IF EXISTS `assess_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assess_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jiaotong` int(11) NOT NULL,
  `tingche` int(11) NOT NULL,
  `sheshi` int(11) NOT NULL,
  `ganjing` int(11) NOT NULL,
  `jiage` int(11) NOT NULL,
  `fuwu` int(11) NOT NULL,
  `score` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assess_table`
--

LOCK TABLES `assess_table` WRITE;
/*!40000 ALTER TABLE `assess_table` DISABLE KEYS */;
INSERT INTO `assess_table` VALUES (1,11,9,6,10,5,9,4);
/*!40000 ALTER TABLE `assess_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_table`
--

DROP TABLE IF EXISTS `book_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_table` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `byear` int(11) NOT NULL,
  `bmon` int(11) NOT NULL,
  `bday` int(11) NOT NULL,
  `broomid` varchar(10) NOT NULL,
  `bguestid` varchar(45) NOT NULL,
  `bprice` int(11) NOT NULL,
  `bassess` int(11) NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_table`
--

LOCK TABLES `book_table` WRITE;
/*!40000 ALTER TABLE `book_table` DISABLE KEYS */;
INSERT INTO `book_table` VALUES (61,2023,1,31,'101','1',100,1),(62,2023,2,1,'101','1',100,1),(66,2023,2,2,'101','112233',100,1),(67,2023,2,28,'101','1',100,0),(68,2023,3,2,'401','112233',200,0),(70,2023,1,1,'401','123123111',200,0),(71,2023,6,5,'401','1',200,0),(72,2024,1,4,'101','1',100,0),(85,2024,2,28,'401','1',200,0),(86,2024,2,29,'401','1',200,0),(87,2024,6,1,'301','112233',800,0),(88,2024,6,2,'301','112233',800,0),(90,2024,3,3,'403','1',350,0),(91,2024,3,4,'403','1',350,0),(92,2024,3,5,'403','1',350,0),(96,2024,5,8,'403','1',350,0),(97,2024,5,9,'403','1',350,0),(99,2024,1,4,'202','112233',200,0),(100,2024,1,4,'403','123123111',350,0),(101,2024,1,4,'402','123123111',200,0);
/*!40000 ALTER TABLE `book_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_table`
--

DROP TABLE IF EXISTS `comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `score` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_table`
--

LOCK TABLES `comment_table` WRITE;
/*!40000 ALTER TABLE `comment_table` DISABLE KEYS */;
INSERT INTO `comment_table` VALUES (1,'停车方便',4.5),(2,'不行',3),(3,'成功人士，巅峰住宅',4.5),(4,'很好',4.4),(12,'非常棒的酒店，设施很齐全，卫生干净',4.4),(13,'该用户没有留下评价~',4),(14,'交通便利，离市中心近',4.2),(15,'服务态度好',3.8),(16,'该用户没有留下评价~',4.2),(17,'该用户没有留下评价~',4),(18,'隔音很好',4.6),(19,'该用户没有留下评价~',4.3);
/*!40000 ALTER TABLE `comment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consume_table`
--

DROP TABLE IF EXISTS `consume_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consume_table` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `croom` varchar(10) NOT NULL,
  `cfood` varchar(10) NOT NULL,
  `cmon` int(11) NOT NULL,
  `cday` int(11) NOT NULL,
  `cnum` int(11) NOT NULL,
  `cprice` double NOT NULL,
  `cyear` int(11) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consume_table`
--

LOCK TABLES `consume_table` WRITE;
/*!40000 ALTER TABLE `consume_table` DISABLE KEYS */;
INSERT INTO `consume_table` VALUES (1,'101','泡面',1,1,2,16,2023),(2,'201','泡面',1,2,1,8,2023),(3,'101','泡面',1,31,1,8,2023),(4,'101','矿泉水',2,1,1,5,2023),(5,'101','咖啡',2,2,1,20,2023),(6,'101','冰红茶',12,29,1,10,2023),(7,'101','咖啡',12,29,2,32,2023),(8,'101','泡面',1,3,2,16,2024),(9,'101','冰红茶',1,3,1,8,2024),(10,'101','可乐',1,3,4,20,2024),(11,'101','可乐',1,1,2,20,2023),(12,'101','咖啡',1,4,5,100,2024),(14,'202','矿泉水',1,4,10,5,2024),(15,'101','空调',1,4,1,900,2024);
/*!40000 ALTER TABLE `consume_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_table`
--

DROP TABLE IF EXISTS `food_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_table` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) NOT NULL,
  `fprice` double NOT NULL,
  `ftype` varchar(10) NOT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_table`
--

LOCK TABLES `food_table` WRITE;
/*!40000 ALTER TABLE `food_table` DISABLE KEYS */;
INSERT INTO `food_table` VALUES (2,'泡面',8,'餐饮类'),(3,'矿泉水',5,'餐饮类'),(4,'空调',3000,'维修类'),(5,'咖啡',20,'餐饮类'),(11,'可乐',5,'餐饮类'),(29,'吹风机',30,'日用品类'),(33,'冰红茶',10,'餐饮类'),(34,'电视',2000,'维修类'),(35,'巧克力',15,'餐饮类'),(36,'陶瓷杯',20,'日用品类'),(37,'窗帘',200,'维修类'),(39,'小茗同学',2,'餐饮类');
/*!40000 ALTER TABLE `food_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest_table`
--

DROP TABLE IF EXISTS `guest_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest_table` (
  `gid` varchar(45) NOT NULL,
  `gname` varchar(15) NOT NULL,
  `gsex` varchar(5) NOT NULL,
  `gphonenum` varchar(11) DEFAULT NULL,
  `gpasswd` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest_table`
--

LOCK TABLES `guest_table` WRITE;
/*!40000 ALTER TABLE `guest_table` DISABLE KEYS */;
INSERT INTO `guest_table` VALUES ('1','王蓉','女','13319812543','123456'),('11','李欣','女','19788272711','123456'),('112','张婷','女','13927746523','123456'),('112233','张三','男','18399055402','123456'),('123','李四','男','13106635222','123456'),('12311','吴炜滨','男','13722584299','123456'),('123123','Wang Yingqi','男','13366734695',NULL),('123123111','张大壮','男','13313314678',NULL),('12345','王五','男','18865733409','123456'),('123456789','王英琪','女','18835833278','123456'),('a','a','男','13311122222',NULL),('aaa','aaa','男','13929947492',NULL),('bbb','乔海燕','男','13946822694','123456'),('E3432','刘六六','女','13733580642','123456'),('test','陈嘉弛','男','13368031468','123456'),('testday','何笑雨','男','15833680125','123456');
/*!40000 ALTER TABLE `guest_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_table`
--

DROP TABLE IF EXISTS `room_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_table` (
  `rid` varchar(10) NOT NULL,
  `rprice` int(10) NOT NULL,
  `rtype` varchar(10) NOT NULL,
  `rarea` int(10) NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_table`
--

LOCK TABLES `room_table` WRITE;
/*!40000 ALTER TABLE `room_table` DISABLE KEYS */;
INSERT INTO `room_table` VALUES ('101',150,'大床房',20),('102',150,'大床房',20),('201',200,'双床房',30),('202',200,'双床房',30),('301',800,'总统套房',120),('302',800,'总统套房',120),('401',200,'豪华单人间',25),('402',200,'豪华单人间',25),('403',350,'豪华双床房',35),('404',350,'豪华双床房',35);
/*!40000 ALTER TABLE `room_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hoteldatabase'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-04 17:31:45
