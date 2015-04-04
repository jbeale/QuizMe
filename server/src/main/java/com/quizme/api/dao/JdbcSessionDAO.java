package com.quizme.api.dao;

import com.quizme.api.model.Session;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jbeale on 3/15/15.
 */
public class JdbcSessionDAO implements SessionDAO {
    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;

    public JdbcSessionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    public RowMapper<Session> rowMapper = new RowMapper<Session>() {
        @Override
        public Session mapRow(ResultSet resultSet, int i) throws SQLException {
            Session s = new Session();
            s.setActivityId(resultSet.getInt("activityId"));
            s.setId(resultSet.getInt("id"));
            s.setOwnerUserId(resultSet.getInt("userId"));
            s.setSessionCode(resultSet.getInt("code"));
            s.setSessionName(resultSet.getString("name"));
            s.setCreated(resultSet.getLong("created"));
            s.setClosed(resultSet.getLong("closed"));
            return s;
        }
    };


    @Override
    public int createSession(Session session) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("sessions").usingGeneratedKeyColumns("id");
        Map<String, Object> cols = new HashMap<String, Object>();
        cols.put("code", session.getSessionCode());
        cols.put("name", session.getSessionName());
        cols.put("activityId", session.getActivityId());
        cols.put("userId", session.getOwnerUserId());
        cols.put("created", session.getCreated());
        cols.put("closed", session.getClosed());
        return insert.executeAndReturnKey(cols).intValue();
    }

    @Override
    public Session getSession(int sessionId) {
        try {
            Session s = jdbcTemplate.queryForObject("SELECT * FROM sessions WHERE id = ?", rowMapper, sessionId);
            return s;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Session getSessionByCode(int sessionCode) {
        try {
            Session s = jdbcTemplate.queryForObject("SELECT * FROM sessions WHERE code = ? AND closed IS NULL", rowMapper, sessionCode);
            return s;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int newSessionCode() {
        String sql = "SELECT FLOOR(100000000 + RAND() * 899999999) AS random_number FROM sessions WHERE \"random_number\" NOT IN (SELECT code FROM sessions) LIMIT 1";
        return jdbcTemplate.queryForInt(sql);
    }


}
