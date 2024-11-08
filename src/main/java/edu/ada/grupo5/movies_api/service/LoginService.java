package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.dto.AuthLoginDTO;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import edu.ada.grupo5.movies_api.service.exception.ValidationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseDTO<String> login(AuthLoginDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return tokenService.generateResponse(token.getToken());

        } catch (Exception e) {
            throw new ValidationErrorException("Invalid username or password.");
        }
    }
}
