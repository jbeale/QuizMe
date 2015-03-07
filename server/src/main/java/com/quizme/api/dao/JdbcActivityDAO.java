package com.quizme.api.dao;

import com.quizme.api.model.Activity;
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
 * Created by jbeale on 3/6/15.
 */
public class JdbcActivityDAO implements ActivityDAO {

    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;

    public JdbcActivityDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    private RowMapper<Activity> rowMapper = new RowMapper<Activity>() {
        @Override
        public Activity mapRow(ResultSet resultSet, int i) throws SQLException {
            Activity a = new Activity();
            a.setId(resultSet.getInt("id"));
            a.setUserId(resultSet.getInt("userId"));
            a.setName(resultSet.getString("name"));
            a.setQuestionIds(resultSet.getString("items"));
            return a;
        }
    };

    @Override
    public Activity getActivity(int activityId) {
        try {
            Activity a = jdbcTemplate.queryForObject("SELECT * FROM activities WHERE id = ?", rowMapper, activityId);
            return a;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Activity> listActivityByUser(int userId) {
        return jdbcTemplate.query("SELECT * FROM activities WHERE userId = ?", rowMapper, userId);
    }

    @Override
    public int create(Activity activity) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("activities").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", activity.getName());
        params.put("items", activity.getQuestionIds());
        params.put("userId", activity.getUserId());
        Number id = insert.executeAndReturnKey(params);
        return id.intValue();
    }

    @Override
    public void update(Activity activity) {
        String sql = "UPDATE activities SET userId = ?, name = ?, items = ? WHERE id = ?";
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, activity.getUserId());
            ps.setString(2, activity.getName());
            ps.setString(3, activity.getQuestionIds());
            ps.setInt(4, activity.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int activityId) {
        this.jdbcTemplate.update("DELETE FROM activities WHERE id = ?", activityId);
    }
}
