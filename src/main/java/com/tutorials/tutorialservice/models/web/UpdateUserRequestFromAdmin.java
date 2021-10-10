package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "UpdateUserRequestFromAdmin", description = "Updating a user request from admin model")
public class UpdateUserRequestFromAdmin {

    @Size(max = 40, message = "First Name length should not be grater than 40 characters")
    private String firstName;

    private String lastName;

    @Size(max = 40, message = "email length should not be grater than 40 characters")
    @Email
    private String email;

    private List<String> roles = new ArrayList<>();
}
