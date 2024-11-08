package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.dto.RegisterDTO;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RegisterService {

    @Autowired
    private UserService userService;

    public ResponseDTO<String> register(RegisterDTO data) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());

        userService.save(user);
        return ResponseDTO.<String>builder()
                .message("Account created successfully")
                .timestamp(Instant.now())
                .data("")
                .build();
    }
}
