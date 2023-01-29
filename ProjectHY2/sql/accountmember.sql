--member 테이블 생성_회원관리테이블
create table accountmember(
	accountmember_idx number primary key
	, id varchar2(20)
	, pass varchar2(64) --암호화 할시엔 64자
	, email varchar2(100)
	, regdate date default sysdate
);

--시퀀스
create sequence seq_accountmember
increment by 1
start with 1;