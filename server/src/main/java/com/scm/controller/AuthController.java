package com.scm.controller;

import com.scm.dto.AuthResponse;
import com.scm.dto.SignUpRequest;
import com.scm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

//    TODO: Implement JwtService OR write methods here
//    private final JwtService jwtService;

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        System.out.println("PENGUIN");
        AuthResponse authResponse = authService.register(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller is working!";
    }


//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest loginRequest) {
//        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
//
//        return ResponseEntity.ok(loginResponse);
//    }
}
