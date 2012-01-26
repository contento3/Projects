create table PAGE_CATEGORY_ASSOCIATION 
(
    CATEGORY_ID int(11) not null,
    PAGE_ID int(11) not null,
    primary key (CATEGORY_ID,PAGE_ID)
);