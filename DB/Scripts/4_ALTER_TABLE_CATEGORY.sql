alter table category change categoryid Category_Id int(11);
alter table category change Parent_Category Parent_Category_Id int(11);
ALTER TABLE category Modify Category_Name varchar(45) NOT NULL;
ALTER TABLE category  ADD CONSTRAINT `FK_PARENT` FOREIGN KEY (`Parent_Category_Id`) REFERENCES `category`  (`Category_Id`)