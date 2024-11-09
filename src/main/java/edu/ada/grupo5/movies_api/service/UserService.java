package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.model.Token;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.service.exception.RegisterErrorException;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;


//TODO : criar tratamento de excecoes personalizado
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails findUserByLogin(String login) {
        UserDetails userDetails = userRepository.findByLogin(login);
        if (userDetails == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return userDetails;
    }

    public User save(User user) {
        if (userRepository.existsBylogin(user.getLogin())) {
            throw new RegisterErrorException("User already registered");
        }
        return userRepository.save(user);
    }

    public ResponseDTO<String> delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return ResponseDTO.<String>builder()
                .timestamp(Instant.now())
                .message("User deleted successfully")
                .data("")
                .build();
    }

    public void updateToken(Integer userId, Token token) {
        userRepository.updateToken(userId, token);
    }

}
