CREATE DATABASE  IF NOT EXISTS `stocktrade`;
USE `stocktrade`;

DROP TABLE IF EXISTS `transaction`;

CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `stock_id` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `count` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `kullanici_id` (`user_id`),
  KEY `hisse_id` (`stock_id`),
  CONSTRAINT `FKlhqnenc4bwsw1v2vit8ohoffj` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `FKsg7jp0aj6qipr50856wf6vbw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `transaction` WRITE;



UNLOCK TABLES;