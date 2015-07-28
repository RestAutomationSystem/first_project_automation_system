# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  constraint pk_category primary key (id))
;

create table food (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  image                     varchar(255),
  status                    varchar(255),
  price                     double,
  start_time                datetime,
  end_time                  datetime,
  storage_id                integer,
  supplier_id               integer,
  constraint pk_food primary key (id))
;

create table item (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  image_path                varchar(255),
  price_original            double,
  price_for_sale            double,
  start_time                datetime,
  end_time                  datetime,
  constraint pk_item primary key (id))
;

create table kitchen (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  image                     varchar(255),
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  restaurant_id             integer,
  constraint pk_kitchen primary key (id))
;

create table menu (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  constraint pk_menu primary key (id))
;

create table modificator (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  image_path                varchar(255),
  price_original            double,
  price_for_sale            double,
  start_time                datetime,
  end_time                  datetime,
  constraint pk_modificator primary key (id))
;

create table place (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  image                     varchar(255),
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  section_id                integer,
  constraint pk_place primary key (id))
;

create table restaurant (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  constraint pk_restaurant primary key (id))
;

create table restaurant_section (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  image                     varchar(255),
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  restaurant_id             integer,
  constraint pk_restaurant_section primary key (id))
;

create table service (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  constraint pk_service primary key (id))
;

create table storage (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  image                     varchar(255),
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  restaurant_id             integer,
  constraint pk_storage primary key (id))
;

create table supplier (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  constraint pk_supplier primary key (id))
;

create table unit_type (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  amount                    varchar(255),
  constraint pk_unit_type primary key (id))
;

create table user (
  email                     varchar(40) not null,
  password                  varchar(255),
  name                      varchar(255),
  role                      integer,
  constraint pk_user primary key (email))
;


create table menu_category (
  menu_id                        integer not null,
  category_id                    integer not null,
  constraint pk_menu_category primary key (menu_id, category_id))
;

create table restaurant_service (
  restaurant_id                  integer not null,
  service_id                     integer not null,
  constraint pk_restaurant_service primary key (restaurant_id, service_id))
;

create table service_menu (
  service_id                     integer not null,
  menu_id                        integer not null,
  constraint pk_service_menu primary key (service_id, menu_id))
;
alter table food add constraint fk_food_storage_1 foreign key (storage_id) references storage (id) on delete restrict on update restrict;
create index ix_food_storage_1 on food (storage_id);
alter table food add constraint fk_food_supplier_2 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;
create index ix_food_supplier_2 on food (supplier_id);
alter table kitchen add constraint fk_kitchen_restaurant_3 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_kitchen_restaurant_3 on kitchen (restaurant_id);
alter table place add constraint fk_place_section_4 foreign key (section_id) references restaurant_section (id) on delete restrict on update restrict;
create index ix_place_section_4 on place (section_id);
alter table restaurant_section add constraint fk_restaurant_section_restaurant_5 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_restaurant_section_restaurant_5 on restaurant_section (restaurant_id);
alter table storage add constraint fk_storage_restaurant_6 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_storage_restaurant_6 on storage (restaurant_id);



alter table menu_category add constraint fk_menu_category_menu_01 foreign key (menu_id) references menu (id) on delete restrict on update restrict;

alter table menu_category add constraint fk_menu_category_category_02 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table restaurant_service add constraint fk_restaurant_service_restaurant_01 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;

alter table restaurant_service add constraint fk_restaurant_service_service_02 foreign key (service_id) references service (id) on delete restrict on update restrict;

alter table service_menu add constraint fk_service_menu_service_01 foreign key (service_id) references service (id) on delete restrict on update restrict;

alter table service_menu add constraint fk_service_menu_menu_02 foreign key (menu_id) references menu (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table food;

drop table item;

drop table kitchen;

drop table menu;

drop table menu_category;

drop table modificator;

drop table place;

drop table restaurant;

drop table restaurant_service;

drop table restaurant_section;

drop table service;

drop table service_menu;

drop table storage;

drop table supplier;

drop table unit_type;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

