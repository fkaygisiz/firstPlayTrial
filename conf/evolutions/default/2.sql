# --- !Ups

insert into sample_model (id,name) values (1,'first sample');

insert into apartment_type (id,name) values (1,'Finca');

insert into apartment (id,name,apartment_type_id) values (1,'Finca on Mallorca',1);

# --- !Downs

delete from sample_model;

delete from apartment;
delete from apartment_type;
