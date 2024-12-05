package edu.ada.grupo5.movies_api;

import edu.ada.grupo5.movies_api.Repositories.UserRepository;
import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.controller.WatchListController;
import edu.ada.grupo5.movies_api.dto.RecommendedMovieDTO;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.*;
import edu.ada.grupo5.movies_api.service.WatchListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        classes = MoviesApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WatchlistControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WatchListService watchListService;

    @Mock
    private WatchListRepository watchListRepository;

    @MockBean
    private UserRepository userRepository;

    private User mockUser;

    private String tmdbId;
    private String title;
    private MovieSerieEnum movieSerieEnum;
    private WatchListStatus watchListStatus;
    private boolean favorite;

    @BeforeEach
    public void setup() {
        tmdbId = "123";
        title = "Lord of the Rings";
        movieSerieEnum = MovieSerieEnum.MOVIE;
        watchListStatus = WatchListStatus.WATCHED;

        mockUser = new User();
        mockUser.setId(1);
        mockUser.setLogin("admin");
        mockUser.setPassword("password");
        mockUser.setRole(UserRole.ADMIN);

        when(userRepository.findUserByLogin("admin")).thenReturn(mockUser);
    }

    @Test
    @DisplayName("Deve salvar item na Watchlist e retornar HttpStatus CREATED")
    @WithMockUser(username = "admin", roles = "admin")
    public void save() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/watchlist")
                        .param("tmdbId", tmdbId)
                        .param("title", title)
                        .param("movieSerieEnum", String.valueOf(movieSerieEnum))
                        .param("watchListStatus", String.valueOf(watchListStatus))
                        .param("favorite", String.valueOf(true))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(watchListService, times(1)).save(tmdbId,title,movieSerieEnum,watchListStatus,true);
    }

    @Test
    @DisplayName("Deve buscar lista da WatchList e retornar HttpStatus FOUND")
    @WithMockUser(username = "admin", roles = "admin")
    public void getAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/watchlist/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

        verify(watchListService, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve atualizar status na Watchlist e retornar HttpStatus NO_CONTENT")
    @WithMockUser(username = "admin", roles = "admin")
    public void updateWatchListStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/watchlist/{tmdbId}", 123)
                        .param("movieSerieEnum", "MOVIE")
                        .param("watchListStatus", "WATCHED")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(watchListService, times(1)).updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus);
    }

    @Test
    @DisplayName("Deve deletar item da Watchlist e retornar HttpStatus NO_CONTENT")
    @WithMockUser(username = "admin", roles = "admin")
    public void deleteByTmdbIdAndByMovieSerieEnum() throws Exception {
        when(watchListRepository.findByTmdbIdAndUserIdAndMovieSerieEnum(tmdbId, mockUser.getId(), movieSerieEnum))
                .thenReturn(new WatchList(tmdbId, title, movieSerieEnum, watchListStatus));

         mockMvc.perform(MockMvcRequestBuilders
                        .delete("/watchlist/{tmdbId}", tmdbId)
                        .param("movieSerieEnum", "MOVIE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(watchListService, times(1)).deleteByTmdbIdAndByMovieSerieEnum(any(), any());
    }

    @Test
    @DisplayName("Deve buscar recomendações de filmes e HttpStatus FOUND")
    @WithMockUser(username = "admin", roles = "admin")
    public void getRecommendations() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/watchlist/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

        verify(watchListService, times(1)).getRecommendedMovies();
    }

    @Test
    @DisplayName("Deve buscar recomendações com base no gênero e HttpStatus FOUND")
    @WithMockUser(username = "admin", roles = "admin")
    public void getGenreBasedRecommendations() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/watchlist/genrerecommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

        verify(watchListService, times(1)).getGenreBasedRecommendations();
    }
}
