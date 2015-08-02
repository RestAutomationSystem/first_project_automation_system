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
  parent_category_id        integer,
  menu_id                   integer,
  constraint pk_category primary key (id))
;

create table event (
  id                        integer auto_increment not null,
  event_type                varchar(255),
  description               TEXT,
  old_value                 varchar(255),
  new_value                 varchar(255),
  event_time                datetime,
  author_email              varchar(40),
  constraint pk_event primary key (id))
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
  unit_type_id              integer,
  category_id               integer,
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
  service_id                integer,
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
  unit_type_id              integer,
  item_id                   integer,
  constraint pk_modificator primary key (id))
;

create table order_table (
  id                        bigint auto_increment not null,
  description               TEXT,
  start_time                datetime,
  end_time                  datetime,
  status                    tinyint(1) default 0,
  total                     double,
  clients_quantity          integer,
  constraint pk_order_table primary key (id))
;

create table order_type (
  id                        integer auto_increment not null,
  title                     TEXT,
  description               TEXT,
  start_time                datetime,
  end_time                  datetime,
  status                    tinyint(1) default 0,
  constraint pk_order_type primary key (id))
;

create table payment_type (
  id                        integer auto_increment not null,
  title                     TEXT,
  description               TEXT,
  start_time                datetime,
  end_time                  datetime,
  status                    tinyint(1) default 0,
  constraint pk_payment_type primary key (id))
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

create table role (
  title                     TEXT,
  description               TEXT,
  start_time                datetime,
  end_time                  datetime,
  status                    tinyint(1) default 0)
;

create table service (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    varchar(255),
  start_time                datetime,
  end_time                  datetime,
  restaurant_id             integer,
  constraint pk_service primary key (id))
;

create table session (
  description               TEXT,
  start_time                datetime,
  end_time                  datetime,
  status                    tinyint(1) default 0)
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

alter table category add constraint fk_category_parent_category_1 foreign key (parent_category_id) references category (id) on delete restrict on update restrict;
create index ix_category_parent_category_1 on category (parent_category_id);
alter table category add constraint fk_category_menu_2 foreign key (menu_id) references menu (id) on delete restrict on update restrict;
create index ix_category_menu_2 on category (menu_id);
alter table event add constraint fk_event_author_3 foreign key (author_email) references user (email) on delete restrict on update restrict;
create index ix_event_author_3 on event (author_email);
alter table food add constraint fk_food_storage_4 foreign key (storage_id) references storage (id) on delete restrict on update restrict;
create index ix_food_storage_4 on food (storage_id);
alter table food add constraint fk_food_supplier_5 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;
create index ix_food_supplier_5 on food (supplier_id);
alter table item add constraint fk_item_unit_type_6 foreign key (unit_type_id) references unit_type (id) on delete restrict on update restrict;
create index ix_item_unit_type_6 on item (unit_type_id);
alter table item add constraint fk_item_category_7 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_item_category_7 on item (category_id);
alter table kitchen add constraint fk_kitchen_restaurant_8 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_kitchen_restaurant_8 on kitchen (restaurant_id);
alter table menu add constraint fk_menu_service_9 foreign key (service_id) references service (id) on delete restrict on update restrict;
create index ix_menu_service_9 on menu (service_id);
alter table modificator add constraint fk_modificator_unit_type_10 foreign key (unit_type_id) references unit_type (id) on delete restrict on update restrict;
create index ix_modificator_unit_type_10 on modificator (unit_type_id);
alter table modificator add constraint fk_modificator_item_11 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_modificator_item_11 on modificator (item_id);
alter table place add constraint fk_place_section_12 foreign key (section_id) references restaurant_section (id) on delete restrict on update restrict;
create index ix_place_section_12 on place (section_id);
alter table restaurant_section add constraint fk_restaurant_section_restaurant_13 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_restaurant_section_restaurant_13 on restaurant_section (restaurant_id);
alter table service add constraint fk_service_restaurant_14 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_service_restaurant_14 on service (restaurant_id);
alter table storage add constraint fk_storage_restaurant_15 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_storage_restaurant_15 on storage (restaurant_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table event;

drop table food;

drop table item;

drop table kitchen;

drop table menu;

drop table modificator;

drop table order_table;

drop table order_type;

drop table payment_type;

drop table place;

drop table restaurant;

drop table restaurant_section;

drop table role;

drop table service;

drop table session;

drop table storage;

drop table supplier;

drop table unit_type;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

