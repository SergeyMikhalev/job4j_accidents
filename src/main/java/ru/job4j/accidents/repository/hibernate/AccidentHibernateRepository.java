package ru.job4j.accidents.repository.hibernate;


import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class AccidentHibernateRepository implements AccidentRepository {
    public static final String ALL_ACCIDENTS = "from Accident";
    public static final String ACCIDENT_BY_ID = "from Accident a where a.id = :fId";
    public static final String UPDATE_DESC = "update Accident a set a.description=:fDesc where a.id = :fId";
    private final SessionFactory sf;

    @Override
    public List<Accident> findAll() {
        List<Accident> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(ALL_ACCIDENTS, Accident.class)
                    .list();
        }
        return result;
    }

    @Override
    public boolean create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.persist(accident);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident result = null;
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(ACCIDENT_BY_ID, Accident.class)
                    .setParameter("fId", id)
                    .uniqueResult();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public boolean updateText(Accident accident) {
        int result = 0;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session
                    .createQuery(UPDATE_DESC)
                    .setParameter("fId", accident.getId())
                    .setParameter("fDesc", accident.getDescription())
                    .executeUpdate();
            session.getTransaction().commit();
        }
        return result > 0;
    }
}
