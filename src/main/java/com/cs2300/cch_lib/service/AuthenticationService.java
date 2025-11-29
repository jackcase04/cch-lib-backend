package com.cs2300.cch_lib.service;


import com.cs2300.cch_lib.dto.LoginUserDto;
import com.cs2300.cch_lib.exception.InvalidLoginException;
import com.cs2300.cch_lib.exception.InvalidSignupException;
import com.cs2300.cch_lib.model.User;
import com.cs2300.cch_lib.repository.UserRepository;
import com.cs2300.cch_lib.dto.RegisterUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User signup(RegisterUserDto input) {
        User user = userRepository.getUserByEmail(input.getEmail());

        if (user != null) {
            throw new InvalidSignupException("User with that email already exists");
        }

        return userRepository.signupUser(input);
    }

    public User login(LoginUserDto input) {
        User user = userRepository.getUserByEmail(input.getEmail());

        if (user == null) {
            throw new InvalidLoginException("Invalid email or password");
        }

        if (!passwordEncoder.matches(input.getPassword(), user.password())) {
            throw new InvalidLoginException("Invalid email or password");
        }

        // TODO: Create session
        return user;
    }
}
