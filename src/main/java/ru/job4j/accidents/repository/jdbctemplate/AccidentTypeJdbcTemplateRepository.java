package ru.job4j.accidents.repository.jdbctemplate;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccidentTypeJdbcTemplateRepository implements AccidentTypeRepository {
    public static final String SELECT_ALL = "select id, name from types";
    public static final String SELECT_BY_ID = "select id, name from types where id = ?";
    private final JdbcTemplate jdbc;

    public AccidentTypeJdbcTemplateRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query(SELECT_ALL,
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        AccidentType result = jdbc.queryForObject(SELECT_BY_ID,
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                }, id);
        return Optional.ofNullable(result);
    }
}
