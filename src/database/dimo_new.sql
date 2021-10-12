CREATE DATABASE  IF NOT EXISTS `demolanka` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `demolanka`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: demolanka
-- ------------------------------------------------------
-- Server version	5.5.20

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(1000) NOT NULL,
  `contact_no` varchar(40) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `customer_type` int(11) DEFAULT NULL,
  `customer_acc_no` varchar(45) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'General','-','-',0,NULL,'2014-03-09','23:17:11',1),(2,'Shehan','23324','',1,NULL,'2014-03-19','12:39:03',1),(3,'Dinesh','07551652','Galle',3,'C032122','2014-03-22','19:18:20',1),(4,'Janaka','07165216','Ambalangoda',2,'C212','2014-03-22','19:57:19',1),(5,'Chamila','1351352','Kyddsd',1,'C51435','2014-03-22','20:02:12',1),(6,'Chathura','165449545','Galle',2,'C00010','2014-03-27','10:01:14',1),(7,'kalum','54654','1565',3,'5446546','2014-03-31','12:41:57',1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_vehicles`
--

DROP TABLE IF EXISTS `customer_vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_vehicles` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `vehicle_reg_no` varchar(45) DEFAULT NULL,
  `vehicle_model` int(11) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`),
  KEY `cust_id_idx` (`customer_id`),
  CONSTRAINT `cust_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_vehicles`
--

