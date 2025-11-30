package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.model.BookListing;
import com.cs2300.cch_lib.model.EquipmentListing;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EquipmentRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public EquipmentRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SELECT_ALL_LISTINGS = """
        SELECT
          E.equipment_name,
          E.class_requirement,
          E.checked_out,
          E.additional_info,
          U.f_name AS contact_f_name,
          U.m_init AS contact_m_init,
          U.l_name AS contact_l_name
        FROM equipment AS E
        JOIN users AS U ON E.contact = U.user_id;
    """;

    public List<EquipmentListing> findAllEquipment() {
        return jdbc.query(SELECT_ALL_LISTINGS, Map.of(), (rs, rowNum) ->
                new EquipmentListing(
                        rs.getString("equipment_name"),
                        rs.getString("class_requirement"),
                        rs.getString("checked_out"),
                        rs.getString("additional_info"),
                        rs.getString("contact_f_name"),
                        rs.getString("contact_m_init"),
                        rs.getString("contact_l_name")
                )
        );
    }

    public List<EquipmentListing> searchEquipmentByName(String search) {
        String sql = """
            SELECT
              E.equipment_name,
              E.class_requirement,
              E.checked_out,
              E.additional_info,
              U.f_name AS contact_f_name,
              U.m_init AS contact_m_init,
              U.l_name AS contact_l_name
            FROM equipment AS E
            JOIN users AS U ON E.contact = U.user_id
            WHERE E.equipment_name ILIKE :search;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("search", "%" + search + "%");

        return jdbc.query(sql, params, (rs, rowNum) ->
                new EquipmentListing(
                        rs.getString("equipment_name"),
                        rs.getString("class_requirement"),
                        rs.getString("checked_out"),
                        rs.getString("additional_info"),
                        rs.getString("contact_f_name"),
                        rs.getString("contact_m_init"),
                        rs.getString("contact_l_name")
                )
        );
    }
}
