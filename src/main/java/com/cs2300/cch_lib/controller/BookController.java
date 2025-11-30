package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.request.UpdateBookRequest;
import com.cs2300.cch_lib.dto.response.UpdateBookResponse;
import com.cs2300.cch_lib.model.projection.BookListing;
import com.cs2300.cch_lib.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/listings")
    public List<BookListing> findAllListings() {
        return bookService.findAllListings();
    }

    @GetMapping("/search")
    public List<BookListing> searchBooks(
            @RequestParam String search
    ) {
        return bookService.searchBooksByTitle(search);
    }

    @PatchMapping("/update")
    public UpdateBookResponse updateBook(
            @RequestBody UpdateBookRequest request
    ) {
        return bookService.updateBook(request);
    }
}
