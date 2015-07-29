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
  restaurant_id             integer,
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

alter table category add constraint fk_category_parent_category_1 foreign key (parent_category_id) references category (id) on delete restrict on update restrict;
create index ix_category_parent_category_1 on category (parent_category_id);
alter table category add constraint fk_category_menu_2 foreign key (menu_id) references menu (id) on delete restrict on update restrict;
create index ix_category_menu_2 on category (menu_id);
alter table food add constraint fk_food_storage_3 foreign key (storage_id) references storage (id) on delete restrict on update restrict;
create index ix_food_storage_3 on food (storage_id);
alter table food add constraint fk_food_supplier_4 foreign key (supplier_id) references supplier (id) on delete restrict on update restrict;
create index ix_food_supplier_4 on food (supplier_id);
alter table item add constraint fk_item_unit_type_5 foreign key (unit_type_id) references unit_type (id) on delete restrict on update restrict;
create index ix_item_unit_type_5 on item (unit_type_id);
alter table item add constraint fk_item_category_6 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_item_category_6 on item (category_id);
alter table kitchen add constraint fk_kitchen_restaurant_7 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_kitchen_restaurant_7 on kitchen (restaurant_id);
alter table menu add constraint fk_menu_service_8 foreign key (service_id) references service (id) on delete restrict on update restrict;
create index ix_menu_service_8 on menu (service_id);
alter table modificator add constraint fk_modificator_unit_type_9 foreign key (unit_type_id) references unit_type (id) on delete restrict on update restrict;
create index ix_modificator_unit_type_9 on modificator (unit_type_id);
alter table modificator add constraint fk_modificator_item_10 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_modificator_item_10 on modificator (item_id);
alter table place add constraint fk_place_section_11 foreign key (section_id) references restaurant_section (id) on delete restrict on update restrict;
create index ix_place_section_11 on place (section_id);
alter table restaurant_section add constraint fk_restaurant_section_restaurant_12 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_restaurant_section_restaurant_12 on restaurant_section (restaurant_id);
alter table service add constraint fk_service_restaurant_13 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_service_restaurant_13 on service (restaurant_id);
alter table storage add constraint fk_storage_restaurant_14 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_storage_restaurant_14 on storage (restaurant_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table food;

drop table item;

drop table kitchen;

drop table menu;

drop table modificator;

drop table place;

drop table restaurant;

drop table restaurant_section;

drop table service;

drop table storage;

drop table supplier;

drop table unit_type;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

