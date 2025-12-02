package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.dto.request.AddBookRequest;
import com.cs2300.cch_lib.dto.request.UpdateBookRequest;
import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.projection.BookListing;
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
public class BookRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public BookRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Book getBookById(long book_id) {
        String sql = """
            SELECT * FROM book
            WHERE book_id = :book_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("book_id", book_id);

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("course"),
                            rs.getString("book_edition"),
                            rs.getString("condition"),
                            rs.getInt("isbn"),
                            rs.getString("additional_info"),
                            rs.getBoolean("checked_out"),
                            rs.getInt("pdf_id"),
                            rs.getInt("contact"),
                            rs.getInt("checked_out_by")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SELECT_ALL_LISTINGS = """
        SELECT B.title, A.f_name, B.book_edition, B.condition, U.f_name AS contact_f_name, U.m_init AS contact_m_init, U.l_name AS contact_l_name, B.checked_out FROM book AS B
        JOIN write AS W ON B.book_id = W.book_id
        JOIN author AS A ON W.author_id = A.author_id
        JOIN users AS U ON B.contact = U.user_id;
    """;

    public List<BookListing> findAllListings() {
        return jdbc.query(SELECT_ALL_LISTINGS, Map.of(), (rs, rowNum) ->
                new BookListing(
                        rs.getString("title"),
                        rs.getString("f_name"),
                        rs.getString("book_edition"),
                        rs.getString("condition"),
                        rs.getString("contact_f_name"),
                        rs.getString("contact_m_init"),
                        rs.getString("contact_l_name"),
                        rs.getBoolean("checked_out")
                )
        );
    }

    public List<BookListing> searchBooksByTitle(String search) {
        String sql = """
            SELECT B.title, A.f_name, B.book_edition, B.condition, U.f_name AS contact_f_name, U.m_init AS contact_m_init, U.l_name AS contact_l_name, B.checked_out FROM book AS B
            JOIN write AS W ON B.book_id = W.book_id
            JOIN author AS A ON W.author_id = A.author_id
            JOIN users AS U ON B.contact = U.user_id
            WHERE B.title ILIKE :search;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("search", "%" + search + "%");

        return jdbc.query(sql, params, (rs, rowNum) ->
                new BookListing(
                        rs.getString("title"),
                        rs.getString("f_name"),
                        rs.getString("book_edition"),
                        rs.getString("condition"),
                        rs.getString("contact_f_name"),
                        rs.getString("contact_m_init"),
                        rs.getString("contact_l_name"),
                        rs.getBoolean("checked_out")
                )
        );
    }

    public List<BookListing> searchBooksByAuthor(String search) {
        String sql = """
            SELECT B.title, A.f_name, B.book_edition, B.condition, U.f_name AS contact_f_name, U.m_init AS contact_m_init, U.l_name AS contact_l_name, B.checked_out FROM book AS B
            JOIN write AS W ON B.book_id = W.book_id
            JOIN author AS A ON W.author_id = A.author_id
            JOIN users AS U ON B.contact = U.user_id
            WHERE A.f_name ILIKE :search OR A.f_name ILIKE :search;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("search", "%" + search + "%");

        return jdbc.query(sql, params, (rs, rowNum) ->
                new BookListing(
                        rs.getString("title"),
                        rs.getString("f_name"),
                        rs.getString("book_edition"),
                        rs.getString("condition"),
                        rs.getString("contact_f_name"),
                        rs.getString("contact_m_init"),
                        rs.getString("contact_l_name"),
                        rs.getBoolean("checked_out")
                )
        );
    }

    public Book updateBook(UpdateBookRequest request) {

        String sql = """
            UPDATE book
            SET
              title = COALESCE(:title, title),
              course = COALESCE(:course, course),
              book_edition = COALESCE(:book_edition, book_edition),
              condition = COALESCE(:condition, condition),
              isbn = COALESCE(:isbn, isbn),
              additional_info = COALESCE(:additional_info, additional_info)
            WHERE book_id = :book_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("title", request.getTitle());
        params.put("course", request.getCourse());
        params.put("book_edition", request.getBookEdition());
        params.put("condition", request.getCondition());
        params.put("isbn", request.getIsbn());
        params.put("additional_info", request.getAdditionalInfo());
        params.put("book_id", request.getBookId());

        jdbc.update(sql, params);

        return getBookById(request.getBookId());
    }

    public Book addNewBook(AddBookRequest request) {
        String sql = """
            INSERT INTO book (title, course, book_edition, condition, isbn, additional_info, contact)
            VALUES (:title, :course, :book_edition, CAST(:condition AS medium), :isbn, :additional_info, :contact);
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("title", request.getTitle());
        params.put("course", request.getCourse());
        params.put("book_edition", request.getBookEdition());
        params.put("condition", request.getCondition());
        params.put("isbn", request.getIsbn());
        params.put("additional_info", request.getAdditionalInfo());
        params.put("contact", request.getContact());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"book_id"});

        return getBookById(keyHolder.getKey().longValue());
    }
}
