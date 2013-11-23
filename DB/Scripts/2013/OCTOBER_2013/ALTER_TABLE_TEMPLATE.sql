ALTER TABLE `cms`.`template` ADD COLUMN `TEMPLATE_CATEGORY_ID` INT NULL  AFTER `ACCOUNT_ID` , 
  ADD CONSTRAINT `FK_TEMPLATE_TMPLT_CATEGORY`
  FOREIGN KEY (`TEMPLATE_CATEGORY_ID` )
  REFERENCES `cms`.`template_categories` (`TEMPLATE_CATEGORY_ID` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `FK_TEMPLATE_TMPLT_CATEGORY_idx` (`TEMPLATE_CATEGORY_ID` ASC) ;

select * from template;
delete from template where template_id = 5;

insert into template_Categories values (1,'registerSuccess','Template defined when user is registered successully');

select * from template; 

update template set template_category_id=1 where template_id = 6;