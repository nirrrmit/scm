package com.scm.service;

import com.scm.dto.LoginRequest;
import com.scm.dto.SignUpRequest;
import com.scm.exception.DuplicateUserException;
import com.scm.exception.InvalidDataException;
import com.scm.exception.UserDoesNotExistException;
import com.scm.model.entity.User;
import com.scm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public ResponseCookie register(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match!");
        }

        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new DuplicateUserException("Email already exists");
        }

        // Create and save User entity
        User newUser = User.builder()
                        .email(signUpRequest.getEmail())
                        .password(hashPassword(signUpRequest.getPassword()))
                        .build();

        userRepository.save(newUser);

        String jwt = jwtService.createToken(signUpRequest.getEmail());

        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();
    }

    public ResponseCookie authenticate(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if(user.isEmpty()) {
            throw new UserDoesNotExistException("User does not exist");
        }

        String hashedPassword = user.get().getPassword();

        if(!passwordEncoder.matches(loginRequest.getPassword(), hashedPassword)) {
            throw new InvalidDataException("Invalid Password");
        }

        String jwt = jwtService.createToken(loginRequest.getEmail());

        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
