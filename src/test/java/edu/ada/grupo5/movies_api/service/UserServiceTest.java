package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.TokenRepository;
import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.dto.UserDTO;
import edu.ada.grupo5.movies_api.model.Token;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.model.UserRole;
import edu.ada.grupo5.movies_api.service.exception.RegisterErrorException;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    private User user;

    private Token token;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setLogin("login");
        user.setPassword("password");


        token = new Token();
        token.setToken("token");
    }

    @Test
    public void deve_executar_pelo_menos_uma_vez_findByLogin_retornar_uma_excecao_ResourceNotFound(){
        when(userRepository.findByLogin(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> userService.findUserDetailsByLogin(anyString()));
        verify(userRepository, times(1)).findByLogin(anyString());
    }

    @Test
    public void deve_executar_pelo_menos_uma_vez_finUserByLogin_e_retornar_um_userDTO(){
        when(userRepository.findUserByLogin(anyString())).thenReturn(user);

        UserDTO result = userService.findUserDTOByLogin(anyString());

        verify(userRepository, times(1)).findUserByLogin(anyString());
        assertNotNull(result);
        assertInstanceOf(UserDTO.class, result);
    }

    @Test
    public void deve_salvar_usuario() {

        when(userRepository.save(user)).thenReturn(user);

        User salvo = userService.save(user);
        assertNotNull(salvo);
        assertEquals(1, salvo.getId());
        assertEquals(UserRole.ADMIN, salvo.getRole());
        assertEquals("login", salvo.getLogin());
        assertEquals("password", salvo.getPassword());
    }

    @Test
    public void deve_retornar_uma_excecao_de_RegisterErrorException(){
        when(userRepository.existsBylogin(anyString())).thenReturn(true);
        assertThrows(RegisterErrorException.class, () -> userService.save(user));
    }

    @Test
    public void deve_retornar_o_usuario_pelo_login(){

        when(userRepository.findUserByLogin(anyString())).thenReturn(user);

        User retorno = userService.findUserByLogin("login");

        verify(userRepository, times(1)).findUserByLogin("login");
        assertNotNull(retorno);
        assertEquals(1, retorno.getId());
        assertEquals(UserRole.ADMIN, retorno.getRole());
        assertEquals("login", retorno.getLogin());
        assertEquals("password", retorno.getPassword());

    }

    @Test
    public void deve_executar_pelo_menos_uma_vez_tokenRepository_save_and_updateToken(){

        userService.updateToken(user, token);
        //Verifica se os metodos internos do updateToken estão sendo chamados
        verify(tokenRepository, times(1)).save(token);
        verify(userRepository, times(1)).updateToken(user.getId(), token);
    }

    @Test
    public void deve_retornar_User_e_executar_findUserByLogin(){
        when(userRepository.findUserByLogin(anyString())).thenReturn(user);
        User retorno = userService.findUserByLogin(anyString());
        verify(userRepository, times(1)).findUserByLogin(anyString());
        assertNotNull(retorno);
        assertInstanceOf(User.class, retorno);
    }

}
