package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.service.exception.RegisterErrorException;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


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
}
