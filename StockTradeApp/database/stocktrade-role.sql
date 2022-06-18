CREATE DATABASE  IF NOT EXISTS `stocktrade`;
USE `stocktrade`;


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` char(255) DEFAULT NULL,
  `grantedAuthority` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (0,'ROLE_USER',NULL),(1,'ROLE_ADMIN',NULL);
UNLOCK TABLES;