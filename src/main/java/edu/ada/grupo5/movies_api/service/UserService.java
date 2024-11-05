package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//TODO : criar tratamento de excecoes personalizado
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails findUserByLogin(String login) {
        UserDetails userDetails = userRepository.findByLogin(login);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
