package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.LoginRequest;
import com.cs2300.cch_lib.dto.SignupRequest;
import com.cs2300.cch_lib.dto.UserResponse;
import com.cs2300.cch_lib.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestBody SignupRequest dto,
            HttpSession session
    ) {

        UserResponse response = authenticationService.signup(dto);

        session.setAttribute("userId", response.userId());
        session.setAttribute("isAdmin", response.isAdmin());
        session.setAttribute("email", response.email());

        return ResponseEntity.status(
                HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginRequest dto,
            HttpSession session
    ) {
        UserResponse response = authenticationService.login(dto);

        session.setAttribute("userId", response.userId());
        session.setAttribute("isAdmin", response.isAdmin());
        session.setAttribute("email", response.email());

        return ResponseEntity.status(
                HttpStatus.OK)
                .body(response)
        ;
    }
}