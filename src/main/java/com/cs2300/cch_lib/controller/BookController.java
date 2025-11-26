package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.model.BookListing;
import com.cs2300.cch_lib.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/listings")
    public List<BookListing> list() {
        return bookRepository.findAllListings();
    }
}
