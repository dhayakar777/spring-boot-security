package com.tutorials.tutorialservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      if(request.getServletPath().equals("/api/authentication/login")) {
          filterChain.doFilter(request, response);
      } else {
          String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
          if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
              try{
                  String token = authorizationHeader.substring("Bearer ".length());
                  Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
                  JWTVerifier verifier = JWT.require(algorithm).build();
                  DecodedJWT decodedJWT = verifier.verify(token);
                  String userName = decodedJWT.getSubject();
                  String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                  List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                  Stream.of(roles).forEach(role-> {
                      authorities.add(new SimpleGrantedAuthority(role));
                  });
                  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  filterChain.doFilter(request, response);
              } catch (ExpiredJwtException exception){
                  log.error("JWT token has expired");
                  throw new RuntimeException("JWT token is expired");
              } catch (SignatureException exception) {
                  log.error("JWT signature exception");
                  throw new RuntimeException("JWT token signature exception");
              }
          } else {
              filterChain.doFilter(request, response);
          }
      }
    }
}
