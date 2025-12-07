package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.dto.request.SignupRequest;
import com.cs2300.cch_lib.model.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final PasswordEncoder passwordEncoder;

    public UserRepository(NamedParameterJdbcTemplate jdbc, PasswordEncoder passwordEncoder) {
        this.jdbc = jdbc;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        String sql = """
            SELECT * FROM users
            WHERE email = :email; 
        """; //And password.

        Map<String, Object> params = new HashMap<>();

        params.put("email", email);

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("f_name"),
                            rs.getString("m_init"),
                            rs.getString("l_name"),
                            rs.getBoolean("is_admin")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User getUserById(long user_id) {
        String sql = """
            SELECT * FROM users
            WHERE user_id = :user_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("user_id", user_id);

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("f_name"),
                            rs.getString("m_init"),
                            rs.getString("l_name"),
                            rs.getBoolean("is_admin")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User signupUser(SignupRequest dto) {
        String sql = """
            INSERT INTO users (email, password, f_name, m_init, l_name, is_admin)
            VALUES (:email, :password, :f_name, :m_init, :l_name, :isAdmin)
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("email", dto.getEmail());
        params.put("password", passwordEncoder.encode(dto.getPassword()));
        params.put("f_name", dto.getFName());
        params.put("m_init", dto.getMInit());
        params.put("l_name", dto.getLName());
        params.put("isAdmin", false); //Default to false. State alterable by admin action only.

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"user_id"});

        return getUserById(keyHolder.getKey().longValue());
    }

    public void deleteUser(long userId) {
        String sql = """
            DELETE FROM users WHERE user_id = :user_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("user_id", userId);

        jdbc.update(sql, params);
    }
}
