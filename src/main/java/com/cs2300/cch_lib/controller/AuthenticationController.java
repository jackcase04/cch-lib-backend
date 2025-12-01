package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.request.LoginRequest;
import com.cs2300.cch_lib.dto.request.SignupRequest;
import com.cs2300.cch_lib.dto.response.GenericResponse;
import com.cs2300.cch_lib.dto.response.Response;
import com.cs2300.cch_lib.dto.response.SessionInfoResponse;
import com.cs2300.cch_lib.dto.response.UserResponse;
import com.cs2300.cch_lib.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
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
    public ResponseEntity<?> login(
            @RequestBody LoginRequest dto,
            HttpSession session
    ) {
        UserResponse response = authenticationService.login(dto);

        session.setAttribute("userId", response.userId());
        session.setAttribute("isAdmin", response.isAdmin());
        session.setAttribute("email", response.email());
        session.setAttribute("fName", response.f_name());

        return ResponseEntity.status(
                HttpStatus.OK)
                .body(response)
        ;
    }

    @GetMapping("/user/name")
    public ResponseEntity<Response<?>> getUserName(HttpSession session) {
        try {
            String name = authenticationService.getUserName(session);
            Response<String> response = new Response<>(true, null, name);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            System.err.println("Error fetching user name: " + e.getMessage());
            Response<Void> error = new Response<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpSession session
    ) {
        session.invalidate();
        return ResponseEntity.ok(new GenericResponse("Logged out successfully"));
    }

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(
            HttpSession session
    ) {
        Integer userId = (Integer) session.getAttribute("userId");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        String email = (String) session.getAttribute("email");

        if (userId == null) {
            return ResponseEntity.status(
                    HttpStatus.OK)
                    .body(new SessionInfoResponse(false, null, null, false));

        }

        return ResponseEntity.status(
                HttpStatus.OK)
                .body(new SessionInfoResponse(true, userId, email, isAdmin));
    }
}