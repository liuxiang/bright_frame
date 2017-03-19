-- Create table
create table device
(
  id              number,
  model_code      varchar2(32),
  name            varchar2(32),
  position        varchar2(32),
  device_id       number,
  ip              varchar2(32),
  menu_id         number,
  create_time     date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table device
  add constraint pk_device primary key (ID);
  
-- Create sequence 
create sequence seq_device
start with 201
increment by 1
cache 20;

-- Create table
create table device_property
(
  id              number,
  device_id       number,
  key             varchar2(255),
  value           varchar2(255),
  remark          varchar2(255),
  update_time     date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table device_property
  add constraint pk_device_property primary key (ID);
  
-- Create sequence 
create sequence seq_device_property
start with 201
increment by 1
cache 20;

-- Create table
create table device_property_log
(
  id              number,
  device_id       number,
  key             varchar2(255),
  value           varchar2(255),
  remark          varchar2(255),
  update_time     date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table device_property_log
  add constraint pk_device_property_log primary key (ID);
  
-- Create sequence 
create sequence seq_device_property_log
start with 201
increment by 1
cache 20;


-- Create table
create table device_repair
(
  id              number,
  device_id       number,
  userI_id        number,
  status          number,
  remark          varchar2(255),
  create_time     date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table device_repair
  add constraint pk_device_repair primary key (ID);
  
-- Create sequence 
create sequence seq_device_repair
start with 201
increment by 1
cache 20;


-- Create table
create table device_warning
(
  id                    number,
  rule_id               number,
  rule_script_id        number,
  device_id             number,
  device_property_key   varchar2(255),
  value                 varchar2(255),
  user_id               number,
  status                number,
  menu_id               number,
  lever                 number,
  create_time           date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table device_warning
  add constraint pk_device_warning primary key (ID);
  
-- Create sequence 
create sequence seq_device_warning
start with 201
increment by 1
cache 20;


-- Create table
create table device_warning_rule
(
  id                    number,
  model_code            varchar2(32),
  model_property_key    varchar2(255),
  target_value          varchar2(255),
  upper_limit           number,
  lower_limit           number,
  frequency             number,
  isaccum               number
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table device_warning_rule
  add constraint pk_device_warning_rule primary key (ID);
  
-- Create sequence 
create sequence seq_device_warning_rule
start with 201
increment by 1
cache 20;


-- Create table
create table layer
(
  id                    number,
  name                  varchar2(32),
  menu_id               number,
  background            varchar2(32),
  create_time           date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table layer
  add constraint pk_layer primary key (ID);
  
-- Create sequence 
create sequence seq_layer
start with 201
increment by 1
cache 20;


-- Create table
create table layer_text
(
  id                    number,
  layer_id              number,
  position_x            number,
  position_y            number,
  text                  varchar2(32),
  back_color            varchar2(32),
  create_time           date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table layer_text
  add constraint pk_layer_text primary key (ID);
  
-- Create sequence 
create sequence seq_layer_text
start with 201
increment by 1
cache 20;



-- Create table
create table layer_data
(
  id                    number,
  layer_id              number,
  position_x            number,
  position_y            number,
  back_color            varchar2(32),
  device_id             number,
  device_property_key   varchar2(255),
  create_time           date
)
;

-- Create/Recreate primary, unique and foreign key constraints 
alter table layer_data
  add constraint pk_layer_data primary key (ID);
  
-- Create sequence 
create sequence seq_layer_data
start with 201
increment by 1
cache 20;


-- Create table
create table layer_icon
(
  id                    number,
  layer_id              number,
  position_x            number,
  position_y            number,
  width                 number,
  height                number,
  device_id             number,
  device_property_key   varchar2(255),
  create_time           date
)
;

-- Create/Recreate primary, unique and foreign key constraints
alter table layer_icon
  add constraint pk_layer_icon primary key (ID);

-- Create sequence
create sequence seq_layer_icon
start with 201
increment by 1
cache 20;


-- Create table
create table layer_icon_status
(
  id            number,
  icon_id       number,
  icon_src      varchar2(32),
  target_value  varchar2(255),
  upper_limit   number,
  lower_limit   number
)
;

-- Create/Recreate primary, unique and foreign key constraints
alter table layer_icon_status
  add constraint pk_layer_icon_status primary key (ID);

-- Create sequence
create sequence seq_layer_icon_status
start with 201
increment by 1
cache 20;


-- Create table
create table layer_function
(
  id                    number,
  layer_id              number,
  position_x            number,
  position_y            number,
  width                 number,
  height                number,
  device_id             number,
  device_property_key   varchar2(255),
  function_code         varchar2(32),
  create_time           date
)
;

-- Create/Recreate primary, unique and foreign key constraints
alter table layer_function
  add constraint pk_layer_function primary key (ID);

-- Create sequence
create sequence seq_layer_function
start with 201
increment by 1
cache 20;
