
insert into users values ('admin','b3db3f53dca38ed59900a392b9c7c4cef22b5894',1,888945365);
insert into users values ('guest','939e8cd90868c2cf578d973e9a739f601288b7b4',1,435083678);
insert into users values ('superadmin','a15c33fa2254e79971934faa29ac07afc85a8023',1,508582327);

insert into authorities values ('admin','ROLE_ADMIN');
insert into authorities values ('admin','ROLE_USER');
insert into authorities values ('guest','ROLE_USER');
insert into authorities values ('superadmin','ROLE_ADMIN');
insert into authorities values ('superadmin','ROLE_USER');

insert into groups values (1,'Users');
insert into groups values (2,'Administrators');


insert into group_Authorities values (1,'ROLE_USER');
insert into group_Authorities values (2,'ROLE_USER');
insert into group_Authorities values (2,'ROLE_ADMIN');


insert into group_members values (1,'guest',1);
insert into group_members values (2,'admin',2);



