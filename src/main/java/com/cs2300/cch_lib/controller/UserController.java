package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.exception.UnauthorizedException;
import com.cs2300.cch_lib.model.entity.LibraryItems;
import com.cs2300.cch_lib.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cs2300.cch_lib.dto.response.Response;
import com.cs2300.cch_lib.model.entity.CheckoutNotices;
import com.cs2300.cch_lib.service.UserService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    //Gets items the user requested for checkout that are ready for the user to pick up.
    @GetMapping("/checkout-notices")
    public ResponseEntity<Response<?>> getCheckOutNotices(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        CheckoutNotices checkOutNotices = userService.getCheckOutNotices(userId);
        Response<CheckoutNotices> response = new Response<CheckoutNotices>(true, "", checkOutNotices);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Gets items the user is currently in possession of.
    @GetMapping("/user-items")
    public ResponseEntity<Response<?>> getUserItems(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        LibraryItems userItems = userService.getUserItems(userId);
        Response<LibraryItems> response = new Response<>(true, "", userItems);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public Response deleteUser(
        @RequestParam int id,
        HttpSession session
    ) {
        if (!authenticationService.isAdmin(session)) {
            throw new UnauthorizedException("Admin access required");
        }

        userService.deleteUser(id);

        return new Response(
                true,
                "Successfully deleted user",
                null
        );
    }
}