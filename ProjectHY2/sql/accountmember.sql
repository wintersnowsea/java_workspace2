--member ���̺� ����_ȸ���������̺�
create table accountmember(
	accountmember_idx number primary key
	, id varchar2(20)
	, pass varchar2(64) --��ȣȭ �ҽÿ� 64��
	, email varchar2(100)
	, regdate date default sysdate
);

--������
create sequence seq_accountmember
increment by 1
start with 1;