package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.TokenRepository;
import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.model.Token;
import edu.ada.grupo5.movies_api.model.TokenType;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.model.UserRole;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    private User user;

    private Token token;

    @BeforeEach
    void setUp() {
        token = new Token();
        token.setId(1);
        token.setToken("token");
        token.setTokenType(TokenType.BEARER);
        token.setRevoked(false);
        token.setExpired(false);

        ReflectionTestUtils.setField(tokenService, "secret", "my-secret-key");

        user = new User();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setLogin("login");
        user.setPassword("password");
    }

    @Test
    public void deve_gerar_um_token(){

        Token result = tokenService.generateToken(user);
        assertNotNull(result);
    }

    @Test
    public void deve_lancar_ResourceNotFoundException_com_a_mensagem_token_not_found_metodo_isValidToken(){
        when(tokenRepository.findByToken(anyString())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> tokenService.isTokenValid(anyString()));
        verify(tokenRepository, times(1)).findByToken(anyString());
        assertEquals("Token not found", exception.getMessage());
    }

    @Test
    public void deve_lancar_ResourceNotFoundException_token_not_found_metodo_generateResponse(){
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class ,
                () ->tokenService.generateResponse(""));

        assertEquals("Token not found", exception.getMessage());
    }

    @Test
    public void deve_retornar_ResponseDTO(){
        ResponseDTO<String> result = tokenService.generateResponse("token");
        assertNotNull(result);
        assertEquals("token", result.getData());
    }

    @Test
    public void deve_chamar_tokenRepository_findByToken_and_dele_and_userRepository_findUserByToken_and_save(){
        when(tokenRepository.findByToken(anyString())).thenReturn(token);
        when(userRepository.findUserByToken(token)).thenReturn(user);

        tokenService.invalidateToken(anyString());

        verify(userRepository, times(1)).findUserByToken(token);
        verify(userRepository, times(1)).save(user);
        verify(tokenRepository, times(2)).findByToken(anyString());
        verify(tokenRepository, times(1)).delete(token);
    }

}

//    @Test
//    public void deve_retornar_uma_string_de_token(){
//        when(tokenRepository.findByToken(anyString())).thenReturn(token);
//
//        String result = tokenService.isTokenValid(anyString());//TODO Entender o pq ele falha na validação
//        verify(tokenRepository, times(1)).findByToken(anyString());
//        assertInstanceOf(String.class, result);
//    }


