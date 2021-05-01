create table person
(
    id          serial primary key not null,
    login       varchar(2000),
    password    varchar(2000),
    employee_id int references employee (id)
);

create table employee
(
    id      serial primary key not null,
    name    varchar(2000),
    surname varchar(2000),
    inn     bigint,
    hired   timestamp
);

insert into employee (name, surname, inn)
values ('Oleg', 'Ivanov', 777888999),
       ('Kate', 'Semenova', 111222333),
       ('Artur', 'Petrov', 333444555);

insert into person (login, password, employee_id)
values ('ivanov@mail.com', '123', 1),
       ('petrov@mail.com', '123', 3),
       ('semenova@mail.com', '123', 2),
       ('newuser', '123', 1);