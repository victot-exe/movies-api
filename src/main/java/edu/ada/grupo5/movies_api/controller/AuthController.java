package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.AuthLoginDTO;
import edu.ada.grupo5.movies_api.dto.RegisterDTO;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.service.LoginService;
import edu.ada.grupo5.movies_api.service.LogoutService;
import edu.ada.grupo5.movies_api.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//TODO : revisar refatoramento metodos

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LogoutService logoutService;


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(@RequestBody @Valid AuthLoginDTO data) {
        ResponseDTO<String> response = loginService.login(data);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody @Valid RegisterDTO data) {
        ResponseDTO<String> response = registerService.register(data);
        return ResponseEntity.ok(response);
    }
}
