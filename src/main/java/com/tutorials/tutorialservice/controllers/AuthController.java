package com.tutorials.tutorialservice.controllers;

import com.tutorials.tutorialservice.exception.RunTimeExceptionPlaceHolder;
import com.tutorials.tutorialservice.models.web.CreateUserResponse;
import com.tutorials.tutorialservice.models.web.JwtAuthenticationResponse;
import com.tutorials.tutorialservice.models.web.LoginRequest;
import com.tutorials.tutorialservice.models.web.SignUpRequest;
import com.tutorials.tutorialservice.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Api(tags = "Authentication Controller")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ApiOperation(value = "Register a new user")
    public ResponseEntity<?> registerUser(@ApiParam(name = "signUpRequest", value = "Sign up request body to sign up a user",
            required = true) @RequestBody @Valid SignUpRequest signUpRequest) throws RunTimeExceptionPlaceHolder {
        CreateUserResponse userResponse = userService.registerUser(signUpRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @ApiOperation(value = "Login using username, password and generate  JWT token")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@ApiParam(name = "loginRequest",
            value = "User name and password to generate token") @RequestBody @Valid LoginRequest loginRequest) throws Exception {
        JwtAuthenticationResponse authenticationResponse = userService.authenticate(loginRequest);
        return  ResponseEntity.ok(authenticationResponse);
    }
}
