--liquibase formatted sql
--changeset daniil:1
DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS user_id_sequence;
DROP TABLE IF EXISTS catering CASCADE;
DROP SEQUENCE IF EXISTS catering_id_sequence;