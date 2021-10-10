package com.tutorials.tutorialservice.models.web;

import com.tutorials.tutorialservice.models.Role;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "GetUserResponse", description = "User response model")
public class GetUserResponse {

    private String userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
}
