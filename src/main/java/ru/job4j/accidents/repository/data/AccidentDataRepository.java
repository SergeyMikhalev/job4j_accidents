package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentDataRepository extends CrudRepository<Accident, Integer> {
    @Override
    List<Accident> findAll();
}
