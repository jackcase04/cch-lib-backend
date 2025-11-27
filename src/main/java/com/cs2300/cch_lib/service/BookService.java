package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.model.BookListing;
import com.cs2300.cch_lib.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookListing> findAllListings() {
        return bookRepository.findAllListings();
    }
}
