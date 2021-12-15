--liquibase formatted sql
--changeset daniil:2
create table users(
    id bigint primary key,
    username varchar(255) unique,
    email varchar(255) unique,
    password varchar(255) not null,
    user_role int not null
);

create sequence user_id_sequence
    as bigint
    start 0
    increment by 1
    minvalue -1;

--rollback DROP TABLE IF EXISTS users CASCADE;
--rollback DROP SEQUENCE IF EXISTS user_id_sequence;