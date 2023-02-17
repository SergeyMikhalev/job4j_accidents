package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RuleMemRepository implements RuleRepository {

    private final Map<Integer, Rule> data = new ConcurrentHashMap<>();

    public RuleMemRepository() {
        data.put(1, new Rule(1, "Статья 1"));
        data.put(2, new Rule(2, "Статья 2"));
        data.put(3, new Rule(3, "Статья 3"));
    }

    @Override
    public Set<Rule> findAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }
}