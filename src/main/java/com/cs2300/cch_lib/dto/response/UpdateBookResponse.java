package com.cs2300.cch_lib.dto.response;

public record UpdateBookResponse (
    Integer bookId,
    String title,
    String course,
    String bookEdition,
    String condition,
    Integer isbn,
    String additionalInfo
) {}
