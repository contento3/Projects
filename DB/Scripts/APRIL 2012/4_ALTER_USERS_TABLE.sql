-- adding new colunm to  user table
ALTER  TABLE  USERS 
ADD  ACCOUNT_ID INT(11);

-- update account_id
update users set account_id =1;

 -- setting foreign key 
 ALTER  TABLE  USERS 
 ADD FOREIGN KEY (account_id)
 REFERENCES cms.account(account_id) ;
 
 -- alter tale to add not null property
 ALTER  TABLE  USERS 
MODIFY  ACCOUNT_ID INT(11) NOT NULL;