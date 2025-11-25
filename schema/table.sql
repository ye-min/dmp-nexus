-- 1. wis_class
drop table if exists wis_class;
create table wis_class (
   class_id        serial,
   class_code      varchar,
   class_year      integer not null,
   class_name      varchar not null,
   teacher_id      integer not null,
   student_count   integer default 0,
   state           integer default 1,
   create_time     timestamptz,
   status          integer default 1,
   optime          timestamptz default current_timestamp,
   extinfo         jsonb
);
drop index if exists idx_class_01;
create unique index idx_class_01 on wis_class (class_id);
drop index if exists idx_class_02;
create unique index idx_class_02 on wis_class (class_name);
drop index if exists idx_class_03;
create index idx_class_03 on wis_class (class_year, state);

-- 2. wis_student
drop table if exists wis_student;
create table wis_student (
   uid            integer,
   student_name   varchar not null,
   student_code   varchar not null,
   phone          varchar,
   contact        varchar,
   e_mail         varchar,
   gender         integer default 1,
   birth          timestamptz,
   state          integer default 1,
   class_id       integer not null,
   create_time    timestamptz,
   status         integer default 1,
   optime         timestamptz default current_timestamp,
   extinfo        jsonb
);
drop index if exists idx_student_01;
create unique index idx_student_01 on wis_student (uid);
drop index if exists idx_student_02;
create unique index idx_student_02 on wis_student (student_code);
drop index if exists idx_student_03;
create index idx_student_03 on wis_student (state);

-- 3. teacher
drop table if exists wis_teacher;
create table wis_teacher (
   uid             integer,
   teacher_name    varchar,
   state           integer default 1,
   phone           varchar,
   contact         varchar,
   e_mail          varchar,
   teacher_intro   varchar,
   create_time     timestamptz,
   status          integer default 1,
   optime          timestamptz default current_timestamp,
   extinfo         jsonb
);
drop index if exists idx_teacher_01;
create unique index idx_teacher_01 on wis_teacher (uid);
drop index if exists idx_teacher_02;
create index idx_teacher_02 on wis_teacher (state);

-- 4. user
drop table if exists wis_users;
create table wis_users (
   uid        serial,
   username   varchar,
   realname   varchar,
   passwd     varchar,
   usertype   integer default 1,
   phone      varchar,
   contact    varchar,
   e_mail     varchar,
   isvalid    integer default 1,
   roles      jsonb,
   status     integer default 1,
   optime     timestamptz default current_timestamp,
   extinfo    jsonb
);
drop index if exists idx_users_01;
create unique index idx_users_01 on wis_users (uid);
drop index if exists idx_users_02;
create unique index idx_users_02 on wis_users (username);
drop index if exists idx_users_03;
create index idx_users_03 on wis_users (usertype);

-- 5. wis_param_menu
drop table if exists wis_param_menu;
create table wis_param_menu (
   id          serial,
   mid         integer,
   mtitle      varchar,
   mdesc       varchar,
   murl        varchar,
   mpath       varchar,
   mtype       integer default 1,
   micon       varchar,
   mdispid     integer default 0,
   parent_id   integer default 0,
   status      integer default 1,
   optime      timestamptz default now(),
   extinfo     jsonb
);

-- 6. wis_param_menu_role
drop table if exists wis_param_menu_role;
create table wis_param_menu_role (
   id        serial,
   mid       integer,
   rid       integer,
   status    integer default 1,
   optime    timestamptz default now(),
   extinfo   jsonb
);
drop index if exists wis_param_menu_role_mid;
create index wis_param_menu_role_mid on wis_param_menu_role (mid);
drop index if exists wis_param_menu_role_rid;
create index wis_param_menu_role_rid on wis_param_menu_role (rid);

-- 7. wis_param_roles
drop table if exists wis_param_roles;
create table wis_param_roles (
   id        serial,
   rid       integer,
   rtitle    varchar,
   rdesc     varchar,
   status    integer default 1,
   optime    timestamptz default now(),
   extinfo   jsonb
);
drop index if exists wis_param_roles_rid;
create index wis_param_roles_rid ON wis_param_roles (rid);

-- 8. wis_param_usertype_roles
drop table if exists wis_param_usertype_roles;
create table wis_param_usertype_roles (
   id             serial,
   usertype       integer,
   roles          jsonb,
   valid_time     timestamptz default current_timestamp,
   invalid_time   timestamptz,
   status         integer default 1,
   optime         timestamptz default current_timestamp,
   extinfo        jsonb
);
drop index if exists idx_usertype_roles_01;
create unique index idx_usertype_roles_01 ON wis_param_usertype_roles (usertype);

-- 9. data set
drop table if exists wis_dataset;
create table wis_dataset (
   id               serial primary key,
   upload_time      timestamptz,
   name             varchar(256)   not null,
   content          text           not null,
   size             integer default 0 not null,
   comment          text
);

-- 10. k-means archive
drop table if exists wis_archive_kmeans;
create table wis_archive_kmeans (
   id                  serial primary key,
   record_time         timestamptz   not null,
   account_id          integer       not null,
   result_dataset      text          not null,
   result_field_list   text          not null,
   parameter           text          not null
);

-- 11. rfm archive
drop table if exists wis_archive_rfm;
create table wis_archive_rfm (
   id                  serial primary key,
   record_time         timestamptz   not null,
   account_id          integer       not null,
   result_dataset      text          not null,
   result_field_list   text          not null,
   parameter           text          not null
);
