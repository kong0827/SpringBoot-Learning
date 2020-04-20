drop table if exists user;
create table user
(
    id       bigint(20) not null auto_increment,
    username varchar(40) DEFAULT NULL,
    password varchar(40) DEFAULT NULL,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;