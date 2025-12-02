package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.request.AddBookRequest;
import com.cs2300.cch_lib.dto.request.UpdateBookRequest;
import com.cs2300.cch_lib.dto.response.UpdateBookResponse;
import com.cs2300.cch_lib.exception.UnauthorizedException;
import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.projection.BookListing;
import com.cs2300.cch_lib.service.AuthenticationService;
import com.cs2300.cch_lib.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final AuthenticationService authenticationService;

    public BookController(BookService bookService, AuthenticationService authenticationService) {
        this.bookService = bookService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/listings")
    public List<BookListing> findAllListings(
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return bookService.findAllListings();
    }

    @GetMapping("/search-title")
    public List<BookListing> searchBooksByTitle(
            @RequestParam String search,
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return bookService.searchBooksByTitle(search);
    }

    @GetMapping("/search-author")
    public List<BookListing> searchBooksByAuthor(
            @RequestParam String search,
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return bookService.searchBooksByAuthor(search);
    }

    @PostMapping("/add")
    public Book addBook(
            @RequestBody AddBookRequest request,
            HttpSession session
    ) {
        if (!authenticationService.isAdmin(session)) {
            throw new UnauthorizedException("Admin access required");
        }

        return bookService.addNewBook(request);
    }

    @PatchMapping("/update")
    public UpdateBookResponse updateBook(
            @RequestBody UpdateBookRequest request,
            HttpSession session
    ) {
        if (!authenticationService.isAdmin(session)) {
            throw new UnauthorizedException("Admin access required");
        }

        return bookService.updateBook(request);
    }
}
