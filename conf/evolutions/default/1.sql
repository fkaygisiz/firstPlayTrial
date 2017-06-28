# --- !Ups

create table sample_model (
  id                bigint not null,
  name              text,
  constraint pk_sample_model primary key (id)
);

create sequence sample_model_seq;

create table apartment_type (
  id                bigint not null,
  name              text,
  constraint pk_apartment_type primary key (id)
);

create sequence apartment_type_seq;

create table apartment (
  id                bigint not null,
  name              text,
  apartment_type_id bigint,
  constraint pk_apartment primary key (id)
);

create sequence apartment_seq;

alter table apartment add constraint fk_apartment_apartment_type foreign key (apartment_type_id) references apartment (id) on delete restrict on update restrict;

# --- !Downs

drop table if exists sample_model;

drop sequence if exists sample_model_seq;

drop table if exists apartment;

drop sequence if exists apartment_seq;

drop table if exists apartment_type;

drop sequence if exists apartment_type_seq;
