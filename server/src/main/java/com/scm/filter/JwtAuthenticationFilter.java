package com.scm.filter;

import com.scm.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest httpServletRequest,
            @NonNull HttpServletResponse httpServletResponse,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get all cookies from the request
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            // Retrieve the JWT from the "jwt" cookie
            Cookie jwtCookie = Arrays.stream(cookies)
                    .filter(cookie -> "jwt".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            if (jwtCookie != null) {
                String jwt = jwtCookie.getValue();
                try {
                    SecurityContextHolder.getContext().setAuthentication(jwtService.validateToken(jwt));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
