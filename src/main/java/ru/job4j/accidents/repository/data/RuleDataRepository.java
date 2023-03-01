package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.List;

public interface RuleDataRepository extends CrudRepository<Rule, Integer> {
    @Override
    List<Rule> findAll();

    @Override
    List<Rule> findAllById(Iterable<Integer> integers);
}
