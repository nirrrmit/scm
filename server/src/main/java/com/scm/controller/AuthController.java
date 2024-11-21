package com.scm.controller;

import com.scm.dto.LoginRequest;
import com.scm.dto.SignUpRequest;
import com.scm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody SignUpRequest signUpRequest) {
        String jwt = authService.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(@RequestBody LoginRequest loginRequest) {
        String jwt = authService.authenticate(loginRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();
    }
}
