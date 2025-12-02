package com.cs2300.cch_lib.service;


import com.cs2300.cch_lib.dto.request.LoginRequest;
import com.cs2300.cch_lib.dto.response.UserResponse;
import com.cs2300.cch_lib.dto.response.Response;
import com.cs2300.cch_lib.exception.InvalidLoginException;
import com.cs2300.cch_lib.exception.InvalidSignupException;
import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.entity.User;
import com.cs2300.cch_lib.repository.UserRepository;
import com.cs2300.cch_lib.dto.request.SignupRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    public Boolean isAdmin(HttpSession session) {
        Boolean admin = (Boolean) session.getAttribute("isAdmin");

        return admin != null && admin;
    }

    public UserResponse signup(SignupRequest input) {
        User user = userRepository.getUserByEmail(input.getEmail());

        if (user != null) {
            throw new InvalidSignupException("User with that email already exists");
        }

        // if we get to this line, user is null
        user = userRepository.signupUser(input);

        return new UserResponse(
                user.userId(),
                user.email(),
                user.f_name(),
                user.m_init(),
                user.l_name(),
                user.isAdmin()
        );
    }

    public UserResponse login(LoginRequest input) {
        User user = userRepository.getUserByEmail(input.getEmail());

        if (user == null) {
            throw new InvalidLoginException("Invalid email or password");
        }

        if (!passwordEncoder.matches(input.getPassword(), user.password())) {
            throw new InvalidLoginException("Invalid email or password");
        }

        return new UserResponse(
                user.userId(),
                user.email(),
                user.f_name(),
                user.m_init(),
                user.l_name(),
                user.isAdmin()
        );
    }

    public String getUserName(HttpSession session) throws NoSuchElementException {
        String name = (String) session.getAttribute("fName");

        if (name == null) {
            throw new NoSuchElementException("Unable to find user name.");
        }

        return name;
    }
}
