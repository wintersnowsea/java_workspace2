select table_name from user_tables
--sql 문도 깃허브에 올라가서 확인이 가능하다

--<오라클 테이블>
제목 title varchar2(100)
작성자 writer varchar2(30)
내용 content clob
이미지이름  filename varchar2(30)
등록일 regdate date default sysdate

--갤러리 테이블 생성하기
create table gallery(
  gallery_id number primary key --id 번호
, title varchar2(100) --제목
, writer varchar2(30) --작성자
, content clob --내용
, filename varchar2(30) --이미지 이름
, regdate date default sysdate --등록일
);

--갤러리 시퀀스 만들기
create sequence seq_gallery
increment by1
start with1;