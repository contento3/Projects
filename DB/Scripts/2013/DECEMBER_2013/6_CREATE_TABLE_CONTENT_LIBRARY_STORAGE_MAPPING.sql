/**Table that maps a content library to storage provider instance. */
CREATE  TABLE `cms`.`CONTENT_LIBRARY_STORAGE_MAPPING` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT 'Primary key of this table' ,
  `LIBRARY_ID` INT NOT NULL COMMENT 'Foreign key from CONTENT_LIBRARY table' ,
  `STORAGE_TYPE_ID` INT NOT NULL COMMENT 'FOREIGN key from storage_type table' ,
  `STORAGE_ID` INT NULL COMMENT 'ID that come from the storage tables.' ,
  `CONTENT_TYPE` VARCHAR(45) NOT NULL COMMENT 'COlumn that tells what type of library is this. Like IMAGE,DOCUMENT or other type of content' ,
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `UNIQUE_KEY` (`CONTENT_TYPE` ASC,`STORAGE_TYPE_ID` ASC,`LIBRARY_ID` ASC,`STORAGE_ID` ASC) ,
  CONSTRAINT `FK_CTNTLIB_STORAGE_TYPE`
    FOREIGN KEY (`STORAGE_TYPE_ID` )
    REFERENCES `cms`.`storage_type` (`STORAGE_TYPE_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CTNTLIB_LIBRARY`
    FOREIGN KEY (`LIBRARY_ID` )
    REFERENCES `cms`.`content_library` (`LIBRARY_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
