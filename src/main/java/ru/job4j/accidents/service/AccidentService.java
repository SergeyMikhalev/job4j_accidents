package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    List<Accident> findAll();
    boolean create(Accident accident);
    Optional<Accident> findById(int id);
    boolean update(Accident accident);
}