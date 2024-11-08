package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.dto.RegisterDTO;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.UserDTO;
import edu.ada.grupo5.movies_api.model.Token;
import edu.ada.grupo5.movies_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RegisterService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public ResponseDTO<String> register(UserDTO data) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), data.login(), encryptedPassword, data.role());
        Token token = tokenService.generateToken(user);
        return ResponseDTO.<String>builder()
                .message("Account created successfully")
                .timestamp(Instant.now())
                .data(token.getToken())
                .build();
    }
}
