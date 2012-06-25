SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';



CREATE SCHEMA IF NOT EXISTS `CMS` DEFAULT CHARACTER SET latin1 ;

USE `CMS` ;



-- -----------------------------------------------------

-- Table `CMStest`.`account`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`ACCOUNT` (

  `ACCOUNT_ID` INT(11) NOT NULL ,

  `ACCOUNT_NAME` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`ACCOUNT_ID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1

COMMENT = 'Used to represent an account for each of the customer.' ;





-- -----------------------------------------------------

-- Table `CMStest`.`article`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`ARTICLE` (

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

-- Table `CMStest`.`image`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`IMAGE` (

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

-- Table `CMStest`.`page_layout_type`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`PAGE_LAYOUT_TYPE` (

  `PAGE_LAYOUT_TYPE_ID` INT(11) NOT NULL ,

  `PAGE_LAYOUT_TYPE_NAME` VARCHAR(45) NOT NULL ,

  `DESCRIPTION` VARCHAR(500) NOT NULL ,

  PRIMARY KEY (`PAGE_LAYOUT_TYPE_ID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1, 

COMMENT = 'Used to specify different type of page layout from which the' ;





-- -----------------------------------------------------

-- Table `CMStest`.`page_layout`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`PAGE_LAYOUT` (

  `PAGE_LAYOUT_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `NAME` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `ACCOUNT_ID` INT(11) NOT NULL ,

  `PAGE_LAYOUT_TYPE_ID` INT(11) NOT NULL ,

  PRIMARY KEY (`PAGE_LAYOUT_ID`) ,

  INDEX `ACCOUNT_ID_FK` (`ACCOUNT_ID` ASC) ,

  INDEX `PAGE_LAYOUT_TYPE_ID` (`PAGE_LAYOUT_TYPE_ID` ASC) ,

  CONSTRAINT `ACCOUNT_ID_FK`

    FOREIGN KEY (`ACCOUNT_ID` )

    REFERENCES `CMS`.`ACCOUNT` (`ACCOUNT_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `PAGE_LAYOUT_TYPE_ID`

    FOREIGN KEY (`PAGE_LAYOUT_TYPE_ID` )

    REFERENCES `CMS`.`PAGE_LAYOUT_TYPE` (`PAGE_LAYOUT_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci;





-- -----------------------------------------------------

-- Table `CMS`.`SITE`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`SITE` (

  `SITE_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `SITE_NAME` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `URL` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `ACCOUNT_ID` INT(11) NOT NULL DEFAULT '1' ,

  PRIMARY KEY (`SITE_ID`) ,

  INDEX `ACCOUNT_ID` (`ACCOUNT_ID` ASC) ,

  CONSTRAINT `ACCOUNT_ID`

    FOREIGN KEY (`ACCOUNT_ID` )

    REFERENCES `CMS`.`ACCOUNT` (`ACCOUNT_ID` )

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci;





-- -----------------------------------------------------

-- Table `CMStest`.`page`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`PAGE` (

  `PAGE_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID of a page' ,

  `PAGE_URI` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `SITE_ID` INT(11) NOT NULL ,

  `PARENT_ID` INT(11) NULL DEFAULT NULL ,

  `PAGE_TITLE` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,

  `PAGE_LAYOUT_ID` INT(11) NULL DEFAULT '1' ,

  PRIMARY KEY (`PAGE_ID`) ,

  INDEX `SITE_PAGE_FK` (`SITE_ID` ASC) ,

  INDEX `PAGE_LAYOUT_FK` (`PAGE_LAYOUT_ID` ASC) ,

  CONSTRAINT `PAGE_LAYOUT_FK`

    FOREIGN KEY (`PAGE_LAYOUT_ID` )

    REFERENCES `CMS`.`PAGE_LAYOUT` (`PAGE_LAYOUT_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `SITE_PAGE_FK`

    FOREIGN KEY (`SITE_ID` )

    REFERENCES `CMS`.`SITE` (`SITE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci, 

COMMENT = 'This table represents page of a website' ;





-- -----------------------------------------------------

-- Table `CMS`.`PAGE_SECTION_TYPE`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`PAGE_SECTION_TYPE` (

  `PAGE_SECTION_TYPE_ID` INT(11) NOT NULL ,

  `PAGE_SECTION_TYPE_NAME` VARCHAR(45) NOT NULL ,

  `DESCRIPTION` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`PAGE_SECTION_TYPE_ID`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `CMS`.`PAGE_SECTION`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`PAGE_SECTION` (

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

    REFERENCES `CMS`.`page_layout` (`PAGE_LAYOUT_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `FK_SECTION_TYPE_SECTION`

    FOREIGN KEY (`PAGE_SECTION_TYPE_ID` )

    REFERENCES `CMS`.`page_section_type` (`PAGE_SECTION_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_unicode_ci;





-- -----------------------------------------------------

-- Table `CMStest`.`TEMPLATE`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`TEMPLATE_DIRECTORY` (

  `TEMPLATE_DIRECTORY_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `DIRECTORY_NAME` VARCHAR(45) NOT NULL ,

  `PARENT` INT(11) NULL DEFAULT NULL ,

  `IS_GLOBAL` INT(11) NOT NULL DEFAULT '0' ,

  PRIMARY KEY (`TEMPLATE_DIRECTORY_ID`) )

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `CMS`.`TEMPLATE_TYPE`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`TEMPLATE_TYPE` (

  `TEMPLATE_TYPE_ID` INT(11) NOT NULL AUTO_INCREMENT ,

  `TEMPLATE_TYPE_NAME` VARCHAR(45) NOT NULL ,

  `DESCRIPTION` VARCHAR(200) NULL DEFAULT NULL ,

  `CONTENT_TYPE` VARCHAR(25) NOT NULL ,

  PRIMARY KEY (`TEMPLATE_TYPE_ID`) )

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `CMStest`.`template`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`TEMPLATE` (

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

    REFERENCES `CMS`.`ACCOUNT` (`ACCOUNT_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `FK_TEMPLATE_DIRECTORY`

    FOREIGN KEY (`TEMPLATE_DIRECTORY_ID` )

    REFERENCES `CMS`.`template_directory` (`TEMPLATE_DIRECTORY_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `FK_TEMPLATE_TEMPLATE_TYPE`

    FOREIGN KEY (`TEMPLATE_TYPE_ID` )

    REFERENCES `CMS`.`TEMPLATE_TYPE` (`TEMPLATE_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

AUTO_INCREMENT = 1

DEFAULT CHARACTER SET = latin1;





-- -----------------------------------------------------

-- Table `CMS`.`PAGE_TEMPLATE_ASSOCIATION`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `CMS`.`PAGE_TEMPLATE_ASSOCIATION` (

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

    REFERENCES `CMS`.`page` (`PAGE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `SECTION_TYPE_PAGE_TEMPLATE_ASSOC`

    FOREIGN KEY (`PAGE_SECTION_TYPE_ID` )

    REFERENCES `CMS`.`page_section_type` (`PAGE_SECTION_TYPE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION,

  CONSTRAINT `TEMPLATE_PAGE_TEMPLATE_ASSOC`

    FOREIGN KEY (`TEMPLATE_ID` )

    REFERENCES `CMS`.`template` (`TEMPLATE_ID` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1;







SET SQL_MODE=@OLD_SQL_MODE;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

