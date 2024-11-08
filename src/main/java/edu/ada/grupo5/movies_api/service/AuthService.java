package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) //throws UsernameNotFoundException
    {
        try {
            return userRepository.findByLogin(username);
        } catch (UsernameNotFoundException e) {
            throw new ResourceNotFoundException("User not found: " + username);
        }
    }
}
