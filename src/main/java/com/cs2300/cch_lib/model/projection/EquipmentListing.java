package com.cs2300.cch_lib.model.projection;

public record EquipmentListing(
        String equipmentName,
        String classRequirement,
        String checkedOut,
        String additionalInfo,
        String contact_f_name,
        String contact_m_init,
        String contact_l_name
) {}
