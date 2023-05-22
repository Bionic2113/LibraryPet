SELECT 'DROP DATABASE mydb'
WHERE EXISTS (SELECT FROM pg_database WHERE datname = 'java_jsp');
SELECT 'CREATE DATABASE mydb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'java_jsp');

create table roles
(
    id    serial
        primary key,
    role_ text not null
        unique
);

insert into roles (role_) values ('admin'),('user');

create table users
(
    id        serial
        primary key,
    username  varchar(40) not null
        unique,
    login     varchar(40) not null
        unique,
    password_ text        not null,
    role_     text
        references roles (role_),
    active    boolean default true
);
insert into users(username, login, password_, role_)
VALUES ('Korgi', 'korgi' ,'$2a$12$chqSWTOueM/Vpe.4IfQ7quTMv/GOa6bBhjNTt7BqPanqvpSmS7ISu','user'),
('Gena_boss','boss','$2a$12$mytd9S8F4YOq4l0IcdmVNui3wmoq3WTlxz0eKg.GJTFoyEzuuIicy','admin');



create table people
(
    id         integer default nextval('peoples_id_seq'::regclass) not null
        constraint peoples_pkey
            primary key,
    lastname   text                                                not null,
    firstname  text                                                not null,
    patronymic text    default 'Нет'::text,
    active     boolean default true
);
create table books
(
    id     serial
        primary key,
    title  text                               not null,
    author text    default 'Неизвестен'::text not null,
    active boolean default true
);
create table bookspeople
(
    id        serial
        primary key,
    id_people integer
        references people,
    id_books  integer
        references books,
    active    boolean default true
);