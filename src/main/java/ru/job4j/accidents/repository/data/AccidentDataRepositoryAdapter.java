package ru.job4j.accidents.repository.data;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentDataRepositoryAdapter implements AccidentRepository {
    private final AccidentDataRepository repository;


    @Override
    public List<Accident> findAll() {
        List<Accident> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean create(Accident accident) {
        repository.save(accident);
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean updateText(Accident accident) {
        boolean result = false;
        Optional<Accident> accidentDb = repository.findById(accident.getId());
        if (accidentDb.isPresent()) {
            accidentDb.get().setDescription(accident.getDescription());
            repository.save(accidentDb.get());
            result = true;
        }
        return result;
    }
}
