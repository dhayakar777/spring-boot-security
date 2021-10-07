package com.tutorials.tutorialservice.controllers;

import com.tutorials.tutorialservice.exception.RunTimeExceptionPlaceHolder;
import com.tutorials.tutorialservice.models.web.CreateUserResponse;
import com.tutorials.tutorialservice.models.web.JwtAuthenticationResponse;
import com.tutorials.tutorialservice.models.web.LoginRequest;
import com.tutorials.tutorialservice.models.web.SignUpRequest;
import com.tutorials.tutorialservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) throws RunTimeExceptionPlaceHolder {
        CreateUserResponse userResponse = userService.registerUser(signUpRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        JwtAuthenticationResponse authenticationResponse = userService.authenticate(loginRequest);
        return  ResponseEntity.ok(authenticationResponse);
    }
}
