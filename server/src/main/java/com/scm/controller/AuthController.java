package com.scm.controller;

import com.scm.dto.LoginRequest;
import com.scm.dto.SignUpRequest;
import com.scm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        ResponseCookie responseCookie = authService.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseCookie responseCookie = authService.authenticate(loginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }
}
