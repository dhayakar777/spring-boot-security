package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "UpdateUserRequest", description = "Updating a user request model")
public class UpdateUserRequest {
    @Size(min = 6, max = 20, message = "password length should not be between 6 and 20 characters")
    private String password;

    @Size(max = 40, message = "First Name length should not be grater than 40 characters")
    private String firstName;

    private String lastName;

    @Size(max = 40, message = "email length should not be grater than 40 characters")
    @Email
    private String email;
}
