CREATE DATABASE IF NOT EXISTS xchngdb;

CREATE TABLE IF NOT EXISTS tag
(
    name varchar(64),
    primary key (name)
);

CREATE TABLE IF NOT EXISTS user
(
    id      int auto_increment,
    name    varchar(64),
    balance int,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS item
(
    id          int auto_increment,
    name        varchar(64),
    description text,
    owner       int,
    price       int,
    primary key (id),
    foreign key (owner) references user (id)
);

CREATE TABLE IF NOT EXISTS item_tag_xref
(
    tagName varchar(64),
    itemId  int,
    primary key (tagName, itemId),
    foreign key (tagName) references tag (name),
    foreign key (itemId) references item (id)
);
