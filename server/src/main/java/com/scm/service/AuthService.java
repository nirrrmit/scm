package com.scm.service;

import com.scm.dto.LoginRequest;
import com.scm.dto.SignUpRequest;
import com.scm.exception.DuplicateUserException;
import com.scm.exception.InvalidDataException;
import com.scm.exception.UserDoesNotExistException;
import com.scm.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private JwtService jwtService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public String register(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match!");
        }

        if (userService.findUserByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new DuplicateUserException("Email already exists");
        }

        // Create and save User entity
        User newUser = User.builder()
                .email(signUpRequest.getEmail())
                .password(hashPassword(signUpRequest.getPassword()))
                .build();

        userService.saveUser(newUser);

        // Generate and return JWT
        return jwtService.createToken(signUpRequest.getEmail());
    }

    public String authenticate(LoginRequest loginRequest) {
        Optional<User> user = userService.findUserByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User does not exist");
        }

        String hashedPassword = user.get().getPassword();

        if (!passwordEncoder.matches(loginRequest.getPassword(), hashedPassword)) {
            throw new InvalidDataException("Invalid Password");
        }

        // Generate and return JWT
        return jwtService.createToken(loginRequest.getEmail());
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
