package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class AccidentTypeHibernateRepository implements AccidentTypeRepository {
    public static final String ALL_ACCIDENT_TYPES = "from AccidentType";
    public static final String ACCIDENT_TYPE_BY_ID = "from AccidentType a where a.id = :fId";
    private final SessionFactory sf;


    @Override
    public List<AccidentType> findAll() {
        List<AccidentType> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(ALL_ACCIDENT_TYPES, AccidentType.class)
                    .list();
        }
        return result;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        AccidentType result = null;
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(ACCIDENT_TYPE_BY_ID, AccidentType.class)
                    .setParameter("fId", id)
                    .uniqueResult();
        }
        return Optional.ofNullable(result);
    }
}
