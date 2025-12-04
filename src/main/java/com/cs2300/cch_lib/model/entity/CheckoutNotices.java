package com.cs2300.cch_lib.model.entity;

import java.util.List;

public record CheckoutNotices(
        List<BookRequest> books,
        List<EquipmentRequest> equipment
){}