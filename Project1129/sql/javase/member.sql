--Project1129->page패키지의 AppMain에서 사용할 회원 테이블 생성하자
--회원테이블 생성하기
CREATE TABLE member(
	member_idx NUMBER PRIMARY KEY
	, id varchar2(20)
	, pass varchar2(64) --sha256에 의한 64
	, email varchar2(50)
	, regdate DATE DEFAULT sysdate
);

--sequence:순서있게 숫자만을 증가시키는 객체
CREATE SEQUENCE seq_member
INCREMENT BY 1
START WITH 1;

