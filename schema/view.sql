-- 1. view_class
drop view if exists view_class;
create view view_class as
select a.class_id
      ,a.class_code
      ,a.class_name
      ,a.class_year
      ,a.teacher_id
      ,b.teacher_name
      ,a.state
      ,a.create_time
      ,coalesce(s.student_count, (0)::bigint) as student_count
from ((wis_class a
left join wis_teacher b on (((b.uid = a.teacher_id) and (b.status > 0))))
left join (select wis_student.class_id,
                  count(*) as student_count
           from wis_student
           where (wis_student.status > 0)
           group by wis_student.class_id) s on ((s.class_id = a.class_id)))
where (a.status > 0);

-- 2. view_student
drop view if exists view_student;
create view view_student as
select a.uid as student_id
      ,a.username
      ,a.realname as student_name
      ,b.student_code
      ,b.gender
      ,b.birth
      ,b.phone
      ,b.contact
      ,b.e_mail
      ,a.isvalid
      ,b.state
      ,b.class_id
      ,c.class_name
      ,c.teacher_id
from ((wis_users a
left join wis_student b on (((b.uid = a.uid) and (b.status > 0))))
left join wis_class c on (((c.class_id = b.class_id) and (c.status > 0))))
where ((a.usertype = 2) and (a.status > 0));

-- 3. view_teacher
drop view if exists view_teacher;
create view view_teacher as
select a.uid as teacher_id
      ,a.username
      ,a.realname as teacher_name
      ,b.phone
      ,b.contact
      ,b.e_mail
      ,b.state
      ,b.teacher_intro
from wis_users a, wis_teacher b
where ((a.usertype = 1) and (a.uid = b.uid) and (a.status > 0) and (b.status > 0));
