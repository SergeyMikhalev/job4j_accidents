package ru.job4j.accidents.repository.data;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Primary
@Repository
@AllArgsConstructor
public class RuleDataRepositoryAdapter implements RuleRepository {
    private final RuleDataRepository repository;

    @Override
    public Set<Rule> findAll() {
        Set<Rule> result = new HashSet<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Set<Rule> findById(List<Integer> ids) {
        Set<Rule> result = new HashSet<>();
        repository.findAllById(ids).forEach(result::add);
        return result;
    }
}
