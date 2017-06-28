-- 设备相关
select * from device;
select * from device_property;
select * from device_property_log;

---------------------------------------------------
select * from sys_user;
select * from sys_user_menu for update;
select * from sys_menu;

id,parent_id,name,url,perms,type,icon,order_num

-- 用户权限
select m.id,parent_id,name,url,perms,type,icon,order_num from sys_user_menu um,sys_menu m
where um.menu_id = m.id and um.user_id = 2;