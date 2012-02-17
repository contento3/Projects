create table Article 
(
    Number_ID int(11) not null,
     UUID varchar(45) not null,
    primary key (Number_ID),
    unique index (UUID)
);