package com.tutorials.tutorialservice.models.web;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

}
