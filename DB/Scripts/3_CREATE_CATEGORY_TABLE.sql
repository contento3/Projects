

CREATE TABLE `category` (
  `Categoryid` int(11) NOT NULL,
  `Category_Name` varchar(45) DEFAULT NULL,
  `Parent_Category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Categoryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

