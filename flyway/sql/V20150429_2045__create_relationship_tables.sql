create table app.user_(
    id integer not null generated always as identity,
    name varchar(30) not null,
    password varchar(50) not null,
    last_login_time timestamp not null,
    manager_id integer,
    group_id integer,
    primary key (id)
);

create table app.user_info_(
    id integer not null generated always as identity,
    user_id integer not null,
    full_name varchar(30) not null,
    gender char(1) not null,
    birthday timestamp not null,
    telephone varchar(50),
    email varchar(50),
    remark varchar(500),
    primary key (id)
);

create table app.interest_(
	id integer not null generated always as identity,
	interest varchar(50) not null,
	primary key (id)
);

create table app.user_interest_(
	user_id integer not null,
	interest_id integer not null,
	primary key (user_id, interest_id)
);

create table app.group_(
    id integer not null generated always as identity,
    name varchar(50) not null,
    primary key (id)
);
