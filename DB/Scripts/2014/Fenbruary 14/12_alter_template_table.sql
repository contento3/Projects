ALTER TABLE `cms`.`TEMPLATE` ADD `TEMPLATE_KEY` varchar(150) NULL AFTER `TEMPLATE_NAME`;
ALTER TABLE `cms`.`TEMPLATE` ADD UNIQUE (`TEMPLATE_KEY`);