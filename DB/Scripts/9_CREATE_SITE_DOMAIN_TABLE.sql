CREATE TABLE `site_domain` (
  `DOMAIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DOMAIN_NAME` varchar(300) DEFAULT NULL,
  `SITE_ID` int(11) NOT NULL,
  PRIMARY KEY (`DOMAIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1