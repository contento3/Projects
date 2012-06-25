-- create table users (
--    username varchar(50) not null primary key,
--    password varchar(50) not null,
--    enabled boolean not null
-- ) engine = InnoDb;

create schema PLATFORM_USERS;

use PLATFORM_USERS;

create table USERS(
  USERNAME varchar(50) not null primary key,
  PASSWORD varchar(50) not null,
  ENABLED boolean not null,
  SALT varchar(25) not null
  );

create table AUTHORITIES (
    USERNAME varchar(50) not null,
    AUTHORITY varchar(50) not null,
    foreign key (USERNAME) references USERS (USERNAME),
    unique index AUTHORITIES_IDX_1 (USERNAME, AUTHORITY)
) engine = InnoDb;

create table GROUPS (
    ID bigint unsigned not null auto_increment primary key,
    GROUP_NAME varchar(50) not null
) engine = InnoDb;

create table GROUP_AUTHORITIES (
    GROUP_ID bigint unsigned not null,
    AUTHORITY varchar(50) not null,
    foreign key (GROUP_ID) references GROUPS (ID)
) engine = InnoDb;

create table GROUP_MEMBERS (
    ID bigint unsigned not null auto_increment primary key,
    USERNAME varchar(50) not null,
    GROUP_ID bigint unsigned not null,
    foreign key (GROUP_ID) references GROUPS (ID)
) engine = InnoDb;

create table PERSISTENT_LOGIN (
    USERNAME varchar(64) not null,
    SERIES varchar(64) primary key,
    TOKEN varchar(64) not null,
    LAST_USED timestamp not null
) engine = InnoDb;


-- /////////////////////////////////////////////////////////////NOT DONE////////////////////////////////////////////
create table acl_sid (
    id bigint unsigned not null auto_increment primary key,
    principal tinyint(1) not null,
    sid varchar(100) not null,
    unique index acl_sid_idx_1 (sid, principal)
) engine = InnoDb;
create table acl_class (
    id bigint unsigned not null auto_increment primary key,
    class varchar(100) unique not null
) engine = InnoDb;

create table acl_object_identity (
    id bigint unsigned not null auto_increment primary key,
    object_id_class bigint unsigned not null,
    object_id_identity bigint unsigned not null,
    parent_object bigint unsigned,
    owner_sid bigint unsigned,
    entries_inheriting tinyint(1) not null,
    unique index acl_object_identity_idx_1
        (object_id_class, object_id_identity),
    foreign key (object_id_class) references acl_class (id),
    foreign key (parent_object) references acl_object_identity (id),
    foreign key (owner_sid) references acl_sid (id)
) engine = InnoDb;

create table acl_entry (
    id bigint unsigned not null auto_increment primary key,
    acl_object_identity bigint unsigned not null,
    ace_order int unsigned not null,
    sid bigint unsigned not null,
    mask int not null,
    granting tinyint(1) not null,
    audit_success tinyint(1) not null,
    audit_failure tinyint(1) not null,
    unique index acl_entry_idx_1 (acl_object_identity, ace_order),
    foreign key (acl_object_identity)
        references acl_object_identity (id),
    foreign key (sid) references acl_sid (id)
) engine = InnoDb;