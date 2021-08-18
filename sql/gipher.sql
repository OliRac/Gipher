CREATE TABLE `gipher`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT ;
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `photo` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));
