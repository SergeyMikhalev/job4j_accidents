package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemRepository implements AccidentRepository {
    private final Map<Integer, Accident> data = new ConcurrentHashMap<>();
    private final AtomicInteger index = new AtomicInteger(4);

    public AccidentMemRepository() {
        data.put(1,
                new Accident(1,
                        "Иванов Иван",
                        "На Ростовской столкнулись 2 легковых автомобиля.",
                        "Ростовская 29",
                        new AccidentType(1, "Две машины"))
        );
        data.put(2,
                new Accident(2,
                        "Петров Петр",
                        "Наезд на велосипедиста. Водитель БМВ скрылся.",
                        "Героев Сибиряков 101",
                        new AccidentType(3, "Машина и велосипед"))
        );

        data.put(3,
                new Accident(3,
                        "Збигнев Бжезинский",
                        "Сбит человек, лежит без сознания на дороге.",
                        "Газовая 22",
                        new AccidentType(2, "Машина и человек"))
        );
    }

    @Override
    public List<Accident> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean create(Accident accident) {
        int ind = index.getAndIncrement();
        accident.setId(ind);
        data.put(ind, accident);
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public boolean update(Accident accident) {
        return data.replace(accident.getId(), accident) != null;
    }
}
