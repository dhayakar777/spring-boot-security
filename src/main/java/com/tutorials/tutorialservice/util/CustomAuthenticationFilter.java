package com.tutorials.tutorialservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tutorials.tutorialservice.models.web.JwtAuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final long ACCESS_TOKEN_VALIDITY= 10 * 60 * 1000;
    private static final long REFRESH_TOKEN_VALIDITY= 10 * 60 * 1000;

    @Value("${spring.jwt.secret}")
    private String jwtSecret;

   // private final AuthenticationManager authenticationManager;

   /* public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        String accessToken = JWT.create().withSubject(user.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                            .withIssuer(request.getRequestURI())
                            .withIssuedAt(new Date()).withClaim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
        String refreshToken = JWT.create().withSubject(user.getUsername())
                              .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY))
                              .withIssuer(request.getRequestURI())
                              .sign(algorithm);
       *//* response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);*//*
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new JwtAuthenticationResponse(accessToken, refreshToken);
    }*/
}
