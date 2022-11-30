--부서 테이블
CREATE TABLE dept(
	deptno NUMBER PRIMARY KEY --부서 번호
	, dname varchar2(20) --부서명
	, loc varchar2(30) --부서 지역
);

--부서테이블 시퀀스
create sequence seq_dept
increment by 1 --1씩 증가
start with 1; --1부터 시작