package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.model.projection.BookListing;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
}
