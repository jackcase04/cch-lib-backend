package com.cs2300.cch_lib.model.entity;

public record Book (
    Integer bookId,
    String title,
    String course,
    String bookEdition,
    String condition,
    Integer isbn,
    String additionalInfo,
    Boolean checkedOut,
    Integer pdfId,
    Integer contact,
    Integer checkedOutBy
) {}
