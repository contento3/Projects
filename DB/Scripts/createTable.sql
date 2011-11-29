SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `cms` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `cms`;

-- -----------------------------------------------------
-- Table `cms`.`CONTENT_TYPE_TEMPLATES`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`CONTENT_TYPE_TEMPLATES` (
  `CONTENT_TYPE_TEMPLATES_ID` INT NOT NULL ,
  `CONTENT_TYPE_ID` INT NOT NULL ,
  `COMPONENT_CONTENT_TYPE_ID` INT NOT NULL ,
  `ORDER` INT NOT NULL ,
  PRIMARY KEY (`CONTENT_TYPE_TEMPLATES_ID`) ,
  INDEX `CONTENT_TYPE_ID_FK` (`CONTENT_TYPE_TEMPLATES_ID` ASC) ,
  CONSTRAINT `CONTENT_TYPE_ID_FK`
    FOREIGN KEY (`CONTENT_TYPE_TEMPLATES_ID` )
    REFERENCES `cms`.`CONTENT_TYPE_TEMPLATES` (`CONTENT_TYPE_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `cms`.`CONTENT_TYPE_INSTANCE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`CONTENT_TYPE_INSTANCE` (
  `CONTENT_TYPE_INSTANCE_ID` INT NOT NULL ,
  `CONTENT_TYPE_ID` INT NOT NULL ,
  PRIMARY KEY (`CONTENT_TYPE_INSTANCE_ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `cms`.`CONTENT`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`CONTENT` (
  `CONTENT_ID` INT NOT NULL ,
  `CONTENT_TYPE_TEMPLATE_ID` INT NOT NULL ,
  `VALUE` VARCHAR(45) NOT NULL ,
  `CONTENT_TYPE_INSTANCE_ID` INT NOT NULL ,
  PRIMARY KEY (`CONTENT_ID`) ,
  INDEX `CONTENT_TYPE_TEMPLATE_ID_FK` (`CONTENT_TYPE_TEMPLATE_ID` ASC) ,
  INDEX `CONTENT_TYPE_INSTANCE_ID` (`CONTENT_TYPE_INSTANCE_ID` ASC) ,
  CONSTRAINT `CONTENT_TYPE_TEMPLATE_ID_FK`
    FOREIGN KEY (`CONTENT_TYPE_TEMPLATE_ID` )
    REFERENCES `cms`.`CONTENT_TYPE_TEMPLATES` (`CONTENT_TYPE_TEMPLATES_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CONTENT_TYPE_INSTANCE_ID`
    FOREIGN KEY (`CONTENT_TYPE_INSTANCE_ID` )
    REFERENCES `cms`.`CONTENT_TYPE_INSTANCE` (`CONTENT_TYPE_INSTANCE_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `cms`.`SITE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`SITE` (
  `SITE_ID` INT NOT NULL ,
  `SITE_NAME` VARCHAR(45) NOT NULL ,
  `URL` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`SITE_ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `cms`.`CONTENT_TYPE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`CONTENT_TYPE` (
  `CONTENT_TYPE_ID` INT NOT NULL COMMENT 'This column is primary key of this table used to identify each content type.' ,
  `CONTENT_TYPE_NAME` VARCHAR(45) NOT NULL COMMENT 'Content type name' ,
  `SITE_ID` INT NULL COMMENT 'This column identifies whether the content_type is created for specific site or the content type is system wide (i.e. for all sites)?' ,
  `IS_COMPOSED` BIT NULL DEFAULT 0 ,
  PRIMARY KEY (`CONTENT_TYPE_ID`) ,
  INDEX `SITE_ID` (`SITE_ID` ASC) ,
  CONSTRAINT `SITE_ID`
    FOREIGN KEY (`SITE_ID` )
    REFERENCES `cms`.`SITE` (`SITE_ID` )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `cms`.`PAGE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`PAGE` (
  `PAGE_ID` INT NOT NULL COMMENT 'ID of a page' ,
  `PAGE_URL` VARCHAR(45) NULL ,
  `SITE_ID` INT NULL ,
  `PARENT_ID` INT NULL ,
  PRIMARY KEY (`PAGE_ID`) ,
  INDEX `site_page_fk` (`SITE_ID` ASC) ,
  CONSTRAINT `site_page_fk`
    FOREIGN KEY (`SITE_ID` )
    REFERENCES `cms`.`SITE` (`SITE_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'This table represents page of a website';


-- -----------------------------------------------------
-- Table `cms`.`CONTENT_REF`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cms`.`CONTENT_REF` (
  `CONTENT_REF_ID` INT NOT NULL COMMENT 'PRIMARY_KEY' ,
  `CONTENT_ID` INT NULL COMMENT 'This is id of the CONTENT which is refering other content' ,
  `CONTENT_ID_REF_TO` INT NULL COMMENT 'Content Id of a content which is being referred by the content_id column' ,
  PRIMARY KEY (`CONTENT_REF_ID`) ,
  INDEX `CONTENT_ID_FK` (`CONTENT_ID` ASC) ,
  CONSTRAINT `CONTENT_ID_FK`
    FOREIGN KEY (`CONTENT_ID` )
    REFERENCES `cms`.`CONTENT` (`CONTENT_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'This table specifies content association to other content';



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
