CREATE TABLE accidents(
   id SERIAL PRIMARY KEY,
   name TEXT not null,
   description TEXT not null,
   address TEXT not null,
   type_id int NOT NULL references types(id)
);

comment on table accidents is 'Дорожно-транспортные происшествия';
comment on column accidents.id is 'Уникальный идентификатор ДТП';
comment on column accidents.name is 'ФИО регистратора-заявителя';
comment on column accidents.description is 'описание ДТП';
comment on column accidents.address is 'адрес происшествия';
comment on column accidents.type_id is 'id типа происшествия';