insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$IEG5N8e6kcbn2lWwMngT6.a29EHOltm03msruZzEe4mRI/xi2euL2',
(select id from authorities where authority = 'ROLE_ADMIN'));