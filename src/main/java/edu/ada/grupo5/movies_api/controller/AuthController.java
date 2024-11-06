package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.AuthLoginDTO;
import edu.ada.grupo5.movies_api.dto.RegisterDTO;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.service.TokenService;
import edu.ada.grupo5.movies_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

//TODO : refatorar metodos

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(@RequestBody @Valid AuthLoginDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .message("Token generated sucessfully")
                .timestamp(Instant.now())
                .data(token)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());

        userService.save(user);

        return ResponseEntity.ok().build();
    }
}
