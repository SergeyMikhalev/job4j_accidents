package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.data.RuleDataRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RuleServiceImpl implements RuleService {

    private final RuleDataRepository repository;

    public RuleServiceImpl(RuleDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Rule> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Set<Rule> findById(List<Integer> ids) {
        return new HashSet<>(repository.findAllById(ids));
    }
}
