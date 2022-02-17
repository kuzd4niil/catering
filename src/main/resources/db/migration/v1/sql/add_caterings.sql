--liquibase formatted sql
--changeset daniil:6
insert into catering(id, name, address, email, owner_id)
values (
    nextval('catering_id_sequence')
    ,'Pirozhok'
    ,'Moscow'
    ,'pirozhok@moscow.ru'
    ,0
);
insert into catering(id, name, address, email, owner_id)
values (
    nextval('catering_id_sequence')
    ,'Borsch'
    ,'Kyiv'
    ,'borsch@kyiv.ru'
    ,1
);
--rollback DELETE FROM catering WHERE email=pirozhok@moscow.ru
--rollback DELETE FROM catering WHERE email=borsch@kyiv.ru