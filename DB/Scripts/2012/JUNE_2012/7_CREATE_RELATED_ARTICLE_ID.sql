CREATE TABLE   RELATED_ARTICLES(
    ARTICLE_ID INT(11) NOT NULL,
    RELATED_ARTICLE_ID INT (11) NOT NULL,
    TYPE  VARCHAR(45) NULL,
    PRIMARY KEY(ARTICLE_ID,RELATED_ARTICLE_ID),
     CONSTRAINT `FK_ARTICLE` FOREIGN KEY (`ARTICLE_ID`) REFERENCES `ARTICLE` (`ARTICLE_ID`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;