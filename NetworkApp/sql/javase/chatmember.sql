
--chatmember 테이블 생성
create table chatmember(
	chatmember_idx number primary key
	, id varchar2(20)
	, pass varchar2(20) --암호화 할시엔 64자
	, name varchar2(20)
	, regdate date default sysdate
);

--시퀀스
create sequence seq_chatmember
increment by 1
start with 1;