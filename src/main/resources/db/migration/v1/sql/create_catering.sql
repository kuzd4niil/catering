--liquibase formatted sql
--changeset daniil:3
create table catering(
    id bigint primary key,
    name varchar(255) not null,
    address varchar(255) not null,
    email varchar(255) unique,
    owner_id bigint not null,
    foreign key (owner_id) references users(id)
);

create sequence catering_id_sequence
    as bigint
    start 0
    increment by 1
    minvalue -1;

--rollback DROP TABLE IF EXISTS catering CASCADE;
--rollback DROP SEQUENCE IF EXISTS catering_id_sequence;