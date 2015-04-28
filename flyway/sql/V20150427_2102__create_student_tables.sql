create table app.student(
    id integer not null generated always as identity,
    sno varchar(50) not null,
    name varchar(30) not null,
    gender char(1) not null,
    telephone varchar(50),
    primary key (id)
);

create table app.student_detail(
    id integer not null,
    birthday timestamp,
    address varchar(200),
    email varchar(100),
    qq varchar(50),
    primary key (id)
);