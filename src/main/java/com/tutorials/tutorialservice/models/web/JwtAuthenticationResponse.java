package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.Value;

@Value
@ApiModel(value = "JwtAuthenticationResponse", description = "Jwt authentication response model")
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

}
