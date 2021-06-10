package learn.field_agent.data;

import learn.field_agent.data.mappers.AliasMapper;
import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Author NAHID
 * Date  09,  June, 2021
 **/
@Repository
public class AliasJdbcTemplateRepository implements AliasRepository {

    private final JdbcTemplate jdbcTemplate;

    public AliasJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // GET ALL ALIAS
    @Override
    public List<Alias> findAll() {
        final String sql = "select * from alias; ";
        return jdbcTemplate.query(sql, new AliasMapper());
    }

    // ADD ALIAS
    @Override
    public Alias add(Alias alias) {
        final String sql = "insert into alias (name, persona, agent_id)" +
                "values (?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, alias.getName());
            ps.setString(2, alias.getPersona());
            ps.setInt(3, alias.getAgentId());
            return ps;
        }, keyHolder);
        alias.setAliasId(keyHolder.getKey().intValue());
        return alias;
    }

    // UPDATE EXISTING ALIAS
    @Override
    public boolean update(Alias alias) {
        final String sql = "update alias set "
                + "`name` = ?, "
                + "persona = ? "
                + "where alias_id = ?;";
        return jdbcTemplate.update(sql,
                alias.getName(),
                alias.getPersona(),
                alias.getAliasId()) > 0;
    }

    // DELETE AN ALIAS
    @Override
    @Transactional
    public boolean deleteById(int aliasId) {
        return jdbcTemplate.update("delete from alias where alias_id = ?;", aliasId) > 0;
    }
}
