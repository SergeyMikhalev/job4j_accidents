package ru.job4j.accidents.repository.jdbctemplate;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


@Repository
public class AccidentJdbcTemplateRepository implements AccidentRepository {
    public static final String ALL_ACCIDENTS =
            "select a.id as a_id, a.name as a_name,  description, address, type_id, t.name as typename "
                    + "from accidents as a join types as t on type_id=t.id";
    public static final String FIND_ACCIDENT_BY_ID =
            "select a.id as a_id, a.name as a_name,  description, address, type_id, t.name as typename "
                    + "from accidents as a join types as t on type_id=t.id where a.id=?";
    public static final String INSERT_INTO_ACCIDENTS =
            "insert into accidents(name,description,address,type_id) values (?,?,?,?)";
    public static final String UPDATE_ACCIDENT_DESC =
            "update accidents set description = ? where id = ?";
    public static final String INSERT_RULES_FOR_ACCIDENT =
            "insert into rules_for_accidents(accident_id,rule_id) values (?,?)";

    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplateRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Accident> findAll() {
        return jdbc.query(ALL_ACCIDENTS,
                getAccidentRowMapper());
    }

    @Override
    public boolean create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_INTO_ACCIDENTS,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, accident.getName());
                    ps.setString(2, accident.getDescription());
                    ps.setString(3, accident.getAddress());
                    ps.setInt(4, accident.getType().getId());
                    return ps;
                },
                keyHolder);
        accident.setId((Integer) keyHolder.getKeyList().get(0).get("id"));
        for (Rule rule : accident.getRules()) {
            jdbc.update(INSERT_RULES_FOR_ACCIDENT,
                    accident.getId(),
                    rule.getId());
        }
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident result = jdbc.queryForObject(FIND_ACCIDENT_BY_ID,
                getAccidentRowMapper(), id);
        return Optional.ofNullable(result);
    }

    @Override
    public boolean updateText(Accident accident) {
        int affected = jdbc.update(UPDATE_ACCIDENT_DESC,
                accident.getDescription(),
                accident.getId());
        return affected > 0;
    }

    private RowMapper<Accident> getAccidentRowMapper() {
        return (rs, row) -> {
            AccidentType type = new AccidentType();
            type.setId(rs.getInt("type_id"));
            type.setName(rs.getString("typename"));
            Accident accident = new Accident();
            accident.setId(rs.getInt("a_id"));
            accident.setName(rs.getString("a_name"));
            accident.setDescription(rs.getString("description"));
            accident.setAddress(rs.getString("address"));
            accident.setType(type);
            return accident;
        };
    }
}
