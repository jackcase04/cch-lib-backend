package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.exception.UnauthorizedException;
import com.cs2300.cch_lib.model.entity.LibraryItems;
import com.cs2300.cch_lib.service.AuthenticationService;
import com.cs2300.cch_lib.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library-items")
public class LibraryController {
    private final LibraryService libraryService;
    private final AuthenticationService authenticationService;

    public LibraryController(LibraryService libraryService, AuthenticationService authenticationService) {
        this.libraryService = libraryService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/search")
    public LibraryItems searchLibrary(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String bookAuthor,
            @RequestParam(required = false) String equipmentName,
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return libraryService.searchLibrary(bookTitle, bookAuthor, equipmentName);
    }
}
