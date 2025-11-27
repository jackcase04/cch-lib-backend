package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.dto.RegisterUserDto;
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

    public long signupUser(RegisterUserDto dto) {
        String sql = """
            INSERT INTO users (email, password, name, is_admin)
            VALUES (:email, :password, :name, :isAdmin)
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("email", dto.getEmail());
        params.put("password", passwordEncoder.encode(dto.getPassword()));
        params.put("name", dto.getName());
        params.put("isAdmin", dto.getIsAdmin());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"user_id"});

        return keyHolder.getKey().longValue();
    }
}
