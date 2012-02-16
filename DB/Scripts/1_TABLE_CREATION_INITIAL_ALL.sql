SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';



CREATE SCHEMA IF NOT EXISTS `cms` DEFAULT CHARACTER SET latin1 ;

USE `cms` ;



-- -----------------------------------------------------

-- Table `cmstest`.`account`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`account` (

  `account_id` INT(11) NOT NULL ,

  `account_name` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`account_id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1

COMMENT = 'Used to represent an account for each of the customer.' ;





-- -----------------------------------------------------

-- Table `cmstest`.`article`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`article` (

  `ARTICLE_UUID` INT(11) NOT NULL AUTO_INCREMENT ,

  `HEAD` VARCHAR(45) NULL DEFAULT NULL ,

  `TEASER` VARCHAR(45) NULL DEFAULT NULL ,

  `BODY` VARCHAR(45) NULL DEFAULT NULL ,

  `DATE_CREATED` VARCHAR(45) NULL DEFAULT NULL ,

  `DATE_POSTED` VARCHAR(45) NULL DEFAULT NULL ,

  `ACCOUNT_ID` VARCHAR(45) NULL DEFAULT NULL ,

  PRIMARY KEY (`ARTICLE_UUID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `cmstest`.`image`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`image` (

  `IMAGE_UUID` VARCHAR(75) NOT NULL ,

  `NAME` VARCHAR(45) NOT NULL ,

  `ALT_TEXT` VARCHAR(45) NULL DEFAULT NULL ,

  `ACCOUNT_ID` VARCHAR(45) NOT NULL ,

  `IMAGE` MEDIUMBLOB NULL DEFAULT NULL ,

  PRIMARY KEY (`IMAGE_UUID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1, 

COMMENT = 'Used to store image and related information.' ;





-- -----------------------------------------------------

-- Table `cmstest`.`page_layout_type`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`page_layout_type` (

  `PAGE_LAYOUT_TYPE_ID` INT(11) NOT NULL ,

  `PAGE_LAYOUT_TYPE_NAME` VARCHAR(45) NOT NULL ,

  `DESCRIPTION` VARCHAR(500) NOT NULL ,

  PRIMARY KEY (`PAGE_LAYOUT_TYPE_ID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1, 

COMMENT = 'Used to specify different type of page layout from which the' ;





-- -----------------------------------------------------

-- Table `cmstest`.`page_layout`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`page_layout` (

  `PAGE_LAYOUT_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `NAME` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `ACCOUNT_ID` INT(11) NOT NULL ,

  `PAGE_LAYOUT_TYPE_ID` INT(11) NOT NULL ,

  PRIMARY KEY (`PAGE_LAYOUT_ID`) ,

  INDEX `ACCOUNT_ID_FK` (`ACCOUNT_ID` ASC) ,

  INDEX `PAGE_LAYOUT_TYPE_ID` (`PAGE_LAYOUT_TYPE_ID` ASC) ,

  CONSTRAINT `ACCOUNT_ID_FK`

    FOREIGN KEY (`ACCOUNT_ID` )

    REFERENCES `cms`.`account` (`account_id` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `PAGE_LAYOUT_TYPE_ID`

    FOREIGN KEY (`PAGE_LAYOUT_TYPE_ID` )

    REFERENCES `cms`.`page_layout_type` (`PAGE_LAYOUT_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 17

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci;





-- -----------------------------------------------------

-- Table `cmstest`.`site`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`site` (

  `SITE_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `SITE_NAME` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `URL` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `ACCOUNT_ID` INT(11) NOT NULL DEFAULT '1' ,

  PRIMARY KEY (`SITE_ID`) ,

  INDEX `account_id` (`ACCOUNT_ID` ASC) ,

  CONSTRAINT `account_id`

    FOREIGN KEY (`ACCOUNT_ID` )

    REFERENCES `cms`.`account` (`account_id` )

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 9

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci;





-- -----------------------------------------------------

-- Table `cmstest`.`page`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`page` (

  `PAGE_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID of a page' ,

  `PAGE_URI` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `SITE_ID` INT(11) NOT NULL ,

  `PARENT_ID` INT(11) NULL DEFAULT NULL ,

  `PAGE_TITLE` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `PAGE_LAYOUT_ID` INT(11) NULL DEFAULT '1' ,

  PRIMARY KEY (`PAGE_ID`) ,

  INDEX `site_page_fk` (`SITE_ID` ASC) ,

  INDEX `page_layout_fk` (`PAGE_LAYOUT_ID` ASC) ,

  CONSTRAINT `page_layout_fk`

    FOREIGN KEY (`PAGE_LAYOUT_ID` )

    REFERENCES `cms`.`page_layout` (`PAGE_LAYOUT_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `site_page_fk`

    FOREIGN KEY (`SITE_ID` )

    REFERENCES `cms`.`site` (`SITE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 102

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci, 

COMMENT = 'This table represents page of a website' ;





-- -----------------------------------------------------

-- Table `cmstest`.`page_section_type`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`page_section_type` (

  `PAGE_SECTION_TYPE_ID` INT(11) NOT NULL ,

  `PAGE_SECTION_TYPE_NAME` VARCHAR(45) NOT NULL ,

  `DESCRIPTION` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`PAGE_SECTION_TYPE_ID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `cmstest`.`page_section`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`page_section` (

  `PAGE_SECTION_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `PAGE_LAYOUT_ID` INT(11) NOT NULL ,

  `TEMPLATE_MARKUP` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `PAGE_SECTION_TYPE_ID` INT(11) NOT NULL ,

  `NAME` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `DESCRIPTION` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL ,

  PRIMARY KEY (`PAGE_SECTION_ID`) ,

  INDEX `FK_PAGE_LAYOUT_PAGE_SECTION` (`PAGE_LAYOUT_ID` ASC) ,

  INDEX `FK_SECTION_TYPE_SECTION` (`PAGE_SECTION_TYPE_ID` ASC) ,

  CONSTRAINT `FK_PAGE_LAYOUT_PAGE_SECTION`

    FOREIGN KEY (`PAGE_LAYOUT_ID` )

    REFERENCES `cms`.`page_layout` (`PAGE_LAYOUT_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `FK_SECTION_TYPE_SECTION`

    FOREIGN KEY (`PAGE_SECTION_TYPE_ID` )

    REFERENCES `cms`.`page_section_type` (`PAGE_SECTION_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 7

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci;





-- -----------------------------------------------------

-- Table `cmstest`.`template_directory`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`template_directory` (

  `TEMPLATE_DIRECTORY_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `DIRECTORY_NAME` VARCHAR(45) NOT NULL ,

  `PARENT` INT(11) NULL DEFAULT NULL ,

  `IS_GLOBAL` INT(11) NOT NULL DEFAULT '0' ,

  PRIMARY KEY (`TEMPLATE_DIRECTORY_ID`) )

ENGINE = InnoDB

AUTO_INCREMENT = 7

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `cmstest`.`template_type`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`template_type` (

  `TEMPLATE_TYPE_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `TEMPLATE_TYPE_NAME` VARCHAR(45) NOT NULL ,

  `DESCRIPTION` VARCHAR(200) NULL DEFAULT NULL ,

  `CONTENT_TYPE` VARCHAR(25) NOT NULL ,

  PRIMARY KEY (`TEMPLATE_TYPE_ID`) )

ENGINE = InnoDB

AUTO_INCREMENT = 7

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `cmstest`.`template`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`template` (

  `TEMPLATE_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `TEMPLATE_TYPE_ID` INT(11) NOT NULL ,

  `IS_GLOBAL` BINARY(1) NOT NULL DEFAULT '0' ,

  `TEMPLATE_NAME` VARCHAR(100) NOT NULL ,

  `TEMPLATE_TEXT` TEXT NOT NULL ,

  `TEMPLATE_DIRECTORY_ID` INT(11) NOT NULL ,

  `ACCOUNT_ID` INT(11) NOT NULL ,

  PRIMARY KEY (`TEMPLATE_ID`) ,

  INDEX `FK_TEMPLATE_TEMPLATE_TYPE` (`TEMPLATE_TYPE_ID` ASC) ,

  INDEX `FK_TEMPLATE_DIRECTORY` (`TEMPLATE_DIRECTORY_ID` ASC) ,

  INDEX `FK_TEMPLATE_ACCOUNT` (`ACCOUNT_ID` ASC) ,

  CONSTRAINT `FK_TEMPLATE_ACCOUNT`

    FOREIGN KEY (`ACCOUNT_ID` )

    REFERENCES `cms`.`account` (`account_id` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `FK_TEMPLATE_DIRECTORY`

    FOREIGN KEY (`TEMPLATE_DIRECTORY_ID` )

    REFERENCES `cms`.`template_directory` (`TEMPLATE_DIRECTORY_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `FK_TEMPLATE_TEMPLATE_TYPE`

    FOREIGN KEY (`TEMPLATE_TYPE_ID` )

    REFERENCES `cms`.`template_type` (`TEMPLATE_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 7

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `cmstest`.`page_template_association`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `cms`.`page_template_association` (

  `PAGE_ID` INT(11) NOT NULL ,

  `TEMPLATE_ID` INT(11) NOT NULL ,

  `PAGE_SECTION_TYPE_ID` INT(11) NOT NULL ,

  `TEMPLATE_ORDER` INT(11) NULL DEFAULT NULL ,

  PRIMARY KEY (`PAGE_ID`, `TEMPLATE_ID`, `PAGE_SECTION_TYPE_ID`) ,

  INDEX `PAGE_PAGE_TEMPLATE_ASSOC` (`PAGE_ID` ASC) ,

  INDEX `SECTION_TYPE_PAGE_TEMPLATE_ASSOC` (`PAGE_SECTION_TYPE_ID` ASC) ,

  INDEX `TEMPLATE_PAGE_TEMPLATE_ASSOC` (`TEMPLATE_ID` ASC) ,

  CONSTRAINT `PAGE_PAGE_TEMPLATE_ASSOC`

    FOREIGN KEY (`PAGE_ID` )

    REFERENCES `cms`.`page` (`PAGE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `SECTION_TYPE_PAGE_TEMPLATE_ASSOC`

    FOREIGN KEY (`PAGE_SECTION_TYPE_ID` )

    REFERENCES `cms`.`page_section_type` (`PAGE_SECTION_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `TEMPLATE_PAGE_TEMPLATE_ASSOC`

    FOREIGN KEY (`TEMPLATE_ID` )

    REFERENCES `cms`.`template` (`TEMPLATE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1;







SET SQL_MODE=@OLD_SQL_MODE;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

