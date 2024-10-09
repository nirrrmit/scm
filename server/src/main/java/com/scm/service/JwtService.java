package com.scm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.scm.exception.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String createToken(String email) {
        System.out.println("Creating token for email: " + email);
        Date now = new Date();

        try {
            return JWT.create()
                    .withSubject(email)
                    .withIssuedAt(now)
                    .sign(Algorithm.HMAC256(secretKey));
        } catch (JWTCreationException e) {
            throw new JwtException("Failed to create JWT token");
        }
    }

//    public Authentication validateToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//
//        JWTVerifier verifier = JWT.require(algorithm)
//                .build();
//
//        DecodedJWT decoded = verifier.verify(token);
//
//        UserDto user = userService.findByLogin(decoded.getSubject());
//
//        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//    }

}