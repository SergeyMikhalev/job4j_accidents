CREATE TABLE rules_for_accidents(
 id SERIAL PRIMARY KEY,
 accident_id int NOT NULL references accidents(id),
 rule_id int NOT NULL references types(id)
);

comment on table rules_for_accidents is 'Статьи связанные с происшествием';
comment on column rules_for_accidents.id is 'Уникальный идентификатор записи';
comment on column rules_for_accidents.accident_id is 'fk инцидента';
comment on column rules_for_accidents.rule_id is 'fk статьи';