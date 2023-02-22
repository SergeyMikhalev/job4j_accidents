package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    List<Accident> findAll();
    Accident findById(int id);
    void updateText(Accident accident);
    void checkAndCreate(Accident accident, List<Integer> ruleIds);

}
