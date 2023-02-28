package ru.job4j.accidents.repository.data;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentTypeDataRepositoryAdapter implements AccidentTypeRepository {
    private final AccidentTypeDataRepository repository;

    @Override
    public List<AccidentType> findAll() {
        List<AccidentType> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }
}
