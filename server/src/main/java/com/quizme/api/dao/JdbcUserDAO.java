package com.quizme.api.dao;

import com.quizme.api.model.User;
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
 * Created by jbeale on 1/28/15.
 */
public class JdbcUserDAO implements UserDAO {
    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;

    public JdbcUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User u = new User();
            u.setId(resultSet.getInt("id"));
            u.setEmail(resultSet.getString("email"));
            u.setUsername(resultSet.getString("username"));
            u.setPassword(resultSet.getString("password"));
            u.setFirstname(resultSet.getString("firstname"));
            u.setLastname(resultSet.getString("lastname"));
            return u;
        }
    };

    @Override
    public User getUser(int userId) {
        try {
            User u = this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", rowMapper, userId);
            return u;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findUser(String username) {
        try {
            User u = this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ? LIMIT 1", rowMapper, username);
            return u;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void createUser(User u) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", u.getUsername());
        params.put("password", u.getPassword());
        params.put("firstName", u.getFirstname());
        params.put("lastName", u.getLastname());
        params.put("type", 0);
        params.put("email", u.getEmail());
        insert.execute(params);
    }
}
