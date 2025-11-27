package com.cs2300.cch_lib.service;


import com.cs2300.cch_lib.repository.UserRepository;
import com.cs2300.cch_lib.dto.RegisterUserDto;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;

    }

    public long signup(RegisterUserDto input) {
        return userRepository.signupUser(input);
    }

//    public User authenticate(LoginUserDto input) {
//        User user = userRepository.findByUsername(input.getUsername())
//                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            input.getUsername(),
//                            input.getPassword()
//                    )
//            );
//
//            // Set the authentication in the security context (creates session)
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            user.setEnabled(true);
//            user.setExpopushtoken(input.getExpopushtoken());
//            userRepository.save(user);
//
//            return user;
//        } catch (AuthenticationException e) {
//            throw new InvalidLoginException("Invalid username or password");
//        }
//    }
}
