package edu.ada.grupo5.movies_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String role;
}
