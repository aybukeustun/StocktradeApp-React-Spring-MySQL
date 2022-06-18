CREATE DATABASE  IF NOT EXISTS `stocktrade`;
USE `stocktrade`;

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price`double DEFAULT NULL ,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `stock` WRITE;



UNLOCK TABLES;