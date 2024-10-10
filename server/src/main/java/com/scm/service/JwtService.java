package com.scm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.scm.exception.JwtException;
import com.scm.model.entity.User;
import com.scm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    public String createToken(String email) {

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

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        User user = userRepository.findByEmail(decoded.getSubject()).get();
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}