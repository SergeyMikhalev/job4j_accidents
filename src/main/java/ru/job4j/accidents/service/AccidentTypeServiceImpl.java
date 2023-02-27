package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeServiceImpl implements AccidentTypeService {
    private final AccidentTypeRepository repository;

    public AccidentTypeServiceImpl(AccidentTypeRepository repository) {
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
