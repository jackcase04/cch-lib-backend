package com.cs2300.cch_lib.repository;

import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.entity.Equipment;
import com.cs2300.cch_lib.model.entity.EquipmentRequest;
import com.cs2300.cch_lib.model.projection.EquipmentListing;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EquipmentRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public EquipmentRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SQL_FIND_EQUIP_CHECKOUT_NOTICES = """
        SELECT e.equipment_id, e.equipment_name, r.request_id
        FROM request r
        JOIN equipment e ON r.equipment_id = e.equipment_id
        WHERE r.user_id = :user_id AND r.approved = TRUE AND r.fulfilled = FALSE;
    """;

    private static final String SQL_FIND_EQUIP_USER_ITEMS = """
        SELECT e.equipment_id, e.equipment_name
        FROM equipment e
        WHERE e.checked_out_by = :user_id;
    """;

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

    public ArrayList<EquipmentRequest> findCheckOutNotices(Integer userId) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

        List<EquipmentRequest> equipment = jdbc.query(SQL_FIND_EQUIP_CHECKOUT_NOTICES, params, (rs, rowNum) -> new EquipmentRequest(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getInt("request_id")

        ));

        return new ArrayList<>(equipment);

    }

    public ArrayList<Equipment> findUserEquipment(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

        List<Equipment> equipment =  jdbc.query(SQL_FIND_EQUIP_USER_ITEMS, params, (rs, rowNum) -> new Equipment(
                rs.getInt("equipment_id"),
                rs.getString("equipment_name"),
                null,
                null,
                null,
                null,
                null
        ));

        return new ArrayList<>(equipment);
    }

}
