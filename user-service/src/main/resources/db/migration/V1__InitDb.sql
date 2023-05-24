create table users
(
    id         serial,
    email      varchar(255) unique,
    password   varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
);

create table role
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

create table user_and_role
(
    id      bigserial,
    user_id bigint not null,
    role_id bigint not null,
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (role_id) references role (id) on delete cascade
);