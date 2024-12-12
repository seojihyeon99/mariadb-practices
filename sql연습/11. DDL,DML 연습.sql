--
-- DDL/DML 연습
--

create table member(
	id int not null auto_increment,
    email varchar(200) not null,
    password varchar(64) not null,
    name varchar(50) not null,
    department varchar(100),
    primary key(id)
);
desc member;

alter table member add column juminbunho char(13) not null;
desc member;

alter table member drop juminbunho;
desc member;

alter table member add column juminbunho char(13) not null after email; -- 순서 지정은 after만 가능! (before는 불가능)
desc member;

alter table member change column department dept varchar(100) not null;
desc member;

alter table member add column profile text;
desc member;

alter table member drop juminbunho;
desc member;

-- insert
insert
	into member
values (null, 'jihyeon2474@gmail.com', password('1234'), '서지현', '개발팀', null); -- 대칭키(암호화 & 복호화 가능), 비대칭키(암호화만 가능)
select  * from member;

insert
	into member(id, email, name, password, dept)
values (null, 'jihyeon99@naver.com', '서젼', password('1234'), '개발팀');
select * from member;

-- update
update member
	set email='seojihyeon99@naver.com', password=password('12345')
where id = 2;
select * from member;

-- delete
delete
	from member
where id = 2;
select * from member;

-- transaction(tx)
select id, email from member;

select @@autocommit; -- autocommit 설정 확인(1: 자동커밋, 0: 수동커밋)

insert 
	into member 
values(null, 'jihyeon99@naver.com', password('123'), '서젼', '개발팀2', null);
select id, email from member;

-- tx : begin
set autocommit = 0;
select @@autocommit; -- 0(수동커밋. 결과를 실제DB에 반영하는 것이 아닌 캐시하고 있음)
insert 
	into member 
values(null, 'jihyeon999@naver.com', password('123'), '서지현3', '개발팀3', null);
select id, email from member;

-- tx : end
commit;
-- rollback;
select id, email from member;



