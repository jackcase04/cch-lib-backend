package com.cs2300.cch_lib.dto.response;

public record UpdateEquipmentResponse (
    String equipmentName,
    String classRequirement,
    Boolean checkedOut,
    String additionalInfo,
    Integer contact,
    Integer checkedOutBy
) {}

