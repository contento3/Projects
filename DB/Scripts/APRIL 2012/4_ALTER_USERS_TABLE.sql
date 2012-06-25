USE CRM;

-- adding new colunm to  user table
ALTER  TABLE  USERS 
ADD  ACCOUNT_ID INT(11);

 -- setting foreign key 
 ALTER  TABLE  USERS 
 ADD FOREIGN KEY (ACCOUNT_ID)
 REFERENCES CMS.ACCOUNT(ACCOUNT_ID) ;
 
 -- alter tale to add not null property
 ALTER  TABLE  USERS 
MODIFY  ACCOUNT_ID INT(11) NOT NULL;