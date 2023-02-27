package ru.job4j.accidents.repository.mem;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentTypeMemRepository implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> data = new ConcurrentHashMap<>();

    public AccidentTypeMemRepository() {
        data.put(1, new AccidentType(1, "Две машины"));
        data.put(2, new AccidentType(2, "Машина и человек"));
        data.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public List<AccidentType> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }
}
