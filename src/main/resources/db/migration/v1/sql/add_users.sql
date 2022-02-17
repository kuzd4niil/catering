--liquibase formatted sql
--changeset daniil:4
insert into users(id, username, email, password, user_role)
values (
    nextval('user_id_sequence')
    ,'dan'
    ,'dan@gmail.com'
    ,'$2a$10$TfFM6zwo2LaAni/lR7lTweFTMzRtes3URJdcMUkPfGQM.oG5iVZ4O'
    ,3
);
insert into users(id, username, email, password, user_role)
values (
    nextval('user_id_sequence')
    ,'max'
    ,'max@gmail.com'
    ,'$2a$10$nPbVc.mAhpZujUhVx9BY9O7L96xt6fwFlIz5/lIfBsGByXFZ.34Li'
    ,1
);
--rollback DELETE FROM users WHERE username=dan
--rollback DELETE FROM users WHERE username=max