package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.UserDTO;
import edu.ada.grupo5.movies_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/get-user/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable("login") String login) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.findUserByLogin(login));
    }
}
