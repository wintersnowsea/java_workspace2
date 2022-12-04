--diaryMain에 사용할 diary 테이블 생성하기
create table Diary(
	diary_idx number primary key
	, yy number
	, mm number
	, dd number
	, content varchar2(1000)
	, icon varchar2(20)
);

create sequence seq_diary
increment by 1
start with 1;

