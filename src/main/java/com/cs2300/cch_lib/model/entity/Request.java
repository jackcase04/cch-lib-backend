package com.cs2300.cch_lib.model.entity;

import java.sql.Timestamp;

public record Request (
    Integer requestId,
    Integer bookId,
    Integer equipmentId,
    Integer userId,
    Timestamp timeRequested,
    Boolean approved,
    String requestType,
    Boolean fulfilled
) {}
