package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.request.SearchLibraryRequest;
import com.cs2300.cch_lib.dto.response.ItemCountResponse;
import com.cs2300.cch_lib.dto.response.Response;
import com.cs2300.cch_lib.exception.UnauthorizedException;
import com.cs2300.cch_lib.model.entity.LibraryItems;
import com.cs2300.cch_lib.service.AuthenticationService;
import com.cs2300.cch_lib.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library-items")
public class LibraryController {
    private final LibraryService libraryService;
    private final AuthenticationService authenticationService;

    public LibraryController(LibraryService libraryService, AuthenticationService authenticationService) {
        this.libraryService = libraryService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/search")
    public ResponseEntity<Response<?>> searchLibrary(
            @RequestBody SearchLibraryRequest request,
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        LibraryItems items = libraryService.searchLibrary(
                request.getSelectedSearchOption(),
                request.getBookTitle(),
                request.getBookAuthor(),
                request.getIsbn(),
                request.getEquipmentName()
        );

        Response<LibraryItems> response = new Response<>(true, "", items);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/count")
    public ItemCountResponse countItems(
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return libraryService.countItems();
    }
}
