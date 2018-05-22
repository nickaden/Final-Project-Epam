CREATE DATABASE  IF NOT EXISTS `epam-jwd-final` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `epam-jwd-final`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: epam-jwd-final
-- ------------------------------------------------------
-- Server version	5.7.20-log


--
-- Table structure for table `an_mark`
--

DROP TABLE IF EXISTS `an_mark`;

CREATE TABLE `an_mark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `an_id` int(11) NOT NULL,
  `type` enum('UP','DOWN') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`an_id`,`owner_id`),
  KEY `answer_mark_FK_idx` (`an_id`),
  KEY `user_an_mark_FK_idx` (`owner_id`),
  CONSTRAINT `answer_an_mark_FK` FOREIGN KEY (`an_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_an_mark_FK` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;

CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `q_id` int(11) NOT NULL,
  `cr_date` datetime DEFAULT NULL,
  `description` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_answer_FK_idx` (`owner_id`),
  KEY `question_answer_FK` (`q_id`),
  CONSTRAINT `question_answer_FK` FOREIGN KEY (`q_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_answer___fk` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;

CREATE TABLE `language` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `language_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `q_mark`
--

DROP TABLE IF EXISTS `q_mark`;

CREATE TABLE `q_mark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `q_id` int(11) NOT NULL,
  `type` enum('UP','DOWN') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`q_id`,`owner_id`),
  KEY `qu_mark_FK_idx` (`q_id`),
  KEY `user_mark_FK_idx` (`owner_id`),
  CONSTRAINT `qu_mark_FK` FOREIGN KEY (`q_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_mark_FK` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lang_id` int(11) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `cr_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `question_title_uindex` (`title`,`lang_id`),
  KEY `quest_user_FK_idx` (`owner_id`),
  KEY `language_question___fk` (`lang_id`),
  CONSTRAINT `language_question___fk` FOREIGN KEY (`lang_id`) REFERENCES `language` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_user_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question_tag`
--

DROP TABLE IF EXISTS `question_tag`;

CREATE TABLE `question_tag` (
  `tag_id` int(11) NOT NULL,
  `q_id` int(11) NOT NULL,
  PRIMARY KEY (`tag_id`,`q_id`),
  KEY `question_FK_idx` (`q_id`),
  CONSTRAINT `question_FK` FOREIGN KEY (`q_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tag_FK` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `solution`
--

DROP TABLE IF EXISTS `solution`;

CREATE TABLE `solution` (
  `q_id` int(11) NOT NULL,
  `an_id` int(11) NOT NULL,
  PRIMARY KEY (`q_id`,`an_id`),
  UNIQUE KEY `q_id_UNIQUE` (`q_id`),
  KEY `answer_solution_idx` (`an_id`),
  CONSTRAINT `answer_solution` FOREIGN KEY (`an_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_solution_FK` FOREIGN KEY (`q_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `tag_title_uindex` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `reg_date` date DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT 'USER',
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `image_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

