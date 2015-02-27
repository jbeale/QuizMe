package com.quizme.api.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.ProblemTypeModel;
import com.quizme.api.model.Question;
import com.quizme.api.model.canonical.MultipleChoice;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbeale on 2/19/15.
 */
public class JdbcQuestionDAO implements QuestionDAO {

    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;
    private Gson gson;

    public JdbcQuestionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
    }

    RowMapper<Question> rowMapper = new RowMapper<Question>() {
        @Override
        public Question mapRow(ResultSet resultSet, int i) throws SQLException {
            Question q = new Question();
            q.setId(resultSet.getInt("id"));
            q.setAuthorUserId(resultSet.getInt("authorUserId"));
            q.setName(resultSet.getString("name"));
            q.setType(resultSet.getString("type"));
            q.setCreated(resultSet.getLong("created"));
            q.setModified(resultSet.getLong("modified"));
            //data blob handling
            ProblemTypeModel problemData = problemDataFromJson(q.getType(), resultSet.getString("data"));
            q.setData(problemData);
            return q;
        }
    };

    private ProblemTypeModel problemDataFromJson(String type, String json) {
        ProblemTypeModel problemData = null;
        if (type.equals(Question.TYPE_MULTIPLE_CHOICE)) {
            problemData = gson.fromJson(json, MultipleChoice.class);
        }
        return problemData;
    }

    @Override
    public Question getQuestion(int id) {
        try {
            Question q = jdbcTemplate.queryForObject("SELECT * FROM questions WHERE id = ?", rowMapper, id);
            return q;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int insertQuestion(Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("questions").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authorUserId", question.getAuthorUserId());
        params.put("name", question.getName());
        params.put("type", question.getType());
        params.put("created", question.getCreated());
        params.put("modified", question.getModified());
        params.put("data", gson.toJson(question.getData()));
        Number id = insert.executeAndReturnKey(params);
        return id.intValue();
    }

    @Override
    public int updateQuestion(Question question) {
        String sql = "UPDATE questions SET authorUserId = ?, name = ?, type = ?, created = ?, modified = ?, data = ? WHERE id = ?";
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, question.getAuthorUserId());
            ps.setString(2, question.getName());
            ps.setString(3, question.getType());
            ps.setLong(4, question.getCreated());
            ps.setLong(5, question.getModified());
            Blob data = dataSource.getConnection().createBlob();
            data.setBytes(1, gson.toJson(question.getData()).getBytes());
            ps.setBlob(6, data);
            ps.setInt(7, question.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question.getId();
    }

    @Override
    public void deleteQuestion(int id) {
        this.jdbcTemplate.update("DELETE FROM questions WHERE id = ?", id);
    }

    @Override
    public List<Question> getQuestionsByUser(int id) {
         return jdbcTemplate.query("SELECT * FROM questions WHERE authorUserId = ?", rowMapper, id);
    }
}
