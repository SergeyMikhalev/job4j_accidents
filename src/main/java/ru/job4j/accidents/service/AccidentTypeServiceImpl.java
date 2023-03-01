package ru.job4j.accidents.service;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.data.AccidentTypeDataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeServiceImpl implements AccidentTypeService {
    private final AccidentTypeDataRepository repository;

    public AccidentTypeServiceImpl(AccidentTypeDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AccidentType> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }
}
