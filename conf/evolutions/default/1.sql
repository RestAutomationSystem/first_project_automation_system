# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user_test (
  email                     varchar(40) not null,
  password                  varchar(255),
  name                      varchar(255),
  age                       integer,
  active                    tinyint(1) default 0,
  constraint pk_user_test primary key (email))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table user_test;

SET FOREIGN_KEY_CHECKS=1;

