-- Drop the Deals table if it exists
drop table if exists Deals;

create table deals
(
    id                  bigint auto_increment
        primary key,
    title               varchar(255)  not null,
    description         varchar(2000) not null,
    applicable_item_ids varchar(1000) not null,
    discount_type       varchar(255)  not null,
    discount_value      double        not null,
    end_date            date          null,
    start_date          date          null
);
