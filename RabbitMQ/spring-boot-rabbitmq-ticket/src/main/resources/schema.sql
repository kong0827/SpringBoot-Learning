
DROP TABLE IF EXISTS `result`;
create table result
(
    id int auto_increment primary key,
    ticket_id int null,
    user_id int null
);
DROP TABLE IF EXISTS `ticket`;
create table ticket
(
    id int auto_increment primary key,
    name varchar(255) null,
    content varchar(255) null,
    user_name varchar(20) null,
    count int default '6666' not null
);