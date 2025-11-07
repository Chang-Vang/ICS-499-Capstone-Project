-- Drop tables if they already exist to avoid conflicts
drop table if exists food_items;
drop table if exists restaurant;

-- Create the 'restaurant' table
create table restaurant
(
    id       bigint auto_increment
        primary key,
    name     varchar(255) not null,
    location varchar(255) not null,
    category varchar(255) not null
);

-- Create the 'food_items' table
create table food_items
(
    id            bigint auto_increment
        primary key,
    name          varchar(255) not null,
    description   varchar(255) null,
    category      varchar(255) not null,
    price         float        not null,
    restaurant_id bigint       not null,
    constraint foodItems_restaurant_id_fk
        foreign key (restaurant_id) references restaurant (id)
);


