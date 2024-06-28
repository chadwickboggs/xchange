CREATE DATABASE IF NOT EXISTS xchngdb;

CREATE TABLE IF NOT EXISTS user
(
    id        uuid primary key,
    name      varchar(64) not null unique,
    photo_url varchar(128),
    balance   int         not null default 0
);

CREATE TABLE IF NOT EXISTS tag
(
    name varchar(64) primary key
);

CREATE TABLE IF NOT EXISTS item_type
(
    name varchar(64) primary key
);

CREATE TABLE IF NOT EXISTS item
(
    id          uuid primary key,
    name        varchar(64) not null,
    description text        not null,
    photo_url   varchar(128),
    type        varchar(64) not null,
    owner       uuid        not null,
    price       int,
    index (name),
    foreign key (type) references item_type (name),
    foreign key (owner) references user (id)
);

CREATE TABLE IF NOT EXISTS want
(
    id          uuid primary key,
    name        varchar(64) not null,
    description text        not null,
    type        varchar(64) not null,
    user        uuid        not null,
    price       int,
    index (name),
    foreign key (type) references item_type (name),
    foreign key (user) references user (id)
);

CREATE TABLE IF NOT EXISTS item_tag_xref
(
    tag_name varchar(64) not null,
    item_id  uuid        not null,
    primary key (tag_name, item_id),
    foreign key (tag_name) references tag (name),
    foreign key (item_id) references item (id)
);

CREATE TABLE IF NOT EXISTS want_tag_xref
(
    tag_name varchar(64) not null,
    want_id  uuid        not null,
    primary key (tag_name, want_id),
    foreign key (tag_name) references tag (name),
    foreign key (want_id) references item (id)
);

CREATE TABLE IF NOT EXISTS trade
(
    id           uuid primary key,
    composite_id uuid,
    item_one_id  uuid                           not null,
    item_two_id  uuid                           not null,
    state        varchar(32) default 'Proposed' not null,
    data_item    text,
    foreign key (item_one_id) references item (id),
    foreign key (item_two_id) references item (id)
);