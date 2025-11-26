package com.cs2300.cch_lib.model;

public record BookListing(
        String title,
        String authorName,
        String bookEdition,
        String condition,
        String contact,
        Boolean checkedOut
) {}
