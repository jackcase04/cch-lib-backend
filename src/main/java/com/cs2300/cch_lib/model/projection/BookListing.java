package com.cs2300.cch_lib.model.projection;

public record BookListing(
        String title,
        String authorName,
        String bookEdition,
        String condition,
        String contact_f_name,
        String contact_m_init,
        String contact_l_name,
        Boolean checkedOut
) {}
