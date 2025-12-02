package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.dto.request.AddEquipmentRequest;
import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.entity.Equipment;
import com.cs2300.cch_lib.model.projection.EquipmentListing;
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

    public Equipment getEquipmentById(long equipment_id) {
        String sql = """
            SELECT * FROM equipment
            WHERE equipment_id = :equipment_id;
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("equipment_id", equipment_id);

        try {
            return jdbc.queryForObject(sql, params, (rs, rowNum) ->
                    new Equipment(
                            rs.getInt("equipment_id"),
                            rs.getString("equipment_name"),
                            rs.getString("class_requirement"),
                            rs.getBoolean("checked_out"),
                            rs.getString("additional_info"),
                            rs.getInt("contact"),
                            rs.getInt("checked_out_by")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

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

    public Equipment addNewEquipment(AddEquipmentRequest request) {
        String sql = """
            INSERT INTO equipment (equipment_name, class_requirement, additional_info, contact)
            VALUES (:equipment_name, :class_requirement, :additional_info, :contact);
        """;

        Map<String, Object> params = new HashMap<>();

        params.put("equipment_name", request.getEquipmentName());
        params.put("class_requirement", request.getClassRequirement());
        params.put("additional_info", request.getAdditionalInfo());
        params.put("contact", request.getContact());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"equipment_id"});

        return getEquipmentById(keyHolder.getKey().longValue());
    }
}