package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

public interface AccidentDataRepository extends CrudRepository<Accident, Integer> {
}
