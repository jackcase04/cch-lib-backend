package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.RegisterUserDto;
import com.cs2300.cch_lib.service.AuthenticationService;
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
            @RequestBody RegisterUserDto dto
    ) {
        return ResponseEntity.ok(authenticationService.signup(dto));
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(
//            @RequestBody LoginUserDto dto
//    ) {
//        try {
//            User authenticatedUser = authenticationService.authenticate(dto);
//            AuthResponse authResponse = new AuthResponse(authenticatedUser.getFullname(), authenticatedUser.getUsername());
//
//            return ResponseEntity.ok(authResponse);
//        }
//    }
}