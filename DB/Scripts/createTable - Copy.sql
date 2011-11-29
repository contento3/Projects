SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table `CONTENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CONTENT` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `CONTENT` (
  `CONTENT_ID` INT NOT NULL ,
  `CONTENT_TYPE_TEMPLATE_ID` INT NOT NULL ,
  `VALUE` VARCHAR(45) NOT NULL ,
  `CONTENT_TYPE_INSTANCE_ID` INT NOT NULL ,
  PRIMARY KEY (`CONTENT_ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `CONTENT_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CONTENT_TYPE` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `CONTENT_TYPE` (
  `CONTENT_TYPE_ID` INT NOT NULL COMMENT 'This column is primary key of this table used to identify each content type.' ,
  `CONTENT_TYPE_NAME` VARCHAR(45) NOT NULL COMMENT 'Content type name' ,
  `SITE_ID` INT NULL COMMENT 'This column identifies whether the content_type is created for specific site or the content type is system wide (i.e. for all sites)?' ,
  `IS_COMPOSED` BIT NULL DEFAULT 0 ,
  PRIMARY KEY (`CONTENT_TYPE_ID`) ,
  CONSTRAINT `SITE_ID`
    FOREIGN KEY (`SITE_ID` )
    REFERENCES `cms`.`SITE` (`SITE_ID` )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

SHOW WARNINGS;
CREATE INDEX `SITE_ID` ON `CONTENT_TYPE` (`SITE_ID` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `CONTENT_TYPE_TEMPLATES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CONTENT_TYPE_TEMPLATES` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `CONTENT_TYPE_TEMPLATES` (
  `CONTENT_TYPE_TEMPLATES_ID` INT NOT NULL ,
  `CONTENT_TYPE_ID` INT NOT NULL ,
  `COMPONENT_CONTENT_TYPE_ID` INT NOT NULL ,
  `ORDER` INT NOT NULL ,
  PRIMARY KEY (`CONTENT_TYPE_TEMPLATES_ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `CONTENT_REF`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CONTENT_REF` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `CONTENT_REF` (
  `CONTENT_REF_ID` INT NOT NULL COMMENT 'PRIMARY_KEY' ,
  `CONTENT_ID` INT NULL COMMENT 'This is id of the CONTENT which is refering other content' ,
  `CONTENT_ID_REF_TO` INT NULL COMMENT 'Content Id of a content which is being referred by the content_id column' ,
  PRIMARY KEY (`CONTENT_REF_ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'This table specifies content association to other content';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `CONTENT_TYPE_INSTANCE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CONTENT_TYPE_INSTANCE` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `CONTENT_TYPE_INSTANCE` (
  `CONTENT_TYPE_INSTANCE_ID` INT NOT NULL ,
  `CONTENT_TYPE_ID` INT NOT NULL ,
  PRIMARY KEY (`CONTENT_TYPE_INSTANCE_ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
