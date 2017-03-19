-- Create table
create table sys_user
(
  id          number,
  account     varchar2(32),
  password    varchar2(32),
  type        varchar2(32),
  name        varchar2(32),
  nickname    varchar2(32),
  mobile      varchar2(32),
  email       varchar2(32),
  stats       number,
  create_time date,
  exprie_time  date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_user
  add constraint pk_sys_user primary key (ID);

-- Create sequence
create sequence seq_sys_user
start with 201
increment by 1
cache 20;


-- Create sequence
create sequence seq_user
start with 201
increment by 1
cache 20;

-- init data(md5)
insert into sys_user (ID, ACCOUNT, PASSWORD, TYPE, NAME, NICKNAME, MOBILE, EMAIL, STATS, CREATE_TIME, EXPRIE_TIME)
values (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', 'admin', 'admin', '10086', 'admin@sys.com', 1, to_date('18-03-2017 11:19:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-03-2017 11:19:05', 'dd-mm-yyyy hh24:mi:ss'));


-- Create table
create table sys_menu
(
  id          number,
  parent_id   number,
  name        varchar2(32),
  url         varchar2(32),
  perms       varchar2(32),
  type        number,
  icon        varchar2(32),
  order_num   number
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_menu
  add constraint pk_sys_menu primary key (ID);

-- Create sequence
create sequence seq_sys_menu
start with 201
increment by 1
cache 20;


-- Create table
create table sys_user_menu
(
  id          number,
  user_id     number,
  menu_id     number
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_user_menu
  add constraint pk_sys_user_menu primary key (ID);

-- Create sequence
create sequence seq_sys_user_menu
start with 201
increment by 1
cache 20;


-- Create table
create table sys_menu_data
(
  id          number,
  menu_id     number,
  key         varchar2(32),
  value       varchar2(32)
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_menu_data
  add constraint pk_sys_menu_data primary key (ID);

-- Create sequence
create sequence seq_sys_menu_data
start with 201
increment by 1
cache 20;

-- Create table
create table sys_operating_log
(
  id              number,
  operating_code  varchar2(32),
  user_id         number,
  content         varchar2(32),
  create_time     date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_operating_log
  add constraint pk_sys_operating_log primary key (ID);

-- Create sequence
create sequence seq_sys_operating_log
start with 201
increment by 1
cache 20;

-- Create table
create table sys_operating
(
  id              number,
  code            varchar2(32),
  remark          varchar2(255)
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_operating
  add constraint pk_sys_operating primary key (ID);

-- Create sequence
create sequence seq_sys_operating
start with 201
increment by 1
cache 20;


-- Create table
create table sys_config
(
  id              number,
  key             varchar2(255),
  value           varchar2(255),
  status          number,
  remark          varchar2(255)
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table sys_config
  add constraint pk_sys_config primary key (ID);

-- Create sequence
create sequence seq_sys_config
start with 201
increment by 1
cache 20;

insert into SYS_CONFIG (ID, KEY, VALUE, STATUS, REMARK)
values (1, 'app_version', '1.0', 1, '客户端版本号');