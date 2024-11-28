package edu.ada.grupo5.movies_api;

import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.*;
import edu.ada.grupo5.movies_api.service.WatchListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTests {

    @InjectMocks
    private WatchListService watchListService;

    @Mock
    private WatchListRepository watchListRepository;

    private WatchList watchList;


    @BeforeEach
    void setUp() {
        // Configura um contexto de segurança com um usuário mockado
        User user = new User("testUser", "password", UserRole.ADMIN);
        user.setId(1);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void getAllWatchlist_deveBuscarWatchlistComSucesso(){
        WatchList watchListItem = new WatchList("1", "A volta dos que não foram", MovieSerieEnum.MOVIE, WatchListStatus.WATCHED);
        when(watchListRepository.findAllByUserId(1)).thenReturn(Collections.singletonList(watchListItem));

        List<WatchListDTO> result = watchListService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("A volta dos que não foram", result.get(0).getTitle());

        verify(watchListRepository, times(1)).findAllByUserId(1);
    }

}
