create table app.person(
    id integer not null generated always as identity,
    name varchar(30) not null,
    birthday timestamp not null,
    gender char(1) not null,
    telephone varchar(50),
    email varchar(50),
    primary key (id)
);
