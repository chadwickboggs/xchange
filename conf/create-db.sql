CREATE DATABASE IF NOT EXISTS xchngdb;

CREATE TABLE IF NOT EXISTS tag
(
    name varchar(64),
    primary key (name)
);

CREATE TABLE IF NOT EXISTS item_type
(
    name varchar(64) primary key
);

CREATE TABLE IF NOT EXISTS user
(
    id        int auto_increment primary key,
    name      varchar(64) not null unique,
    photo_url varchar(128),
    balance   int
);

CREATE TABLE IF NOT EXISTS item
(
    id          int auto_increment primary key,
    name        varchar(64) not null unique,
    description text not null,
    photo_url   varchar(128),
    type        varchar(64) not null,
    owner       int,
    price       int,
    foreign key (type) references item_type (name),
    foreign key (owner) references user (id)
);

CREATE TABLE IF NOT EXISTS item_tag_xref
(
    tagName varchar(64),
    itemId  int,
    primary key (tag_name, item_id),
    foreign key (tag_name) references tag (name),
    foreign key (item_id) references item (id)
);
