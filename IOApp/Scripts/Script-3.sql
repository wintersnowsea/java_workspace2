CREATE  TABLE dept(
	deptno NUMBER PRIMARY KEY
	, dname varchar2(20)
	, loc varchar2(30)
);

create sequence seq_dept
increment by 1
start with 1;

create table emp(
	empno number primary key
	, ename varchar2(20)
	, sal number default 0
	, job varchar2(20)
	, deptno number
	, constraint fk_dept_emp foreign key (deptno) references dept(deptno)
);

CREATE SEQUENCE seq_emp
INCREMENT BY 1
START WITH 1;