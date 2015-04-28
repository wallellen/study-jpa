create table app.worker(
    id integer not null generated always as identity,
    name varchar(30) not null,
    gender char(1) not null,
    version integer not null,
    primary key (id)
);