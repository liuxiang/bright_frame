-- Create table
create table sys_user
(
  id             number,
  account       varchar2(32),
  password      varchar2(256),
  type           number,
  name           varchar2(32),
  nickname      varchar2(32),
  mobile        varchar2(32),
  email         varchar2(32),
  status        number,
  platform      varchar2(32),
  create_time  date,
  expire_time  date
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

INSERT INTO SYS_USER (ID, ACCOUNT, PASSWORD, NAME, NICKNAME, MOBILE, EMAIL, STATUS, CREATE_TIME, EXPIRE_TIME, TYPE, PLATFORM) VALUES
(1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 'admin', '10086', 'admin@sys.com', 1, TO_DATE('2017-03-18 11:19:05', 'YYYY-MM-DD HH24:MI:SS'), null, 2, null);
INSERT INTO SYS_USER (ID, ACCOUNT, PASSWORD, NAME, NICKNAME, MOBILE, EMAIL, STATUS, CREATE_TIME, EXPIRE_TIME, TYPE, PLATFORM) VALUES
(2, '123456', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '123456', '123456', '13413134444', '123456@sys.com', 1, TO_DATE('2017-03-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), null, 1, null);
INSERT INTO SYS_USER (ID, ACCOUNT, PASSWORD, NAME, NICKNAME, MOBILE, EMAIL, STATUS, CREATE_TIME, EXPIRE_TIME, TYPE, PLATFORM) VALUES
(3, 'artisan', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'artisan', 'artisan', '10086', 'artisan@sys.com', 1, TO_DATE('2017-03-18 11:19:05', 'YYYY-MM-DD HH24:MI:SS'), null, 3, null);

INSERT INTO SYS_USER (ID, ACCOUNT, PASSWORD, NAME, NICKNAME, MOBILE, EMAIL, STATUS, CREATE_TIME, EXPIRE_TIME, TYPE, PLATFORM) VALUES
(11, 'appadmin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 'admin', '10086', 'admin@sys.com', 1, TO_DATE('2017-03-18 11:19:05', 'YYYY-MM-DD HH24:MI:SS'), null, 2, 'app');
INSERT INTO SYS_USER (ID, ACCOUNT, PASSWORD, NAME, NICKNAME, MOBILE, EMAIL, STATUS, CREATE_TIME, EXPIRE_TIME, TYPE, PLATFORM) VALUES
(12, 'app123456', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '123456', '123456', '13413134444', '123456@sys.com', 1, TO_DATE('2017-03-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), null, 1, 'app');
INSERT INTO SYS_USER (ID, ACCOUNT, PASSWORD, NAME, NICKNAME, MOBILE, EMAIL, STATUS, CREATE_TIME, EXPIRE_TIME, TYPE, PLATFORM) VALUES
(13, 'appartisan', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'artisan', 'artisan', '10086', 'artisan@sys.com', 1, TO_DATE('2017-03-18 11:19:05', 'YYYY-MM-DD HH24:MI:SS'), null, 3, 'app');

/* 系统token */
create table sys_token
(
	id number not null
		constraint pk_sys_token
			primary key,
	user_id number,
	token varchar2(128),
	create_time date,
	expire_time date
)
;

-- Create sequence
create sequence seq_sys_token
start with 201
increment by 1
cache 20;

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

INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1, 0, '消防', null, null, 1, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (2, 1, '消防报警', 'main.layout', null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (3, 0, '综合安防', null, null, 1, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (4, 3, '视频监控', 'main.layout', null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (5, 3, '防盗报警', 'main.layout', null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (6, 3, '门禁管理', 'main.layout', null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (7, 3, '电子巡更', 'main.layout', null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (8, 0, '设备管理', null, null, 1, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (9, 8, '暖通空调', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (10, 8, '给水排水', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (11, 8, '配电系统', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (12, 8, '照明系统', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (13, 8, '电梯监控', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (14, 0, '运营管理', null, null, 1, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (15, 14, '电流统计', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (16, 14, '停车管理', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (17, 14, '信息发布', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (18, 14, '背景音乐', null, null, 2, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (19, 0, '能源', null, null, 1, null, null);
INSERT INTO SYS_MENU (ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (20, 19, '能源管理', null, null, 2, null, null);

-- Create table
create table sys_user_menu
(
  id          number,
  user_id     number,
  menu_id     number,
  status      number
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

-- Create table
create table sys_token
(
  id          number not null,
  user_id     number,
  token       varchar2(128),
  create_time date,
  expire_time date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_TOKEN
  add constraint pk_sys_token primary key (ID);

-- Create sequence
create sequence seq_sys_token
start with 201
increment by 1
cache 20;