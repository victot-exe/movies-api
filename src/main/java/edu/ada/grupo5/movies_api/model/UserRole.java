package edu.ada.grupo5.movies_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    @JsonProperty("admin")
    ADMIN("admin"),
    @JsonProperty("user")
    USER("user");

    private String role;
}
