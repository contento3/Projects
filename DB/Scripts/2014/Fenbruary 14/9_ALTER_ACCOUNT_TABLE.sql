ALTER TABLE `cms`.`account` ADD `ACCOUNT_UUID` varchar(150);
ALTER TABLE `cms`.`account` ADD UNIQUE (`ACCOUNT_UUID`);