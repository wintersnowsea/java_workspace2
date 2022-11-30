--단축기 alt+x

--사원 테이블
create table emp(
	empno number primary key --사원번호
	, ename varchar2(20) --사원명
	, sal number default 0 --사원 급여
	, job varchar2(20) --사원 업무
	, deptno number --부서번호
	, constraint fk_dept_emp foreign key (deptno) references dept(deptno) --deptno에 외래키 제약조건 추가
);

--사원테이블 시퀀스
CREATE SEQUENCE seq_emp
INCREMENT BY 1 --1씩 증가
START WITH 1; --1부터 시작

select table_name from user_tables;

--외래키를 지정했을 때 어떤 효과가 있는가?
--외래키를 정의하면 부모가 구속을 받는다

select d.deptno, dname, loc, ename, sal, job
from emp e inner join dept d
on e.deptno=d.deptno;