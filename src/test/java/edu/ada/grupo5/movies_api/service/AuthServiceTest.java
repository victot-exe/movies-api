package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void deve_retornar_ResourceNotFoundException() {

        when(userRepository.findByLogin(anyString())).thenThrow(UsernameNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                () -> authService.loadUserByUsername(anyString()));
        verify(userRepository, times(1)).findByLogin(anyString());
    }
}
