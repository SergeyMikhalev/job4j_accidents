package ru.job4j.accidents.repository.jdbctemplate;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.*;

@Repository
public class RuleJdbcTemplateRepository implements RuleRepository {
    public static final String SELECT_ALL = "select id, name from rules";
    public static final String SELECT_BY_ID = "select id, name from rules where id = ?";
    public static final String SELECT_ID_IN = "select id, name from rules where id in (%s)";
    private final JdbcTemplate jdbc;

    public RuleJdbcTemplateRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Set<Rule> findAll() {
        List<Rule> rules = jdbc.query(SELECT_ALL,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
        return new HashSet<>(rules);
    }

    @Override
    public Optional<Rule> findById(int id) {
        Rule result = jdbc.queryForObject(SELECT_BY_ID,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }, id);
        return Optional.ofNullable(result);
    }

    @Override
    public Set<Rule> findById(List<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        List<Rule> rules = jdbc.query(String.format(SELECT_ID_IN, inSql),
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }, ids.toArray());
        return new HashSet<>(rules);
    }
}
