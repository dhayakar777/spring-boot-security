package com.tutorials.tutorialservice.models.web;

import com.tutorials.tutorialservice.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    private String userId;

    @NotBlank
    @Size(max = 40, message = "First name length shouldn't be greater than 40 characters")
    private String firstName;
    @NotBlank
    @Size(max = 40, message = "Last Name length shouldn't be greater than 40 characters")
    private String lastName;
    @NotBlank
    @Size(max = 20, message = "User Name length shouldn't be greater than 40 characters")
    private String userName;
    @NotBlank
    @Email
    @Size(max = 30, message = "Email length shouldn't be greater than 40 characters")
    private String email;

    @NotBlank
    @Size(min=6, max = 20, message = "Password length shouldn't be greater than 40 characters")
    private String password;
}
