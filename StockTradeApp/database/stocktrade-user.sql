CREATE DATABASE  IF NOT EXISTS `stocktrade`;
USE `stocktrade`;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roleid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FK2ovmsl4hvm5vu1w8i308r5j6w` (`roleid`),
  CONSTRAINT `FK2ovmsl4hvm5vu1w8i308r5j6w` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_UserRole` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user` WRITE;

INSERT INTO `user` VALUES (1,'AYBUKE','USTUN','aybuke@gmail.com','aybuke','$2a$10$mxuzwM/sUJxyJ2jli7H2U.c9gUGlSIN9NtyBlRPN7TFMVxoKZUtJS',1);

UNLOCK TABLES;