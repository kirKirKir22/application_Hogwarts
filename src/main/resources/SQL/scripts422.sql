create table car (
id serial primary key,
make varchar,
model varchar,
price numeric(10,2)
);

CREATE TABLE car_owner (
    person_id serial PRIMARY KEY,
    name VARCHAR,
    age serial,
    has_license BOOLEAN default false,
    car_id serial references car(id)
);

