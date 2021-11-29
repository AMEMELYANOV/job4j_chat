insert into role (name) values ('USER');

insert into person (username, password) values ('Petr', '123');
insert into person (username, password) values ('Ivan', '123');

insert into person_roles (person_id, roles_id) VALUES (1, 1), (2, 1);

insert into room (name) values ('room1');
insert into room (name) values ('room2');

insert into message (content, person_id, room_id) values ('message1', 1, 1);
insert into message (content, person_id, room_id) values ('message2', 2, 2);