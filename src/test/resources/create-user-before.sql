delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'dima', '$2a$08$FdGwn2ZwabcWkdhwrQgtFePXMcsH5tJ.ccIdQ.asRSwA/J7LOzMrq', true),
(2, 'ivan', '$2a$08$FdGwn2ZwabcWkdhwrQgtFePXMcsH5tJ.ccIdQ.asRSwA/J7LOzMrq', true);


insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');