package com.cs2300.cch_lib.model.entity;

public record Equipment (
    Integer equipmentId,
    String equipmentName,
    String classRequirement,
    Boolean checkedOut,
    String additionalInfo,
    Integer contactId,
    Integer checkedOutBy
) {}
