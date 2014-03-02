

CREATE TABLE IF NOT EXISTS `role_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ROLE_PERMISSION` (`ROLE_ID`,`PERMISSION_ID`),
  KEY `FK_ROLEPERMISSION_PERMISSION` (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `role_permission`
  ADD CONSTRAINT `FK_ROLEPERMISSION_PERMISSION` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permission` (`PERMISSION_ID`),
  ADD CONSTRAINT `FK_ROLEPERMISSION_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`);

