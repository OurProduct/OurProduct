drop table if exists booking_place cascade;
drop table if exists bought_place cascade;
drop table if exists place_booking_price cascade;
drop table if exists place_bought cascade;
drop table if exists place_price cascade;
drop table if exists pay_operation cascade;
drop table if exists operation_stats cascade;

create table users
(
    id    bigserial,
    email varchar(255) unique,
    primary key (id)
);

insert into users (email)
values ('email'),
       ('email2'),
       ('email3');

create table booking_place
(
    id                    bigserial,
    union_key             varchar(255),
    title                 varchar(255),
    description           varchar(255),
    geo_place             varchar(255),
    file_presentation_key varchar(255),
    primary key (id)
);
insert into booking_place (union_key, title, description, geo_place, file_presentation_key)
values ('example', 'title', 'description', 'geo_place', 'example');
create table place_booking_price
(
    id         bigserial,
    price      int,
    date_start timestamp,
    date_end   timestamp,
    primary key (id)

);

create table bought_place
(
    id         bigserial,
    union_key  varchar(255),
    price      int,
    date_start timestamp,
    date_end   timestamp,
    primary key (id)
);
create table place_price
(
    place_id bigint not null,
    price_id bigint not null,
    foreign key (place_id) references booking_place (id) on delete cascade,
    foreign key (price_id) references place_booking_price (id) on delete cascade
);
create table place_bought
(
    place_id   bigint       not null,
    bought_id  bigint       not null,
    user_email varchar(255) not null,
    foreign key (place_id) references booking_place (id) on delete cascade,
    foreign key (bought_id) references bought_place (id) on delete cascade,
    foreign key (user_email) references users (email) on delete cascade
);
create table pay_operation
(
    id            bigserial,
    operation_key varchar(255),
    amount        int,
    date          TIMESTAMP,
    user_email    varchar(255),
    primary key (id),
    foreign key (user_email) references users (email) on delete cascade
);
create table operation_stats
(
    id               bigserial,
    complete         bool,
    operation_key    varchar(255),
    booking_place_id bigint,
    bought_place_id  bigint,
    foreign key (booking_place_id) references booking_place (id) on delete cascade,
    foreign key (bought_place_id) references bought_place (id) on delete cascade,
    primary key (id)
);
