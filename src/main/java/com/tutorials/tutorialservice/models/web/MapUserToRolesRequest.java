package com.tutorials.tutorialservice.models.web;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MapUserToRolesRequest", description = "Mapping  user to role request model")
public class MapUserToRolesRequest {
    private List<String> roleNames;
}
