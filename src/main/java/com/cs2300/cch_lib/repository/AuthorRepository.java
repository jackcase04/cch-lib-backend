package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.dto.request.AuthorRequest;
import com.cs2300.cch_lib.model.entity.Author;
import com.cs2300.cch_lib.model.entity.Write;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public AuthorRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Author getAuthorById(long author_id) {
        String sql = """
            SELECT * FROM author
            WHERE author_id = :author_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("author_id", author_id);

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new Author(
                            rs.getInt("author_id"),
                            rs.getString("f_name"),
                            rs.getString("m_init"),
                            rs.getString("l_name")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Author getAuthorByAllInfo(AuthorRequest author) {
        String sql = """
            SELECT * FROM author
            WHERE
                (f_name = :f_name OR ((:f_name)::text IS NULL AND f_name IS NULL)) AND
                (m_init = :m_init OR ((:m_init)::text IS NULL AND m_init IS NULL)) AND
                (l_name = :l_name OR ((:l_name)::text IS NULL AND l_name IS NULL));
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("f_name", author.getFName());
        params.put("m_init", author.getMInit());
        params.put("l_name", author.getLName());

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new Author(
                            rs.getInt("author_id"),
                            rs.getString("f_name"),
                            rs.getString("m_init"),
                            rs.getString("l_name")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Author addNewAuthor(AuthorRequest author) {
        String sql = """
            INSERT INTO author (f_name, m_init, l_name)
            VALUES (:f_name, :m_init, :l_name);
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("f_name", author.getFName());
        params.put("m_init", author.getMInit());
        params.put("l_name", author.getLName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"author_id"});

        return getAuthorById(keyHolder.getKey().longValue());
    }

    public void addNewWrite(long authorId, long bookId) {
        String sql = """
            INSERT INTO write (author_id, book_id)
            VALUES (:author_id, :book_id);
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("author_id", authorId);
        params.put("book_id", bookId);

        jdbc.update(sql, params);
    }

    public List<Write> getWritesByBook(long book_id) {
        String sql = """
            SELECT * FROM write WHERE book_id = :book_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("book_id", book_id);

        try {
            return jdbc.query(sql, params, (rs, rowNum) ->
                    new Write(
                            rs.getInt("author_id"),
                            rs.getInt("book_id")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    public List<Write> getWritesByAuthor(long author_id) {
        String sql = """
            SELECT * FROM write WHERE author_id = :author_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("author_id", author_id);

        try {
            return jdbc.query(sql, params, (rs, rowNum) ->
                    new Write(
                            rs.getInt("author_id"),
                            rs.getInt("book_id")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    public void deleteWrite(long book_id, long author_id) {
        String sql = """
            DELETE FROM write WHERE book_id = :book_id AND author_id = :author_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("book_id", book_id);
        params.put("author_id", author_id);

        jdbc.update(sql, params);
    }

    public void deleteAuthor(long author_id) {
        String sql = """
            DELETE FROM author WHERE author_id = :author_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("author_id", author_id);

        jdbc.update(sql, params);
    }
}
