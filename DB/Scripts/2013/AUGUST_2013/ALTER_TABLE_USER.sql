ALTER TABLE `platform_users`.`users` ADD COLUMN `FIRST_NAME` VARCHAR(100) NOT NULL  AFTER `ACCOUNT_ID` , ADD COLUMN `LAST_NAME` VARCHAR(100) NOT NULL  AFTER `FIRST_NAME` , ADD COLUMN `EMAIL` VARCHAR(256) NULL  AFTER `LAST_NAME`;

ALTER TABLE `platform_users`.`user` ADD COLUMN `FIRST_NAME` VARCHAR(100) NOT NULL  AFTER `ACCOUNT_ID` , ADD COLUMN `LAST_NAME` VARCHAR(100) NOT NULL  AFTER `FIRST_NAME` , ADD COLUMN `EMAIL` VARCHAR(256) NULL  AFTER `LAST_NAME`;

ALTER TABLE `platform_users`.`user` CHANGE COLUMN `PASSWORD` `PASSWORD` VARCHAR(250) NOT NULL;


insert into user (username,password,enabled,salt,account_id,first_name,last_name,email) select username,password,enabled,salt,account_id,first_name,last_name,email from users;