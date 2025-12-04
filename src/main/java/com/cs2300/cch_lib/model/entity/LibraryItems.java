package com.cs2300.cch_lib.model.entity;

import java.util.List;

public record LibraryItems(
        List<Book> books,
        List<Equipment> equipment
){}