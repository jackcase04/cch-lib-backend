package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.exception.InvalidResourceException;
import com.cs2300.cch_lib.model.entity.Request;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RequestRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public RequestRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Request getRequestById(long id) {
        String sql = """
            SELECT * FROM request
            WHERE request_id = :request_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("request_id", id);

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new Request(
                            rs.getInt("request_id"),
                            rs.getInt("book_id"),
                            rs.getInt("equipment_id"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("time_requested"),
                            rs.getBoolean("approved"),
                            rs.getString("request_type"),
                            rs.getBoolean("fulfilled")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Request addNewRequest(long userId, char resourceType, long resourceId, String requestType) {
        String sql;

        Map<String, Object> params = new HashMap<>();

        if (resourceType == 'B') {
            sql = """
                INSERT INTO request (book_id, user_id, time_requested, approved, request_type, fulfilled)
                VALUES (:book_id, :user_id, :time_requested, :approved, CAST(:request_type AS request_type), :fulfilled);
            """;

            params.put("book_id", resourceId);
        } else if (resourceType == 'E') {
            sql = """
                INSERT INTO request (equipment_id, user_id, time_requested, approved, request_type, fulfilled)
                VALUES (:equipment_id, :user_id, :time_requested, :approved, CAST(:request_type AS request_type), :fulfilled);
            """;

            params.put("equipment_id", resourceId);
        } else {
            throw new InvalidResourceException("Invalid resource type.");
        }

        params.put("user_id", userId);
        params.put("time_requested", Timestamp.valueOf(LocalDateTime.now()));
        params.put("approved", true);
        params.put("request_type", requestType);
        params.put("fulfilled", false);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"request_id"});

        return getRequestById(keyHolder.getKey().longValue());
    }

    public Request updateRequest(Boolean approved, Boolean fulfilled, Integer requestId) {
        String sql = """
            UPDATE request
            SET
              approved = :approved,
              fulfilled = :fulfilled
            WHERE request_id = :request_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("approved", approved);
        params.put("fulfilled", fulfilled);
        params.put("request_id", requestId);

        jdbc.update(sql, params);

        return getRequestById(requestId);
    }
}
