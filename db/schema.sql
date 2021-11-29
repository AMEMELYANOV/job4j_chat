create table role (
    id serial primary key,
    name varchar(50) not null unique
);

create table person (
    id serial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create table person_roles (
  person_id int not null references person(id),
  roles_id int not null references role(id)
);

create table room (
    id serial primary key,
    name varchar(255) not null unique
);

create table message (
    id serial primary key,
    content text,
    created timestamp without time zone default now(),
    person_id int references person(id),
    room_id int references room(id)
);