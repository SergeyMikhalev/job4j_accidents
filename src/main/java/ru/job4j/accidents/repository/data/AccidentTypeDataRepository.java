package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

public interface AccidentTypeDataRepository extends CrudRepository<AccidentType, Integer> {
}
