package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MapRoleToUsersRequest", description = "Mapping role to user request model")
public class MapRoleToUsersRequest {
    private List<String> userNames;
}
