ALTER TABLE `crm`.`users` DROP FOREIGN KEY `users_ibfk_1` ;
ALTER TABLE `crm`.`users` DROP COLUMN `ACCOUNT_ID` 
, DROP INDEX `ACCOUNT_ID` ;