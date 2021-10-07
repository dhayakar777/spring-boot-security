package com.tutorials.tutorialservice.models.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum Roles {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_MANAGER,
    ROLE_SUPER_ADMIN;

}
