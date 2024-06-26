CREATE DATABASE IF NOT EXISTS xchngdb;

CREATE TABLE IF NOT EXISTS tag
(
    name varchar(64),
    primary key (name)
);

CREATE TABLE IF NOT EXISTS user
(
    id        int auto_increment,
    name      varchar(64),
    photo_url varchar(128),
    balance   int,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS item
(
    id          int auto_increment,
    name        varchar(64),
    description text,
    photo_url   varchar(128),
    owner       int,
    price       int,
    primary key (id),
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
