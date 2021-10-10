package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "CreateUserResponse", description = "Create user response model")
public class CreateUserResponse {

    private String userId;
    private String userName;
}
