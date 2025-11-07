drop table if exists users;

create table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) not null,
    password varchar(255) not null
);

INSERT INTO users (email, password) VALUES
    ('user1@test.com', 'password1'),
    ('user2@test.com', 'password2');