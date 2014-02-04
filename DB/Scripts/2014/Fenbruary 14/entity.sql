

CREATE TABLE IF NOT EXISTS `entity` (
  `ENTITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ENTITY_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`ENTITY_ID`)
) 
INSERT INTO `entity` (`ENTITY_ID`, `ENTITY_NAME`) VALUES
(1, 'article'),
(2, 'page'),
(3, 'document');
