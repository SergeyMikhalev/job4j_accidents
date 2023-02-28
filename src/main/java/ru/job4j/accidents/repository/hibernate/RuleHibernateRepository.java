package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.*;

@Repository
@AllArgsConstructor
public class RuleHibernateRepository implements RuleRepository {
    public static final String ALL_RULES = "from Rule";
    public static final String RULE_BY_ID = "from Rule r where r.id = :fId";
    public static final String RULES_IDS_IN = "from Rule r  where r.id in :fIds";
    private final SessionFactory sf;

    @Override
    public Set<Rule> findAll() {
        List<Rule> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(ALL_RULES, Rule.class)
                    .list();
        }
        return new HashSet<>(result);
    }

    @Override
    public Optional<Rule> findById(int id) {
        Rule result = null;
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(RULE_BY_ID, Rule.class)
                    .setParameter("fId", id)
                    .uniqueResult();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Set<Rule> findById(List<Integer> ids) {
        List<Rule> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            result = session
                    .createQuery(RULES_IDS_IN, Rule.class)
                    .setParameter("fIds", ids)
                    .list();
        }
        return new HashSet<>(result);
    }
}
