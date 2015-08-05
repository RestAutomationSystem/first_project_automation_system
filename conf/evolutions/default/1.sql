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
  status_id                 integer,
  total                     double,
  clients_quantity          integer,
  order_type_id             integer,
  payment_type_id           integer,
  place_id                  integer,
  constraint pk_order_table primary key (id))
;

create table order_item (
  id                        bigint auto_increment not null,
  description               TEXT,
  amount                    integer,
  price                     double,
  status                    tinyint(1) default 0,
  item_id                   integer,
  order_id                  bigint,
  constraint pk_order_item primary key (id))
;

create table order_modificator (
  id                        bigint auto_increment not null,
  description               TEXT,
  amount                    integer,
  price                     double,
  status                    tinyint(1) default 0,
  modificator_id            integer,
  order_id                  bigint,
  constraint pk_order_modificator primary key (id))
;

create table order_status (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    tinyint(1) default 0,
  constraint pk_order_status primary key (id))
;

create table order_type (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    tinyint(1) default 0,
  constraint pk_order_type primary key (id))
;

create table payment_type (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
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

create table price_list_has_modificator (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    tinyint(1) default 0,
  start_time                datetime,
  end_time                  datetime,
  price                     double,
  modificator_id            integer,
  constraint pk_price_list_has_modificator primary key (id))
;

create table price_list_has_product (
  id                        integer auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  status                    tinyint(1) default 0,
  start_time                datetime,
  end_time                  datetime,
  price                     double,
  item_id                   integer,
  constraint pk_price_list_has_product primary key (id))
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

create table role_table (
  id                        integer auto_increment not null,
  title                     TEXT,
  description               TEXT,
  start_time                datetime,
  end_time                  datetime,
  status                    tinyint(1) default 0,
  constraint pk_role_table primary key (id))
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
  role_id                   integer,
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
alter table order_table add constraint fk_order_table_status_12 foreign key (status_id) references order_status (id) on delete restrict on update restrict;
create index ix_order_table_status_12 on order_table (status_id);
alter table order_table add constraint fk_order_table_orderType_13 foreign key (order_type_id) references order_type (id) on delete restrict on update restrict;
create index ix_order_table_orderType_13 on order_table (order_type_id);
alter table order_table add constraint fk_order_table_paymentType_14 foreign key (payment_type_id) references payment_type (id) on delete restrict on update restrict;
create index ix_order_table_paymentType_14 on order_table (payment_type_id);
alter table order_table add constraint fk_order_table_place_15 foreign key (place_id) references place (id) on delete restrict on update restrict;
create index ix_order_table_place_15 on order_table (place_id);
alter table order_item add constraint fk_order_item_item_16 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_order_item_item_16 on order_item (item_id);
alter table order_item add constraint fk_order_item_order_17 foreign key (order_id) references order_table (id) on delete restrict on update restrict;
create index ix_order_item_order_17 on order_item (order_id);
alter table order_modificator add constraint fk_order_modificator_modificator_18 foreign key (modificator_id) references modificator (id) on delete restrict on update restrict;
create index ix_order_modificator_modificator_18 on order_modificator (modificator_id);
alter table order_modificator add constraint fk_order_modificator_order_19 foreign key (order_id) references order_table (id) on delete restrict on update restrict;
create index ix_order_modificator_order_19 on order_modificator (order_id);
alter table place add constraint fk_place_section_20 foreign key (section_id) references restaurant_section (id) on delete restrict on update restrict;
create index ix_place_section_20 on place (section_id);
alter table price_list_has_modificator add constraint fk_price_list_has_modificator_modificator_21 foreign key (modificator_id) references modificator (id) on delete restrict on update restrict;
create index ix_price_list_has_modificator_modificator_21 on price_list_has_modificator (modificator_id);
alter table price_list_has_product add constraint fk_price_list_has_product_item_22 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_price_list_has_product_item_22 on price_list_has_product (item_id);
alter table restaurant_section add constraint fk_restaurant_section_restaurant_23 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_restaurant_section_restaurant_23 on restaurant_section (restaurant_id);
alter table service add constraint fk_service_restaurant_24 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_service_restaurant_24 on service (restaurant_id);
alter table storage add constraint fk_storage_restaurant_25 foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_storage_restaurant_25 on storage (restaurant_id);
alter table user add constraint fk_user_role_26 foreign key (role_id) references role_table (id) on delete restrict on update restrict;
create index ix_user_role_26 on user (role_id);



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

drop table order_item;

drop table order_modificator;

drop table order_status;

drop table order_type;

drop table payment_type;

drop table place;

drop table price_list_has_modificator;

drop table price_list_has_product;

drop table restaurant;

drop table restaurant_section;

drop table role_table;

drop table service;

drop table session;

drop table storage;

drop table supplier;

drop table unit_type;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

