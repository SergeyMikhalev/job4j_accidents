package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class RuleServiceImpl implements RuleService{

    private final RuleRepository repository;

    public RuleServiceImpl(RuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Rule> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }
}
