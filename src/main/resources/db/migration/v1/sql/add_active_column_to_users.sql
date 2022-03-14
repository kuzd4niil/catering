--liquibase formatted sql
--changeset daniil:7
alter table users add is_active int not null constraint user_is_active_default_value default (1);
--rollback ALTER TABLE users DROP COLUMN active;