LOCK TABLES `customer_vehicles` WRITE;
/*!40000 ALTER TABLE `customer_vehicles` DISABLE KEYS */;
INSERT INTO `customer_vehicles` VALUES (1,4,'WP-154-555',NULL,'2014-02-16','23:51:18',1),(2,4,'QP-456-2154',NULL,'2014-02-16','23:51:18',1),(3,5,'WP-89797-878',NULL,'2014-02-16','23:56:03',1),(4,5,'QP-78994-988',NULL,'2014-02-16','23:56:03',1);
/*!40000 ALTER TABLE `customer_vehicles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `debt`
--

DROP TABLE IF EXISTS `debt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `debt` (
  `debtid` int(11) NOT NULL AUTO_INCREMENT,
  `custid` int(11) NOT NULL,
  `salesorderid` int(11) NOT NULL,
  `debtdate` varchar(45) DEFAULT NULL,
  `debtenddate` varchar(45) DEFAULT NULL,
  `debtamount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`debtid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `debt`
--

LOCK TABLES `debt` WRITE;
/*!40000 ALTER TABLE `debt` DISABLE KEYS */;
INSERT INTO `debt` VALUES (1,1,1,'2014-04-01','2014-05-01',256,1),(2,1,1,'2014-04-02','2014-05-02',52,2);
/*!40000 ALTER TABLE `debt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `debtdetail`
--

DROP TABLE IF EXISTS `debtdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `debtdetail` (
  `detailID` int(11) NOT NULL AUTO_INCREMENT,
  `debtid` int(11) DEFAULT NULL,
  `paymentdate` varchar(45) DEFAULT NULL,
  `paidamount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`detailID`),
  KEY `fk_debtid_idx` (`debtid`),
  CONSTRAINT `fk_debtid` FOREIGN KEY (`debtid`) REFERENCES `debt` (`debtid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `debtdetail`
--

LOCK TABLES `debtdetail` WRITE;
/*!40000 ALTER TABLE `debtdetail` DISABLE KEYS */;
INSERT INTO `debtdetail` VALUES (1,1,'2014-04-01',100,1),(2,1,'2014-04-01',156,1),(3,2,'2014-04-02',10,1);
/*!40000 ALTER TABLE `debtdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliverorder`
--

DROP TABLE IF EXISTS `deliverorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_deliver_order_id` int(11) NOT NULL,
  `acceptedDate` varchar(45) DEFAULT NULL,
  `accepted_time` varchar(45) DEFAULT NULL,
  `amount` double NOT NULL,
  `userAdmin` varchar(45) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `complete` int(1) DEFAULT NULL,
  `admin_purchase_order_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`admin_purchase_order_id`,`admin_deliver_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliverorder`
--

LOCK TABLES `deliverorder` WRITE;
/*!40000 ALTER TABLE `deliverorder` DISABLE KEYS */;
INSERT INTO `deliverorder` VALUES (1,1,'2014:03:18','08:18:07',1870,'dinesh',1,1,1);
/*!40000 ALTER TABLE `deliverorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliverorderdetail`
--

DROP TABLE IF EXISTS `deliverorderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverorderdetail` (
  `doi` int(11) NOT NULL AUTO_INCREMENT,
  `deliveridAdmin` int(11) NOT NULL,
  `unitprice` double DEFAULT NULL,
  `itemid` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `admin_purchase_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`doi`,`deliveridAdmin`),
  KEY `fk_deliveridAdmin_idx` (`deliveridAdmin`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliverorderdetail`
--

LOCK TABLES `deliverorderdetail` WRITE;
/*!40000 ALTER TABLE `deliverorderdetail` DISABLE KEYS */;
INSERT INTO `deliverorderdetail` VALUES (1,1,23.5,14,20,1,1),(2,1,35.5,26,20,1,1),(3,1,34.5,25,20,1,1);
/*!40000 ALTER TABLE `deliverorderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `ItemID` int(11) NOT NULL AUTO_INCREMENT,
  `BrandID` int(11) NOT NULL,
  `item_part_no` varchar(50) DEFAULT NULL,
  `buyingprice` double DEFAULT NULL,
  `sellingprice` double DEFAULT NULL,
  `addeddate` date DEFAULT NULL,
  `image` blob,
  `Description` varchar(50) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `re_order_qty` double DEFAULT NULL,
  `key_id` int(11) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`ItemID`,`BrandID`),
  UNIQUE KEY `item_part_no_UNIQUE` (`item_part_no`),
  KEY `BrandID_idx` (`BrandID`),
  CONSTRAINT `BrandID` FOREIGN KEY (`BrandID`) REFERENCES `itembrand` (`BrandID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,1,'TE252320123205',10.5,12.5,'2014-04-04','-','COLLER SCREW','-',5,1,1,1,1),(2,1,'TE266335707101',11.5,13.5,'2014-04-04','-','ROIL SEAL RETAINER ,ETR-637','-',6,3,1,2,1),(3,1,'TE251926806704',12.5,14.5,'2014-04-04','-','PIN','-',4,1,1,1,1),(4,1,'TE265454510131',13.5,15.5,'2014-04-04','-','REVERSE-LIGHT SWITCH','-',8,1,1,2,1),(5,1,'TE269968910159',14.5,16.5,'2014-04-04','-','ASSY AIR VENT CENTER','-',8,1,1,0,1),(6,1,'TE264042120101',15.5,17.5,'2014-04-04','-','23.81DIA.WHEEL CYLINDER','-',9,1,1,0,1),(7,1,'TE257667106339',16.5,18.5,'2014-04-04','-','RUBBER MOULD REAR WINDOW','-',10,1,1,0,1),(8,1,'TE284574300139',17.5,19.5,'2014-04-04','-','HANDLE ASSY EXTERIOR REAR DOOR','-',11,1,1,0,1),(9,1,'TE280826809901',18.5,20.5,'2014-04-04','-','GEAR SHIFT LEVER','-',12,1,1,1,1),(10,1,'TE278603999947',19.5,21.5,'2014-04-04','-','VIBRATION DAMPER','-',13,1,1,2,1),(11,1,'TE278603990147',20.5,22.5,'2014-04-04','-','CRANKSHAFT ASSEMBLY','-',14,1,1,0,1),(12,1,'TE277954200110',21.5,23.5,'2014-04-04','-','SPEEDO METER CABLE','-',15,1,1,0,1),(13,1,'TE215341120101',22.5,24.5,'2014-04-04','-','ASSY PROP SHARFT REAR','-',16,1,1,0,1),(14,1,'TE269126800108',23.5,25.5,'2014-04-04','-','SUB ASSY BELL CRANK LEVER','-',17,1,1,0,1),(15,1,'TE275441110101',24.5,26.5,'2014-04-04','-','APSC 1615LPT48 90X3 1255 WITH CENT. BRG.','-',18,1,1,0,1),(16,1,'TE281847106913',25.5,27.5,'2014-04-04','-','SPACER TUBE','-',19,1,1,0,1),(17,1,'TE251826208612',26.5,28.5,'2014-04-04','-','SPACER (6.9 MM THK)','-',20,1,1,0,1),(18,1,'TE251826208626',27.5,29.5,'2014-04-04','-','BUTTING RING (5.50 MM THK)','-',21,1,1,0,1),(19,1,'TE250826254201',28.5,30.5,'2014-04-04','-','CLAMPING PLATE.','-',22,1,1,0,1),(20,1,'TE282933208623',29.5,31.5,'2014-04-04','-','SHIM 0.4 THK-SPACER','-',23,3,1,0,1),(21,1,'TE282954406309',30.5,32.5,'2014-04-04','-','COVER FOR REVERSE LAMP CUTOUT','-',24,1,1,0,1),(22,1,'TE269126718703',31.5,33.5,'2014-04-04','-','COMPRESSION SPRING (REVER','-',25,1,1,0,1),(23,1,'TE282933208625',32.5,34.5,'2014-04-04','-','SHIM 0.6 THK-SPACER','-',26,1,1,0,1),(24,1,'TE279007146902',33.5,35.5,'2014-04-04','-','PLUG ( INJECTOR OVER FLOW )','-',27,1,1,0,1),(25,1,'TE253420125834',34.5,36.5,'2014-04-04','-','HOSE (THERMO TO RAD) SPRINT','-',28,1,1,0,1),(26,1,'TE551742990124',35.5,37.5,'2014-04-04','-','REAR KIT WHEEL CYLINDER  MINOR','-',29,1,1,0,1),(27,1,'TE279023120152',36.5,38.5,'2014-04-04','-','ASSY TENSION PULLEY W/H SPACER','-',30,1,1,0,1),(28,1,'TE277582400104',37.5,39.5,'2014-04-04','-','ASSY RR WIPER ARM & BLADE','-',31,1,1,0,1),(29,1,'TE269026200141',38.5,40.5,'2014-04-04','-','CYL ROLLER BEARING','-',32,1,1,0,1),(30,1,'TE284474502301',39.5,41.5,'2014-04-04','-','REAR DOOR GLASS','-',33,1,1,0,1),(31,1,'TE265172300162',40.5,42.5,'2014-04-04','-','GLOVE BOX LOCK(MINDA)','-',34,1,1,0,1),(32,1,'TE264189103703',41.5,43.5,'2014-04-04','-','ADJUSTER RH RHD TOR BAR','-',35,1,1,0,1),(33,1,'TE276326807703',42.5,44.5,'2014-04-04','-','FLAP','-',36,1,1,0,1),(34,1,'TE260829100118',43.5,45.5,'2014-04-04','-','PEDAL ASSY','-',37,1,1,3,1),(35,1,'TE265167100101',44.5,46.5,'2014-04-04','-','ASSY REAR WINDOW COMPLETE','-',38,1,1,0,1),(36,1,'TE282932600102',45.5,47.5,'2014-04-04','-','ASSY REAR SHOCK ABSO','-',39,1,1,0,1),(37,1,'TE270242308706',46.5,48.5,'2014-04-04','-','RETURN SPRING D - P65 0013 6','-',40,1,1,2,1),(38,1,'TE551740308210',47.5,49.5,'2014-04-04','-','LOCK PLATE (SPARE WHEEL MTG.)','-',41,1,1,1,1),(39,1,'TF12140801401',48.5,50.5,'2014-04-04','-','NYLOC NUT M14X1.5','-',42,1,1,0,1),(40,1,'TE282933000106',49.5,51.5,'2014-04-04','-','ASSY FRONT AXLE','-',43,1,1,0,1),(41,1,'TE269126715101',50.5,52.5,'2014-04-04','-','SHIFTER FINGER','-',44,1,1,0,1),(42,1,'TF12140801001',51.5,53.5,'2014-04-04','-','NYLOC NUT M10X1.25 ISO10512-10SS8451-8CH','-',45,1,1,2,1),(43,1,'TE551746996502',52.5,54.5,'2014-04-04','-','NYLOC NUT','-',46,1,1,0,1),(44,1,'TE269126518704',53.5,55.5,'2014-04-04','-','COMPRESSION SPRING (DETEN','-',47,1,1,0,1),(45,1,'TE252707100189',54.5,56.5,'2014-04-04','-','ASSY.ELECTRIC FUEL PUMP EURO-II','-',48,1,1,0,1),(46,1,'TE265947100111',55.5,57.5,'2014-04-04','-','ASSY.RUBBER BUSH','-',49,1,1,0,1),(47,1,'TE265983500115',56.5,58.5,'2014-04-04','-','ASSY COIL HOUSING WITH ALUMINUM HEATER','-',50,1,1,0,1),(48,1,'TE252550506335',57.5,59.5,'2014-04-04','-','FAN SHROUD (WITH ONE CLIP)','-',51,1,1,0,1),(49,1,'TE266335000209',58.5,60.5,'2014-04-04','-','ASSY RA44/9LSDNASBSWO/S.ABSA.ADJ','-',52,1,1,0,1),(50,1,'TE282943100105',59.5,61.5,'2014-04-04','-','MAJ REP KIT CALIPER','-',53,1,1,0,1),(51,1,'TE551742990125',200,202,'2014-04-04','-','REAR KIT WHEEL CYLINDER  MAJOR','-',54,1,1,0,1),(52,1,'TE289468906336',340.5,342.5,'2014-04-04','-','BOTTLE HOLDER / COIN HOLDER B002','-',55,1,1,0,1),(53,1,'TE269126208203',481,483,'2014-04-04','-','OIL SLINGER (REAR COVER)','-',56,1,1,0,1),(54,1,'TE283260000188BF',621.5,623.5,'2014-04-04','-','ASSY. BODY SHELL PAINTED ARCTIC WHITE','-',57,1,1,0,1),(55,1,'TE206726707501',762,764,'2014-04-04','-','RUBBER BELLOW','-',25,1,1,0,1),(56,1,'TE284354509913',902.5,904.5,'2014-04-04','-','WINDOW WIND SWITCH PACK','-',59,1,1,0,1),(57,1,'TE257646000113',1043,1045,'2014-04-04','-','DRAG LINK REPAIR KIT','-',93,1,1,0,1),(58,1,'TT3222600040',1183.5,1185.5,'2014-04-04','-','GR.SHIFT KNOB/G32G40','-',127,1,1,0,1),(59,1,'TE270247600180',1324,1326,'2014-04-04','-','ASSY BREATHER TUBE','-',161,1,1,0,1),(60,1,'TE551730100137',1464.5,1466.5,'2014-04-04','-','ASSY.EXCESS CABLE','-',195,1,1,0,1),(61,1,'TE551742998703',1605,1607,'2014-04-04','-','SPRING W/CYL END','-',229,1,1,0,1),(62,1,'TE551742990135',1745.5,1747.5,'2014-04-04','-','FRONT KIT SHOE ASSY. COMPLETE','-',263,1,1,0,1),(63,1,'TF11071812954',1886,1888,'2014-04-04','-','FLBOLT M12X1.5X140TS17130 10.9SS8451-8CH','-',297,1,1,0,1),(64,1,'TE551732401606',2026.5,2028.5,'2014-04-04','-','BUMP STOPPER REAR','-',331,1,1,0,1),(65,1,'TE551732103201',2167,2169,'2014-04-04','-','HEX FLANGE BOLT FOR LBJ MTG','-',365,1,1,0,1),(66,1,'TF11071814906',450.5,452.5,'2014-04-04','-','FL BOLT M14X1.5X90TS17130 10.9SS8451-8CH','-',399,2,1,0,1),(67,1,'TF11071810358',889.5,891.5,'0000-00-00','-','FLBOLT M10X1.25X35TS17130 10.9SS8451-8CH','-',433,1,1,0,1),(68,1,'TE581326300113',1328.5,1330.5,'2014-04-04','-','JOINT KIT WHEEL SIDE (LH)','-',467,1,1,0,1),(69,1,'TE551730100101',1767.5,1769.5,'2014-04-04','-','ASSY. ACCELERATOR CABLE','-',501,1,1,0,1),(70,1,'TE268426250119',2206.5,2208.5,'2014-04-04','-','NEEDLE CAGE','-',535,1,1,0,1),(71,1,'TE254750109904',2645.5,2647.5,'2014-04-04','-','DRAIN COCK','-',74,1,1,0,1),(72,1,'TE270246603405',3084.5,3086.5,'2014-04-04','-','RUBBER BUSHING','-',75,1,1,0,1),(73,1,'TF14580606009',3523.5,3525.5,'2014-04-04','-','INTERNAL CIRCLIP 60X2N IS3075P2 SS8400','-',76,1,1,0,1),(74,1,'TE265988500125BF',3962.5,3964.5,'2014-04-04','-','ASSY. BUMPER FRONT LH. ARCTIC WHITE','-',77,1,1,0,1),(75,1,'TE265988500126BF',4401.5,4403.5,'2014-04-04','-','ASSY. BUMPER FRONT RH. ARCTIC WHITE','-',78,1,1,0,1),(76,1,'TE283442990137',4840.5,4842.5,'2014-04-04','-','KIT SPRING ASSY','-',79,1,1,0,1),(77,1,'TE283442990126',5279.5,5281.5,'2014-04-04','-','KIT LEVER PAWL- RH','-',80,1,1,0,1),(78,1,'TEA09600000317',5718.5,5720.5,'2014-04-04','-','REAR BUMPER ASSY - RH','-',81,1,1,0,1),(79,1,'TE283442990125',6157.5,6159.5,'2014-04-04','-','KIT LEVER PAWL CONTENTS PER LH BRAKE ASS','-',82,1,1,0,1),(80,1,'TE551746997703',6596.5,6598.5,'2014-04-04','-','RACK BUSH','-',83,1,1,0,1),(81,1,'TE265133800105',7035.5,7037.5,'2014-04-04','-','BALL JOINT ASSY','-',84,1,1,0,1),(82,1,'TE551746208219',7474.5,7476.5,'2014-04-04','-','COVER SHEET','-',85,1,1,0,1),(83,1,'TET06560000043',7913.5,7915.5,'2014-04-04','-','PIN, PISTON','-',86,1,1,0,1),(84,1,'TE257535603110',900.25,902.25,'2014-04-04','-','REAR HUB INNER BEARING','-',87,1,1,0,1),(85,1,'TE265488700150',11.5,13.5,'2014-04-04','-','ASSY RELEASE CABLE RH RHD','-',88,1,1,0,1),(86,1,'TE263254400131',11.5,13.5,'2014-04-04','-','BLINKER LAMP LH WITH BULB','-',89,1,1,0,1),(87,1,'TE263254400130',11.5,13.5,'2014-04-04','-','BLINKER LAMP RH WITH BULB','-',90,1,1,0,1),(88,1,'TE551746207701',11.5,13.5,'2014-04-04','-','RUBBER GASKET','-',91,1,1,0,1),(89,1,'TE551746200104',1500,1502,'2014-04-04','-','ASSY. STEERING RUBBER BELLOW-RHD','-',92,1,1,0,1),(90,1,'TE287142990105',400,402,'2014-04-04','-','KIT - LEVER ASSY. RH','-',93,1,1,0,1),(91,1,'TE885403622525',400,402,'2014-04-04','-','BEARING SET STD','-',94,1,1,0,1),(92,1,'TE287142990104',400,402,'2014-04-04','-','KIT - LEVER ASSY - LH','-',95,1,1,0,1),(93,1,'TE257454506927',400,402,'2014-04-04','-','PIANO  KEY  SWITCH','-',96,1,1,0,1),(94,1,'TE261854500106',11.5,13.5,'2014-04-04','-','HAZARD  WARNING SWITCH','-',97,1,1,0,1),(95,1,'TE885403642525',1000,1002,'2014-04-04','-','CON ROD BEARING RS1','-',98,2,1,0,1),(96,1,'TE551732103204',1200,1202,'2014-04-04','-','STRUT MOUNTING PIN ECCENTRIC','-',99,1,1,0,1);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itembrand`
--

DROP TABLE IF EXISTS `itembrand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itembrand` (
  `BrandID` int(11) NOT NULL AUTO_INCREMENT,
  `BrandName` varchar(50) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `BrandCode` varchar(20) DEFAULT NULL,
  `Status` int(11) NOT NULL,
  PRIMARY KEY (`BrandID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itembrand`
--

LOCK TABLES `itembrand` WRITE;
/*!40000 ALTER TABLE `itembrand` DISABLE KEYS */;
INSERT INTO `itembrand` VALUES (1,'TATA','TATA','TA',1),(2,'Bajaj','Bajaj motors.','BAJ',1),(3,'TVS','TVS','TVS',1),(4,'Toyota','Japanise','154',1);
/*!40000 ALTER TABLE `itembrand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginhistroy`
--

DROP TABLE IF EXISTS `loginhistroy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loginhistroy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `logouttime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`lid`),
  CONSTRAINT `lid` FOREIGN KEY (`lid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginhistroy`
--

LOCK TABLES `loginhistroy` WRITE;
/*!40000 ALTER TABLE `loginhistroy` DISABLE KEYS */;
INSERT INTO `loginhistroy` VALUES (7,5,'2013-11-16','12:08:09',NULL),(8,6,'2013-11-16','12:08:20',NULL),(13,5,'2013-11-16','13:37:27',NULL),(14,5,'2013-11-16','13:43:53',NULL),(15,5,'2013-11-16','13:44:37',NULL),(16,5,'2013-11-16','13:52:11',NULL),(17,5,'2013-11-19','12:24:57',NULL),(18,5,'2013-11-19','12:26:32',NULL),(19,5,'2013-11-19','12:27:26',NULL),(20,5,'2013-11-19','13:37:33',NULL),(21,5,'2013-11-19','13:38:29',NULL),(22,5,'2013-11-19','13:40:46',NULL),(23,5,'2013-11-19','13:56:20',NULL),(24,5,'2013-11-19','16:34:01',NULL),(25,5,'2013-11-19','16:52:56',NULL),(26,5,'2013-11-20','15:29:45',NULL),(27,5,'2013-11-21','08:45:24',NULL),(28,5,'2013-11-29','09:03:43',NULL),(29,5,'2013-11-29','11:30:23',NULL),(30,5,'2013-11-29','11:35:03',NULL),(31,5,'2013-11-29','11:42:46',NULL),(32,5,'2013-11-29','11:51:21',NULL),(33,5,'2013-11-29','12:07:00',NULL),(34,5,'2013-11-29','12:41:37',NULL),(35,5,'2013-11-29','12:46:17',NULL),(36,5,'2013-11-29','13:11:33',NULL),(37,5,'2013-11-30','09:33:39',NULL),(38,5,'2013-11-30','09:50:48',NULL),(39,5,'2013-11-30','10:53:19',NULL),(40,5,'2013-12-02','16:22:27',NULL),(41,5,'2013-12-02','16:37:59',NULL),(42,5,'2013-12-02','16:42:02',NULL),(43,5,'2013-12-02','16:44:48',NULL),(44,5,'2013-12-02','16:48:08',NULL),(45,5,'2013-12-02','16:49:43',NULL),(46,5,'2013-12-02','16:51:02',NULL),(47,5,'2013-12-02','16:55:47',NULL),(48,5,'2013-12-02','16:57:40',NULL),(49,5,'2013-12-03','09:41:53',NULL),(50,5,'2013-12-03','10:09:15',NULL),(51,5,'2013-12-03','10:22:18',NULL),(52,5,'2013-12-03','10:26:35',NULL),(53,5,'2013-12-03','10:32:23',NULL),(54,5,'2013-12-03','10:33:29',NULL),(55,5,'2013-12-03','10:34:07',NULL),(56,5,'2013-12-03','10:43:11',NULL),(57,5,'2013-12-03','10:50:33',NULL),(58,5,'2013-12-03','10:52:16',NULL),(59,5,'2013-12-03','10:54:47',NULL),(60,5,'2013-12-03','10:58:53',NULL),(61,5,'2013-12-03','11:00:22',NULL),(62,5,'2013-12-03','11:03:35',NULL),(63,5,'2013-12-03','11:04:35',NULL),(64,5,'2013-12-03','11:42:07',NULL),(65,5,'2013-12-03','11:45:02',NULL),(66,5,'2013-12-03','11:46:38',NULL),(67,5,'2013-12-03','11:49:45',NULL),(68,5,'2013-12-03','11:58:00',NULL),(69,5,'2013-12-03','12:26:56',NULL),(70,5,'2013-12-03','12:32:56',NULL),(71,5,'2013-12-03','12:33:35',NULL),(72,5,'2013-12-03','13:03:48',NULL),(73,5,'2013-12-03','13:06:20',NULL),(74,5,'2013-12-03','13:12:23',NULL),(75,5,'2013-12-03','13:17:38',NULL),(76,5,'2013-12-03','13:51:47',NULL),(77,5,'2013-12-03','14:20:22',NULL),(78,5,'2013-12-03','14:21:51',NULL),(79,5,'2013-12-03','14:22:20',NULL),(80,5,'2013-12-03','14:26:10',NULL),(81,5,'2013-12-03','14:27:38',NULL),(82,5,'2013-12-03','14:28:24',NULL),(83,5,'2013-12-03','14:31:04',NULL),(84,5,'2013-12-03','14:36:34',NULL),(85,5,'2013-12-03','14:52:25',NULL),(86,5,'2013-12-03','14:52:27',NULL),(87,5,'2013-12-03','14:52:27',NULL),(88,5,'2013-12-03','14:56:54',NULL),(89,5,'2013-12-03','15:00:02',NULL),(90,5,'2013-12-03','15:02:28',NULL),(91,5,'2013-12-03','15:03:56',NULL),(92,5,'2013-12-03','15:04:57',NULL),(93,5,'2013-12-03','15:06:42',NULL),(94,5,'2013-12-03','15:13:37',NULL),(95,5,'2013-12-03','15:15:13',NULL),(96,5,'2013-12-03','15:25:13',NULL),(97,5,'2013-12-03','15:32:38',NULL),(98,5,'2013-12-03','15:37:51',NULL),(99,5,'2013-12-03','15:42:29',NULL),(100,5,'2013-12-03','15:50:25',NULL),(101,5,'2013-12-03','16:23:38',NULL),(102,5,'2013-12-03','16:24:05',NULL),(103,5,'2013-12-04','08:52:54',NULL),(104,5,'2013-12-06','14:06:58',NULL),(105,5,'2014-01-08','00:28:20',NULL),(106,5,'2014-01-08','00:32:02',NULL),(107,5,'2014-01-08','00:37:50',NULL),(108,5,'2014-01-08','00:39:16',NULL),(109,5,'2014-01-08','00:49:29',NULL),(110,5,'2014-01-08','09:13:21',NULL),(111,5,'2014-01-08','10:52:09',NULL),(112,5,'2014-01-08','12:06:47',NULL),(113,5,'2014-01-08','12:19:34',NULL),(114,5,'2014-01-08','13:10:57',NULL),(115,5,'2014-01-08','14:09:51',NULL),(116,5,'2014-01-08','15:46:12',NULL),(117,5,'2014-01-08','16:27:06',NULL),(118,5,'2014-01-08','17:11:39',NULL),(119,5,'2014-01-08','21:52:09',NULL),(120,5,'2014-01-08','23:02:54',NULL),(121,5,'2014-01-10','00:03:22',NULL),(122,5,'2014-01-10','10:10:27',NULL),(123,5,'2014-01-10','14:25:54',NULL),(124,5,'2014-01-10','16:26:04',NULL),(125,5,'2014-01-11','11:43:52',NULL),(126,5,'2014-01-11','11:45:58',NULL),(127,5,'2014-01-11','11:49:08',NULL),(128,5,'2014-01-11','11:51:38',NULL),(129,5,'2014-01-11','12:42:59',NULL),(130,5,'2014-01-11','13:00:47',NULL),(131,5,'2014-01-11','14:37:32',NULL),(132,5,'2014-01-11','14:42:24',NULL),(133,5,'2014-01-11','14:46:07',NULL),(134,5,'2014-01-11','14:48:20',NULL),(135,5,'2014-01-11','14:50:51',NULL),(136,5,'2014-01-11','14:57:04',NULL),(137,5,'2014-01-11','16:08:41',NULL),(138,5,'2014-01-11','21:46:49',NULL),(139,5,'2014-01-12','22:22:47',NULL),(140,5,'2014-01-12','22:28:49',NULL),(141,5,'2014-01-12','22:32:27',NULL),(142,5,'2014-01-12','22:38:54',NULL),(143,5,'2014-01-12','22:43:18',NULL),(144,5,'2014-01-12','22:56:05',NULL),(145,5,'2014-01-12','22:59:12',NULL),(146,5,'2014-01-13','00:18:58',NULL),(147,5,'2014-01-13','00:19:44',NULL),(148,5,'2014-01-13','00:25:00',NULL),(149,5,'2014-01-13','00:27:43',NULL),(150,5,'2014-01-13','00:28:49',NULL),(151,5,'2014-01-13','00:38:44',NULL),(152,5,'2014-01-13','00:50:35',NULL),(153,5,'2014-01-13','00:51:24',NULL),(154,5,'2014-01-13','00:52:30',NULL),(155,5,'2014-01-13','00:54:41',NULL),(156,5,'2014-01-13','00:58:07',NULL),(157,5,'2014-01-13','01:23:26',NULL),(158,5,'2014-01-13','01:24:39',NULL),(159,5,'2014-01-13','01:30:28',NULL),(160,5,'2014-01-13','01:31:06',NULL),(161,5,'2014-01-13','01:32:06',NULL),(162,5,'2014-01-13','01:35:25',NULL),(163,5,'2014-01-13','01:38:22',NULL),(164,5,'2014-01-13','01:39:24',NULL),(165,5,'2014-01-13','10:27:15',NULL),(166,5,'2014-01-13','10:28:39',NULL),(167,5,'2014-01-13','10:31:44',NULL),(168,5,'2014-01-13','10:33:45',NULL),(169,5,'2014-01-13','10:34:39',NULL),(170,5,'2014-01-13','10:36:27',NULL),(171,5,'2014-01-13','10:45:06',NULL),(172,5,'2014-01-13','10:46:41',NULL),(173,5,'2014-01-13','10:50:07',NULL),(174,5,'2014-01-13','10:55:41',NULL),(175,5,'2014-01-13','11:01:11',NULL),(176,5,'2014-01-13','11:24:03',NULL),(177,5,'2014-01-13','11:47:49',NULL),(178,5,'2014-01-13','12:37:24',NULL),(179,5,'2014-01-13','12:48:48',NULL),(180,5,'2014-01-13','12:50:10',NULL),(181,5,'2014-01-13','14:13:40',NULL),(182,5,'2014-01-13','14:22:46',NULL),(183,5,'2014-01-13','14:25:50',NULL),(184,5,'2014-01-13','14:32:53',NULL),(185,5,'2014-01-13','14:44:26',NULL),(186,5,'2014-01-13','15:13:56',NULL),(187,5,'2014-01-13','15:28:33',NULL),(188,5,'2014-01-13','15:43:16',NULL),(189,5,'2014-01-13','15:52:28',NULL),(190,5,'2014-01-13','15:54:14',NULL),(191,5,'2014-01-13','15:56:35',NULL),(192,5,'2014-01-13','16:21:09',NULL),(193,5,'2014-01-13','16:56:10',NULL),(194,5,'2014-01-13','17:24:46',NULL),(195,5,'2014-01-13','22:09:20',NULL),(196,5,'2014-01-13','22:12:30',NULL),(197,5,'2014-01-13','22:13:47',NULL),(198,5,'2014-01-13','22:15:18',NULL),(199,5,'2014-01-13','22:20:00',NULL),(200,5,'2014-01-13','22:27:47',NULL),(201,5,'2014-01-13','22:30:33',NULL),(202,5,'2014-01-13','22:35:09',NULL),(203,5,'2014-01-13','22:36:53',NULL),(204,5,'2014-01-13','22:47:04',NULL),(205,5,'2014-01-13','22:56:53',NULL),(206,5,'2014-01-13','22:59:41',NULL),(207,5,'2014-01-13','23:07:54',NULL),(208,5,'2014-01-13','23:23:08',NULL),(209,5,'2014-01-13','23:25:52',NULL),(210,5,'2014-01-13','23:28:24',NULL),(211,5,'2014-01-13','23:31:15',NULL),(212,5,'2014-01-14','11:40:23',NULL),(213,5,'2014-01-14','12:13:09',NULL),(214,5,'2014-01-14','12:14:52',NULL),(215,5,'2014-01-14','12:18:44',NULL),(216,5,'2014-01-14','12:35:33',NULL),(217,5,'2014-01-14','12:39:47',NULL),(218,5,'2014-01-14','12:42:12',NULL),(219,5,'2014-01-14','13:02:08',NULL),(220,5,'2014-01-14','13:05:15',NULL),(221,5,'2014-01-14','13:08:15',NULL),(222,5,'2014-01-14','13:15:08',NULL),(223,5,'2014-01-14','13:16:15',NULL),(224,5,'2014-01-14','13:17:04',NULL),(225,5,'2014-01-14','13:17:53',NULL),(226,5,'2014-01-14','13:20:34',NULL),(227,5,'2014-01-14','13:24:14',NULL),(228,5,'2014-01-14','13:27:32',NULL),(229,5,'2014-01-14','13:28:36',NULL),(230,5,'2014-01-14','13:31:37',NULL),(231,5,'2014-01-14','13:34:13',NULL),(232,5,'2014-01-14','13:56:16',NULL),(233,5,'2014-01-14','15:19:06',NULL),(234,5,'2014-01-14','15:25:49',NULL),(235,5,'2014-01-14','15:28:54',NULL),(236,5,'2014-01-14','15:30:48',NULL),(237,5,'2014-01-14','15:37:43',NULL),(238,5,'2014-01-14','15:44:40',NULL),(239,5,'2014-01-14','15:46:02',NULL),(240,5,'2014-01-14','15:46:58',NULL),(241,5,'2014-01-14','15:53:16',NULL),(242,5,'2014-01-14','15:57:37',NULL),(243,5,'2014-01-14','16:09:16',NULL),(244,5,'2014-01-14','16:11:12',NULL),(245,5,'2014-01-14','16:22:03',NULL),(246,5,'2014-01-14','16:23:10',NULL),(247,5,'2014-01-14','16:24:35',NULL),(248,5,'2014-01-14','18:33:13',NULL),(249,5,'2014-01-14','18:46:27',NULL),(250,5,'2014-01-14','19:33:57',NULL),(251,5,'2014-01-14','19:35:16',NULL),(252,5,'2014-01-14','19:38:37',NULL),(253,5,'2014-01-14','19:45:29',NULL),(254,5,'2014-01-14','19:47:06',NULL),(255,5,'2014-01-14','20:38:32',NULL),(256,5,'2014-01-14','20:40:53',NULL),(257,5,'2014-01-14','21:03:38',NULL),(258,5,'2014-01-14','21:04:48',NULL),(259,5,'2014-01-14','21:21:00',NULL),(260,5,'2014-03-31','10:17:26',NULL),(261,5,'2014-03-31','10:22:14',NULL),(262,5,'2014-03-31','10:22:16',NULL),(263,5,'2014-03-31','14:26:09',NULL),(264,5,'2014-03-31','14:36:31',NULL),(265,5,'2014-03-31','14:40:51',NULL),(266,5,'2014-03-31','15:15:07',NULL),(267,5,'2014-03-31','15:20:10',NULL),(268,5,'2014-03-31','15:41:02',NULL),(269,5,'2014-03-31','15:49:02',NULL),(270,5,'2014-03-31','16:12:12',NULL),(271,5,'2014-03-31','16:23:24',NULL),(272,5,'2014-03-31','16:36:44',NULL),(273,5,'2014-03-31','16:50:43',NULL),(274,5,'2014-03-31','16:55:34',NULL),(275,5,'2014-03-31','17:18:17',NULL),(276,5,'2014-03-31','17:19:30',NULL),(277,5,'2014-03-31','17:22:29',NULL),(278,5,'2014-03-31','17:22:54',NULL),(279,5,'2014-03-31','17:23:49',NULL),(280,5,'2014-03-31','17:24:25',NULL),(281,5,'2014-03-31','17:38:17',NULL),(282,5,'2014-03-31','17:38:59',NULL),(283,5,'2014-03-31','23:58:27',NULL),(284,5,'2014-04-01','13:48:16',NULL),(285,5,'2014-04-01','13:48:56',NULL),(286,5,'2014-04-01','15:13:39',NULL),(287,5,'2014-04-01','16:36:32',NULL),(288,5,'2014-04-01','16:51:08',NULL),(289,5,'2014-04-01','17:04:58',NULL),(290,5,'2014-04-01','22:30:58',NULL),(291,5,'2014-04-02','11:21:23',NULL),(292,5,'2014-04-03','10:22:06',NULL),(293,5,'2014-04-08','11:29:19',NULL),(294,5,'2014-04-08','11:31:39',NULL);
/*!40000 ALTER TABLE `loginhistroy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purcheorder`
--

DROP TABLE IF EXISTS `purcheorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purcheorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(45) NOT NULL,
  `purchase_order_number` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `lastupdatedate` varchar(45) DEFAULT NULL,
  `complete` int(11) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `accepttodatabasetime` varchar(45) DEFAULT NULL,
  `accepttodatabasedate` date DEFAULT NULL,
  `accepttodatabase` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `admin_purchase_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purcheorder`
--

LOCK TABLES `purcheorder` WRITE;
/*!40000 ALTER TABLE `purcheorder` DISABLE KEYS */;
INSERT INTO `purcheorder` VALUES (1,'2014-04-08','P00001',1,610,NULL,1,'09:32:19',NULL,NULL,0,'dinesh',1);
/*!40000 ALTER TABLE `purcheorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchesorderdetail`
--

DROP TABLE IF EXISTS `purchesorderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchesorderdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemid` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `unitprice` varchar(45) DEFAULT NULL,
  `orderId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchesorderdetail_purcheOrder1_idx` (`orderId`),
  CONSTRAINT `fk_purchesorderdetail_purcheOrder1` FOREIGN KEY (`orderId`) REFERENCES `purcheorder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchesorderdetail`
--

LOCK TABLES `purchesorderdetail` WRITE;
/*!40000 ALTER TABLE `purchesorderdetail` DISABLE KEYS */;
INSERT INTO `purchesorderdetail` VALUES (1,13,10,'1','22.5',1),(2,29,10,'1','38.5',1);
/*!40000 ALTER TABLE `purchesorderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesorder`
--

DROP TABLE IF EXISTS `salesorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesorder` (
  `OrderID` int(11) NOT NULL AUTO_INCREMENT,
  `sales_order_no` varchar(45) DEFAULT NULL,
  `OrderDate` varchar(20) DEFAULT NULL,
  `CustomerID` int(11) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `labour_amount` double DEFAULT NULL,
  `paid_amount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesorder`
--

LOCK TABLES `salesorder` WRITE;
/*!40000 ALTER TABLE `salesorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesorderdetail`
--

DROP TABLE IF EXISTS `salesorderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesorderdetail` (
  `detailid` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`detailid`),
  KEY `oid_idx` (`orderid`),
  KEY `iid_idx` (`itemid`),
  KEY `orderid_idx` (`orderid`),
  CONSTRAINT `itemid` FOREIGN KEY (`itemid`) REFERENCES `item` (`ItemID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orderid` FOREIGN KEY (`orderid`) REFERENCES `salesorder` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesorderdetail`
--

LOCK TABLES `salesorderdetail` WRITE;
/*!40000 ALTER TABLE `salesorderdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesorderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `StockID` int(11) NOT NULL AUTO_INCREMENT,
  `SupplierID` int(11) NOT NULL,
  `StockDate` varchar(20) NOT NULL,
  `DeliverdTime` varchar(10) NOT NULL,
  `Status` int(11) NOT NULL,
  PRIMARY KEY (`StockID`),
  KEY `Supplier_idx` (`SupplierID`),
  CONSTRAINT `Supplier` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stockdetail`
--

DROP TABLE IF EXISTS `stockdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stockdetail` (
  `DetailID` int(11) NOT NULL AUTO_INCREMENT,
  `StockID` int(11) NOT NULL,
  `ItemID` int(11) NOT NULL,
  `StockQuantity` int(11) NOT NULL,
  `remainingquantity` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`DetailID`),
  KEY `SID_idx` (`StockID`),
  KEY `Iid_idx` (`ItemID`),
  CONSTRAINT `Iid` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SID` FOREIGN KEY (`StockID`) REFERENCES `stock` (`StockID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockdetail`
--

LOCK TABLES `stockdetail` WRITE;
/*!40000 ALTER TABLE `stockdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `stockdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `SupplierID` int(11) NOT NULL AUTO_INCREMENT,
  `SupplierName` varchar(45) NOT NULL,
  `AddedDate` varchar(20) NOT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `ContactNo` varchar(20) NOT NULL,
  `Status` int(11) NOT NULL,
  PRIMARY KEY (`SupplierID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'TATA Dimo','2014-02-16','TATA Dimo','0112354788',1),(2,'Bajaj Motors','2014-02-16','Dikwella,Matara111','0414989853',1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_customer_return`
--

DROP TABLE IF EXISTS `tbl_customer_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_customer_return` (
  `customer_return_id` int(11) NOT NULL AUTO_INCREMENT,
  `sales_order_id` int(11) DEFAULT NULL,
  `return_date` varchar(45) DEFAULT NULL,
  `return_time` varchar(45) DEFAULT NULL,
  `amount` double NOT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`customer_return_id`),
  KEY `fk_sales_order_id_return_idx` (`sales_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_customer_return`
--

LOCK TABLES `tbl_customer_return` WRITE;
/*!40000 ALTER TABLE `tbl_customer_return` DISABLE KEYS */;
INSERT INTO `tbl_customer_return` VALUES (1,8,'2014-03-25','22:41:04',0,NULL),(2,8,'2014-03-25','22:46:48',0,NULL),(3,8,'2014-03-25','22:48:52',0,NULL),(4,8,'2014-03-26','08:05:51',0,NULL),(5,8,'2014-03-26','08:08:32',0,1),(6,8,'2014-03-26','08:14:31',0,1),(7,8,'2014-03-26','08:22:02',0,1),(8,8,'2014-03-26','08:22:28',0,1),(9,8,'2014-03-26','08:24:28',0,1),(10,8,'2014-03-26','08:24:43',2,1),(11,8,'2014-04-04','10:04:46',18,1),(12,1,'2014-04-04','11:24:52',42,1),(13,8,'2014-04-07','09:44:19',2,1),(14,8,'2014-04-07','11:15:52',36,1);
/*!40000 ALTER TABLE `tbl_customer_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_customer_return_detail`
--

DROP TABLE IF EXISTS `tbl_customer_return_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_customer_return_detail` (
  `customer_return_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_return_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `return_quantity` double DEFAULT NULL,
  `unit_price` double NOT NULL,
  `reason` varchar(50) NOT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`customer_return_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_customer_return_detail`
--

LOCK TABLES `tbl_customer_return_detail` WRITE;
/*!40000 ALTER TABLE `tbl_customer_return_detail` DISABLE KEYS */;
INSERT INTO `tbl_customer_return_detail` VALUES (1,6,11,2,0,'Check',NULL),(2,6,12,2,0,'BH',NULL),(3,7,11,1,0,'dc',1),(4,7,12,2,0,'hh',1),(5,8,11,1,0,'dc',1),(6,8,12,2,0,'hh',1),(7,9,12,1,0,'dc',1),(8,10,12,1,0,'dc',1),(9,10,11,1,0,'d',1),(10,11,12,4,0,'d',1),(11,11,11,2,0,'vs',1),(12,12,3,1,0,'jo',1),(13,12,4,2,0,'ho',1),(14,13,12,1,2,'sac',1),(15,14,11,4,5,'bg',1),(16,14,12,8,2,'fevr',1);
/*!40000 ALTER TABLE `tbl_customer_return_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_customer_type`
--

DROP TABLE IF EXISTS `tbl_customer_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_customer_type` (
  `customer_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_type_name` varchar(500) NOT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`customer_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_customer_type`
--

LOCK TABLES `tbl_customer_type` WRITE;
/*!40000 ALTER TABLE `tbl_customer_type` DISABLE KEYS */;
INSERT INTO `tbl_customer_type` VALUES (1,'Wholesale','10:00:00','2014-02-02',1),(2,'Retail','10:00:00','2014-02-02',1),(3,'Garage','10:00:00','2014-02-02',1);
/*!40000 ALTER TABLE `tbl_customer_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_dealer_return`
--

DROP TABLE IF EXISTS `tbl_dealer_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_dealer_return` (
  `dealer_return_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_purchase_order_id` int(11) DEFAULT NULL,
  `invoice_no` varchar(45) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `admin_return_id` int(11) DEFAULT '0',
  `admin_user_name` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`dealer_return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_dealer_return`
--

LOCK TABLES `tbl_dealer_return` WRITE;
/*!40000 ALTER TABLE `tbl_dealer_return` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_dealer_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_dealer_return_detail`
--

DROP TABLE IF EXISTS `tbl_dealer_return_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_dealer_return_detail` (
  `dealer_return_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `dealer_return_id` int(11) DEFAULT NULL,
  `admin_return_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `return_qty` double DEFAULT NULL,
  `dealer_return_reason` varchar(5000) DEFAULT NULL,
  `admin_return_status` int(11) DEFAULT NULL,
  `delivered` varchar(45) DEFAULT NULL,
  `admin_comment` varchar(5000) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`dealer_return_detail_id`),
  KEY `fk_dealer_return_id_idx` (`dealer_return_id`),
  CONSTRAINT `fk_dealer_return_id` FOREIGN KEY (`dealer_return_id`) REFERENCES `tbl_dealer_return` (`dealer_return_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_dealer_return_detail`
--

LOCK TABLES `tbl_dealer_return_detail` WRITE;
/*!40000 ALTER TABLE `tbl_dealer_return_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_dealer_return_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_item_alternatives`
--

DROP TABLE IF EXISTS `tbl_item_alternatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_item_alternatives` (
  `alternative_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `alternative_item_id` int(11) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`alternative_id`),
  KEY `fk_item_id_idx` (`item_id`),
  CONSTRAINT `fk_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`ItemID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_item_alternatives`
--

LOCK TABLES `tbl_item_alternatives` WRITE;
/*!40000 ALTER TABLE `tbl_item_alternatives` DISABLE KEYS */;
INSERT INTO `tbl_item_alternatives` VALUES (1,13,'rwterte',37,'2014-18-02','14:18:9',1),(2,37,'rwterte',13,'2014-18-02','14:18:9',1),(3,10,'rptorter',100,'2014-20-02','14:20:7',1),(4,100,'rptorter',10,'2014-20-02','14:20:7',1);
/*!40000 ALTER TABLE `tbl_item_alternatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_item_type`
--

DROP TABLE IF EXISTS `tbl_item_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_item_type` (
  `item_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_type_name` varchar(200) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_item_type`
--

LOCK TABLES `tbl_item_type` WRITE;
/*!40000 ALTER TABLE `tbl_item_type` DISABLE KEYS */;
INSERT INTO `tbl_item_type` VALUES (1,'Type1','Type1','2014-04-04','10:00:00',1),(2,'Type2','Type2','2014-04-04','10:00:00',1),(3,'Type3','Type3','2014-04-04','10:00:00',1),(4,'Type4','Type4','2014-04-04','10:00:00',1),(5,'Type5','Type5','2014-04-04','10:00:00',1);
/*!40000 ALTER TABLE `tbl_item_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_search_key`
--

DROP TABLE IF EXISTS `tbl_search_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_search_key` (
  `search_key_id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(500) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`search_key_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_search_key`
--

LOCK TABLES `tbl_search_key` WRITE;
/*!40000 ALTER TABLE `tbl_search_key` DISABLE KEYS */;
INSERT INTO `tbl_search_key` VALUES (1,'Batta Clutch Plates',NULL,'2014-04-04','10:00:00',1),(2,'Batta Brake Liners',NULL,'2014-04-04','10:00:00',1),(3,'Batti Clutch Paltes',NULL,'2014-04-04','10:00:00',1),(4,'CT100 5465',NULL,'2014-04-04','10:00:00',1),(5,'FZ3341354',NULL,'2014-04-04','10:00:00',1),(6,'34234','324242','2014-04-07','00:59:59',0);
/*!40000 ALTER TABLE `tbl_search_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_vehicle_models`
--

DROP TABLE IF EXISTS `tbl_vehicle_models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_vehicle_models` (
  `vehicle_model_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_model_name` varchar(500) NOT NULL,
  `added_date` varchar(45) DEFAULT NULL,
  `added_time` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`vehicle_model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_vehicle_models`
--

LOCK TABLES `tbl_vehicle_models` WRITE;
/*!40000 ALTER TABLE `tbl_vehicle_models` DISABLE KEYS */;
INSERT INTO `tbl_vehicle_models` VALUES (1,'Batta','2014-02-02','5:10',1),(2,'LCV','2014-02-02','5:10',1),(5,'MCV','2014-02-02','5:10',1),(6,'HCV','2014-02-02','5:10',1),(7,'Nano','2014-02-02','5:10',1);
/*!40000 ALTER TABLE `tbl_vehicle_models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `privilage` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'admin','admin','*4ACFE3202A5FF5CF467898FC58AAB1D615029441',0,1,'2013-11-16','12:05:52','Admin'),(6,'lok','user','*D5D9F81F5542DE067FFF5FF7A4CA4BDD322C578F',0,1,'2013-11-16','12:07:47','User');
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

-- Dump completed on 2014-04-08 11:56:56
