package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.model.BookListing;
import com.cs2300.cch_lib.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/listings")
    public List<BookListing> list() {
        return bookService.findAllListings();
    }
}
