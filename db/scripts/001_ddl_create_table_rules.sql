CREATE TABLE rules(
   id SERIAL PRIMARY KEY,
   name TEXT not null
);

comment on table rules is 'Статьи нарушений ПДД';
comment on column rules.id is 'Уникальный идентификатор статьи';
comment on column rules.name is 'Название статьи';