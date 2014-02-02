

CREATE TABLE IF NOT EXISTS `role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACCOUNT_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`),
  KEY `FK_ROLE_ACCNT` (`ACCOUNT_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;


INSERT INTO `role` (`ROLE_ID`, `ROLE_NAME`, `DESCRIPTION`, `ACCOUNT_ID`) VALUES
(1, 'admin', 'administrator', 1),
(2, 'admin2', 'administrator2', 1),
(3, 'umair', 'grand admin', 1),
(5, 'jkasbdnjamsnf', 'sdgdfg', 1);


ALTER TABLE `role`
  ADD CONSTRAINT `FK_ROLE_ACCNT` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `cms`.`account` (`ACCOUNT_ID`);


