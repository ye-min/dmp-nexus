-- 1.1 Account
drop table if exists iot_account;
create table iot_account (
    id            bigserial      not null,
    code          text           not null,
    name          text           not null,
    type          int4           not null, -- 1: admin; 2: teacher; 3: student;
    status        int4           not null, -- 1: active; 0: inactive;
    mobile        text           not null,
    email         text           not null,
    avatar        text           not null,
    password      text           not null,

    record_deleted        int4             not null default 0, -- 1: deleted, 0: not deleted
    record_created_time   timestamptz      not null default current_timestamp,
    record_updated_time   timestamptz      not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_account_code;
create unique index ui_account_code on iot_account (code);
drop index if exists ix_account_status;
create index ix_account_status on iot_account (status);

-- 1.2 Role
drop table if exists iot_role;
create table iot_role (
    id               bigserial      not null,
    code             text           not null,
    name             text           not null,
    description      text           not null,
    status           int4           not null,

    record_deleted          int4             not null default 0, -- 1: deleted, 0: not deleted
    record_created_time     timestamptz      not null default current_timestamp,
    record_updated_time     timestamptz      not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_role_code;
create unique index ui_role_code on iot_role (code);
drop index if exists ix_role_status;
create index ix_role_status on iot_role (status);

-- 1.3 Permission
drop table if exists iot_permission;
create table iot_permission (
    id            int8           not null,
    code          text           not null,
    name          text           not null,
    description   text           not null,
    status        int4           not null,

    record_deleted          int4             not null default 0, -- 1: deleted, 0: not deleted
    record_created_time     timestamptz      not null default current_timestamp,
    record_updated_time     timestamptz      not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_permission_code;
create unique index ui_permission_code on iot_permission (code);
drop index if exists ix_permission_status;
create index ix_permission_status on iot_permission (status);

-- 1.4 Menu
drop table if exists iot_menu;
create table iot_menu (
    id               int8           not null,
    code             text           not null,
    name             text           not null,
    description      text           not null,
    parent_id        int8           not null,
    type             int4           not null, -- 1: folder; 2: leaf; 3: operation;
    icon             text           not null,
    sequence         int4           not null,
    url              text           not null,
    status           int4           not null,

    record_deleted          int4             not null default 0, -- 1: deleted, 0: not deleted
    record_created_time     timestamptz      not null default current_timestamp,
    record_updated_time     timestamptz      not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_menu_code;
create unique index ui_menu_code on iot_menu (code);
drop index if exists ix_menu_status;
create index ix_menu_status on iot_menu (status);
drop index if exists ui_menu_sequence;
create index ui_menu_sequence on iot_menu (sequence);
drop index if exists ui_menu_parent_id;
create index ui_menu_parent_id on iot_menu (parent_id);
drop index if exists ui_menu_parent_id_sequence;
create index ui_menu_parent_id_sequence on iot_menu (parent_id, sequence);

-- 1.5. AccountRoleLink
drop table if exists iot_account_role_link;
create table iot_account_role_link (
   account_id            int8           not null,
   role_id               int8           not null,

   record_created_time   timestamptz    not null,
   primary key(account_id, role_id)
);

-- 1.6. RolePermissionLink
drop table if exists iot_role_permission_link;
create table iot_role_permission_link (
   role_id               int8           not null,
   permission_id         int8           not null,

   record_created_time   timestamptz    not null,
   primary key(role_id, permission_id)
);

-- 1.7. MenuPermissionLink
drop table if exists iot_permission_menu_link;
create table iot_permission_menu_link (
   permission_id         int8           not null,
   menu_id               int8           not null,

   record_created_time   timestamptz    not null,
   primary key(permission_id, menu_id)
);

-- ==============================
-- 2.1. Group
drop table if exists wis_group;
create table wis_group (
    id               int8   primary key,
    code             text   not null,
    name             text   not null,
    status           int4   not null,
    grade_year       int4   not null,
    teacher          int8   null,
    student_count    int4   default 0 not null,

    record_deleted        int4          not null default 0, -- 1: deleted, 0: not deleted
    record_created_time   timestamptz   not null default current_timestamp,
    record_updated_time   timestamptz   not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_wis_group_code;
create unique index ui_wis_group_code on wis_group (code);
drop index if exists ix_wis_group_status;
create index ix_wis_group_status on wis_group (status);

-- 2.2. Teacher
drop table if exists wis_teacher;
create table wis_teacher (
    id               int8   primary key,
    code             text   not null,
    name             text   not null,
    phone_number     text   null,
    email            text   null,
    brief            text   null,
    status           int4   not null

    record_deleted        int4          not null default 0, -- 1: deleted, 0: not deleted
    record_created_time   timestamptz   not null default current_timestamp,
    record_updated_time   timestamptz   not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_wis_teacher_code;
create unique index ui_wis_teacher_code on wis_teacher (code);
drop index if exists ix_wis_teacher_status;
create index ix_wis_teacher_status on wis_teacher (status);

-- 2.3. Student
drop table if exists wis_student;
create table wis_student (
    id               int8   primary key,
    code             text   not null,
    name             text   not null,
    phone_number     text   null,
    email            text   null,
    gender           int4   not null,
    birth            date   null,
    group            int8   not null,
    status           int4   not null

    record_deleted        int4          not null default 0, -- 1: deleted, 0: not deleted
    record_created_time   timestamptz   not null default current_timestamp,
    record_updated_time   timestamptz   not null default current_timestamp,
    primary key(id)
);
drop index if exists ui_wis_student_code;
create unique index ui_wis_student_code on wis_student (code);
drop index if exists ix_wis_student_status;
create index ix_wis_student_status on wis_student (status);

-- 3.1. data set
drop table if exists wis_dataset;
create table wis_dataset (
    id               bigserial     primary key,
    name             text          not null,
    content          text          not null,
    size             int8          not null default 0,
    comment          text          null,

    record_deleted        int4          not null default 0, -- 1: deleted, 0: not deleted
    record_created_time   timestamptz   not null default current_timestamp,
    record_updated_time   timestamptz   not null default current_timestamp,
    primary key(id)
);

-- 3.2. k-means archive
drop table if exists wis_archive_kmeans;
create table wis_archive_kmeans (
    id                  bigserial   primary key,
    account_id          int8        not null,
    result_dataset      text        not null,
    result_field_list   text        not null,
    parameter           text        not null,
    type                int4        not null,  -- 1: kmeans, 2: rfm

    record_deleted        int4          not null default 0, -- 1: deleted, 0: not deleted
    record_created_time   timestamptz   not null default current_timestamp,
    record_updated_time   timestamptz   not null default current_timestamp,
    primary key(id)
);
