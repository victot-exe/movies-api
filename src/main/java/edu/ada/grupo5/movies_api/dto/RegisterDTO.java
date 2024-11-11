package edu.ada.grupo5.movies_api.dto;

import edu.ada.grupo5.movies_api.model.Token;
import edu.ada.grupo5.movies_api.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
