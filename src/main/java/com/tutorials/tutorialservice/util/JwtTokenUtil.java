package com.tutorials.tutorialservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tutorials.tutorialservice.models.User;
import com.tutorials.tutorialservice.models.web.JwtAuthenticationResponse;
import com.tutorials.tutorialservice.models.web.LoginRequest;
import com.tutorials.tutorialservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

     private final UserRepository userRepository;
    private static final long ACCESS_TOKEN_VALIDITY= 10 * 60 * 1000;
    private static final long REFRESH_TOKEN_VALIDITY= 10 * 60 * 1000;

    @Value("${spring.jwt.secret}")
    private String jwtSecret;
     public JwtTokenUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
     }

    public JwtAuthenticationResponse generateToken(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUserName(loginRequest.getUserName());
        User userByUserName = user.orElseThrow(()-> new RuntimeException("User not found with given user name"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userByUserName.getRoles().forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        org.springframework.security.core.userdetails.User coreUser = new org.springframework.security.core.userdetails.User(
                userByUserName.getUserName(), userByUserName.getPassword(), authorityList);
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        String accessToken = JWT.create().withSubject(userByUserName.getUserName())
                             .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                             .withIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString())
                             .withIssuedAt(new Date())
                             .withClaim("roles", coreUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).
                                     collect(Collectors.toList()))
                             .sign(algorithm);
        String refreshToken = JWT.create().withSubject(userByUserName.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY))
                .withIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString())
                .sign(algorithm);
        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }
}
