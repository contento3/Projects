create table ARTICLE 
(
    NUMBER_ID int(11) not null,
    UUID varchar(45) not null,
    primary key (NUMBER_ID),
    unique index (UUID)
);