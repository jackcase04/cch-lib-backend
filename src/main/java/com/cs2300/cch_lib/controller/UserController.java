package com.cs2300.cch_lib.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cs2300.cch_lib.dto.response.Response;
import com.cs2300.cch_lib.model.entity.LibraryItems;
import com.cs2300.cch_lib.service.GeneralService;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

//@RequestMapping("/")
@RestController
public class GeneralController {
    private final GeneralService generalService;

    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @GetMapping("/checkout-notices")
    public ResponseEntity<Response<?>> getCheckOutNotices(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        LibraryItems checkOutNotices = generalService.getCheckOutNotices(userId);
        Response<LibraryItems> response = new Response<LibraryItems>(true, "", checkOutNotices);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}