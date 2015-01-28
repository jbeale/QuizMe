package com.quizme.api.dao;

import com.quizme.api.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            return null;
        }
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public User findUser(String username) {
        return null;
    }

    @Override
    public void createUser(User u) {

    }
}
