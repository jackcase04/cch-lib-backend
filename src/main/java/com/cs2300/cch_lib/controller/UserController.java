package com.cs2300.cch_lib.controller;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Gets items the user requested for checkout that are ready for the user to pick up.
    @GetMapping("/checkout-notices")
    public ResponseEntity<Response<?>> getCheckOutNotices(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        CheckoutNotices checkOutNotices = userService.getCheckOutNotices(userId);
        Response<CheckoutNotices> response = new Response<CheckoutNotices>(true, "", checkOutNotices);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}