package com.scm.service;

import com.scm.dto.AuthResponse;
import com.scm.dto.SignUpRequest;
import com.scm.exception.DuplicatedUserInfoException;
import com.scm.exception.InvalidDataException;
import com.scm.model.entity.User;
import com.scm.model.entity.UserAuth;
import com.scm.repository.UserAuthRepository;
import com.scm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(SignUpRequest signUpRequest) {

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match!");
        }

        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new DuplicatedUserInfoException("Email already exists");
        }

        // Create and save User entity
        User newUser = User.builder()
                        .email(signUpRequest.getEmail())
                        .build();
        userRepository.save(newUser);

        // Create and save UserAuth entity for credentials
        UserAuth userDetails = UserAuth.builder()
                                        .user(newUser)
                                        .password(hashPassword(signUpRequest.getPassword()))
                                        .build();

        userAuthRepository.save(userDetails);

        return new AuthResponse("token");
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}