CREATE SCHEMA IF NOT EXISTS gipher;

CREATE TABLE IF NOT EXISTS `gipher`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `photo` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`)
);

CREATE USER IF NOT EXISTS "gipher"@"localhost" IDENTIFIED BY "password";
GRANT ALL PRIVILEGES ON gipher.* TO "gipher"@"localhost";