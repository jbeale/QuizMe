package com.quizme.api.dao;

import com.quizme.api.model.Question;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jbeale on 2/19/15.
 */
public class JdbcQuestionDAO implements QuestionDAO {

    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;

    public JdbcQuestionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    RowMapper<Question> rowMapper = new RowMapper<Question>() {
        @Override
        public Question mapRow(ResultSet resultSet, int i) throws SQLException {
            Question q = new Question();
            q.setId(resultSet.getInt("id"));
            q.setAuthorUserId(resultSet.getInt("authorUserId"));
            q.setName(resultSet.getString("name"));
            q.setType(resultSet.getString("type"));
            return q;
        }
    };

    @Override
    public Question getQuestion(int id) {
        return null;
    }

    @Override
    public int saveQuestion(Question question) {
        return 0;
    }

    @Override
    public void deleteQuestion(int id) {

    }

    @Override
    public List<Question> getQuestionsByUser(int id) {
         return jdbcTemplate.query("SELECT * FROM questions WHERE authorUserId = ?", rowMapper, id);
    }
}
