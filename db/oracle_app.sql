/* app用户 */
create table app_user
(
  id           number,
  account     varchar2(32),
  password    varchar2(256),
  type        number,
  name        varchar2(32),
  nickname    varchar2(32),
  mobile      varchar2(32),
  email       varchar2(32),
  status       number,
  create_time date,
  expire_time  date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table app_user
  add constraint pk_app_user primary key (ID);

-- Create sequence
create sequence seq_app_user
start with 201
increment by 1
cache 20;

/* 客户端用户token */
create table app_token
(
	id number not null
		constraint pk_app_token
			primary key,
	user_id number,
	token varchar2(128),
	create_time date,
	expire_time date
)
;

-- Create sequence
create sequence seq_app_token
start with 201
increment by 1
cache 20;

/* 报修工单 */
create table app_repair_work
(
  id            number,
  code          varchar2(32),
  device_id            number,
  sponsor_user_id     number,
  handle_user_id      number,
  solve_user_id       number,
  remark        varchar2(32),
  img1          varchar2(128),
  img2          varchar2(128),
  img3          varchar2(128),
  img4          varchar2(128),
  status       number,
  create_time date,
  update_time date,
  expire_time  date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table app_repair_work
  add constraint pk_app_repair_work primary key (ID);

-- Create sequence
create sequence seq_app_repair_work
start with 201
increment by 1
cache 20;


/* 报修工单日志 */
create table app_repair_work_log
(
  id                  number,
  work_code         varchar2(32),
  work_status       number,
  work_handle_user_id      number,
  operator_id       number,
  create_time       date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table app_repair_work_log
  add constraint pk_app_repair_work_log primary key (ID);

-- Create sequence
create sequence seq_app_repair_work_log
start with 201
increment by 1
cache 20;

/* 巡检 */
create table app_daily_inspect
(
  id                 number,
  menu_id           number,
  device_id         number,
  fault_code        varchar2(32),
  remark             varchar2(512),
  create_time       date
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table app_daily_inspect
  add constraint pk_app_daily_inspect primary key (ID);

-- Create sequence
create sequence seq_app_daily_inspect
start with 201
increment by 1
cache 20;


/* 巡检故障 */
create table app_daily_inspect_fault
(
  id               number,
  menuId          number,
  code            varchar2(32),
  name            varchar2(128)
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table app_daily_inspect_fault
  add constraint pk_app_daily_inspect_fault primary key (ID);

-- Create sequence
create sequence seq_app_daily_inspect_fault
start with 201
increment by 1
cache 20;