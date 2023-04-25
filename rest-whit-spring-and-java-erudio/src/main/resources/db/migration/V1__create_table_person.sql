
CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `person` VALUES (1,'Rua Primeiro de Abril Formiga','Italo Cesar Castro','M','Castro'),(2,'Rua Primeiro de Abril Formiga','Camilo','M','Neves'),(3,'Rua Primeiro de Abril Formiga','Jose Neves','M','Zeze'),(4,'Rua Primeiro de Abril Formiga','Jose Neves','M','Zeze');

 