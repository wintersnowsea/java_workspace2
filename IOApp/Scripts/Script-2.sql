CREATE TABLE dot(
	dot_id NUMBER PRIMARY KEY
	, x NUMBER
	, y NUMBER
	, gallery_id NUMBER
);
CREATE SEQUENCE seq_dot
INCREMENT BY 1
START WITH 1;