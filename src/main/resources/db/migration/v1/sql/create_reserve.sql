--liquibase formatted sql
--changeset daniil:5
create table reserve(
    id bigint primary key,
    user_id bigint not null,
    catering_id bigint not null,
    is_active int not null default (1),
    foreign key (user_id) references users(id),
    foreign key (catering_id) references catering(id)
);

create sequence reserve_id_sequence
    as bigint
    start 0
    increment by 1
    minvalue -1

--rollback DROP TABLE IF EXISTS reserve CASCADE;
--rollback DROP SEQUENCE IF EXISTS reserve_id_sequence;