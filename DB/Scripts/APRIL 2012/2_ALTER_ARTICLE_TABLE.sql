Alter table article  change ARTICLE_UUID  ARTICLE_ID 
INT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE article ADD ARTICLE_UUID varchar(45) not null AFTER ARTICLE_ID;
alter table article add unique (ARTICLE_UUID);

Alter table article  Modify date_created  date;
Alter table article  Modify date_posted  date;
Alter table article Modify body LONGTEXT;