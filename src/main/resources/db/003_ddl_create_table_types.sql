CREATE TABLE types(
   id SERIAL PRIMARY KEY,
   name TEXT not null
);

comment on table types is 'Типы инцидентов на дароге';
comment on column types.id is 'Уникальный идентификатор типа инцидента';
comment on column types.name is 'Название типа инцидента';