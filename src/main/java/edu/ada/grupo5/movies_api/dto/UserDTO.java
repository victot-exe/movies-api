package edu.ada.grupo5.movies_api.dto;

import edu.ada.grupo5.movies_api.model.UserRole;

public record UserDTO(Integer id, String login, String password, UserRole role) {
}
