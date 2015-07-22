# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               TEXT,
  menu_id                   bigint,
  parent_category           bigint,
  constraint pk_category primary key (id))
;

create table menu (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               TEXT,
  constraint pk_menu primary key (id))
;

create table menu_item (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  short_title               varchar(255),
  description               TEXT,
  price_original            integer,
  price_for_sale            integer,
  image                     varchar(255),
  status                    tinyint(1) default 0,
  cook_time                 integer,
  prep_instruction          TEXT,
  category_id               bigint,
  unit                      ENUM('кг','л','штук'),
  constraint ck_menu_item_unit check (unit in ('gramm','litr','item')),
  constraint pk_menu_item primary key (id))
;

create table restaurant (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               TEXT,
  image                     varchar(255),
  contact                   varchar(255),
  address                   TEXT,
  constraint pk_restaurant primary key (id))
;

create table restaurant_section (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               TEXT,
  image                     varchar(255),
  constraint pk_restaurant_section primary key (id))
;

create table user_test (
  email                     varchar(40) not null,
  password                  varchar(255),
  name                      varchar(255),
  role                      integer,
  constraint pk_user_test primary key (email))
;

alter table category add constraint fk_category_menu_1 foreign key (menu_id) references menu (id) on delete restrict on update restrict;
create index ix_category_menu_1 on category (menu_id);
alter table category add constraint fk_category_parent_category_2 foreign key (parent_category) references category (id) on delete restrict on update restrict;
create index ix_category_parent_category_2 on category (parent_category);
alter table menu_item add constraint fk_menu_item_category_3 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_menu_item_category_3 on menu_item (category_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table menu;

drop table menu_item;

drop table restaurant;

drop table restaurant_section;

drop table user_test;

SET FOREIGN_KEY_CHECKS=1;

