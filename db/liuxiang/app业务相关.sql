-- 巡检
SELECT
  d.*,adi.fault_code, adi.remark, adi.create_time adi_create_time
FROM device d left join app_daily_inspect adi on d.id = adi.DEVICE_ID and (to_char(adi.CREATE_TIME, 'yyyy-mm-dd') = '2017-05-04')
WHERE d.EXPIRE_TIME IS NULL AND d.MENU_ID = 2;

-- 工单
select * from APP_REPAIR_WORK;
select rw.*
,u1.NAME sponsor_user_name,u1.mobile sponsor_user_mobile
,u2.NAME handle_user_name,u2.mobile handle_user_mobile
,u3.NAME solve_user_name,u3.mobile solve_user_mobile
from app_repair_work rw,SYS_USER u1,SYS_USER u2,SYS_USER u3
where rw.sponsor_user_id  = u1.ID(+)
and rw.handle_user_id  = u2.ID(+)
and rw.solve_user_id= u3.ID(+)
and (rw.sponsor_user_id = 2 or rw.handle_user_id = 2  or rw.solve_user_id = 2)


select rw.* ,u1.NAME sponsor_user_name,u1.mobile sponsor_user_mobile ,u2.NAME handle_user_name,u2.mobile handle_user_mobile ,u3.NAME solve_user_name,u3.mobile solve_user_mobile
from app_repair_work rw,SYS_USER u1,SYS_USER u2,SYS_USER u3
WHERE rw.sponsor_user_id = u1.ID(+) and rw.handle_user_id = u2.ID(+) and rw.solve_user_id= u3.ID(+)
and (rw.sponsor_user_id = 2 or rw.handle_user_id = 2 or rw.solve_user_id = 2)

-- ----------------------------------------------
select * from app_daily_inspect;
select * from app_repair_work;
