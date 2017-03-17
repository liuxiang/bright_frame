-- Create table
create table country
(
  id          number,
  countryname varchar2(32),
  countrycode varchar2(32)
)
;
-- Create/Recreate primary, unique and foreign key constraints
alter table country
  add constraint pk_id primary key (ID);

-- init data
insert into COUNTRY (id, countryname, countrycode)
values (1, '中国', 'code_1');
insert into COUNTRY (id, countryname, countrycode)
values (2, '外国2', 'code_2');
insert into COUNTRY (id, countryname, countrycode)
values (3, '外国3', 'code_3');
insert into COUNTRY (id, countryname, countrycode)
values (4, '外国4', 'code_4');
commit;

-- Create sequence 
create sequence seq_country
start with 201
increment by 1
cache 20;