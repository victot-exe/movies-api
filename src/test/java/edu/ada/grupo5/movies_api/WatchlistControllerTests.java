package edu.ada.grupo5.movies_api;

import edu.ada.grupo5.movies_api.controller.WatchListController;
import edu.ada.grupo5.movies_api.dto.RecommendedMovieDTO;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import edu.ada.grupo5.movies_api.service.WatchListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WatchlistControllerTests {

    @InjectMocks
    private WatchListController watchListController;

    @Mock
    private WatchListService watchListService;

    private String tmdbId;
    private String title;
    private MovieSerieEnum movieSerieEnum;
    private WatchListStatus watchListStatus;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        tmdbId = "123";
        title = "Filme xpto";
        movieSerieEnum = MovieSerieEnum.MOVIE;
        watchListStatus = WatchListStatus.TO_WATCH;
    }

    @Test
    public void save_DeveSalvarNaWatchlistComSucesso(){

        boolean favorite = true;
        ResponseEntity<Void> response = watchListController.save(tmdbId, title, movieSerieEnum, watchListStatus, favorite);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        verify(watchListService, times(1)).save(tmdbId,title,movieSerieEnum,watchListStatus,favorite);
    }

    @Test
    public void getAll_DeveBuscarListaDaWatchlist(){

        List<WatchListDTO> retorno = watchListController.getAll();

        assertNotNull(retorno);

        verify(watchListService, times(1)).getAll();
    }

    @Test
    public void updateWatchListStatus_DeveAtualizarStatusNaWatchList(){

        ResponseEntity<Void> response = watchListController.updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(watchListService, times(1)).updateWatchListStatus(any(), any(), any());
    }

    @Test
    public void deleteByTmdbIdAndByMovieSerieEnum_DeveDeletarItemDaWatchlist(){
        ResponseEntity<Void> response = watchListController.deleteByTmdbIdAndByMovieSerieEnum(tmdbId, movieSerieEnum);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(watchListService, times(1)).deleteByTmdbIdAndByMovieSerieEnum(any(), any());
    }

    @Test
    public void getRecommendations_DeveBuscarRecomendacoes(){

        RecommendedMovieDTO filmeA = new RecommendedMovieDTO("Filme A", "123", 2L);
        RecommendedMovieDTO filmeB = new RecommendedMovieDTO("Filme B", "001", 1L);
        List<RecommendedMovieDTO> recommendedMovies = new ArrayList<>();
        recommendedMovies.add(filmeA);
        recommendedMovies.add(filmeB);

        when(watchListService.getRecommendedMovies()).thenReturn(recommendedMovies);

        ResponseEntity<List<RecommendedMovieDTO>> response = watchListController.getRecommendations();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Filme A", response.getBody().get(0).getTitle());

        verify(watchListService, times(1)).getRecommendedMovies();
    }

    @Test
    public void getGenreBasedRecommendations_DeveBuscarRecomendacoesComBaseNoGenero() {
        RecommendedMovieDTO filmeA = new RecommendedMovieDTO("Filme A", "123", 2L);
        RecommendedMovieDTO filmeB = new RecommendedMovieDTO("Filme B", "001", 1L);
        List<RecommendedMovieDTO> recommendedMovies = new ArrayList<>();
        recommendedMovies.add(filmeA);
        recommendedMovies.add(filmeB);

        when(watchListService.getGenreBasedRecommendations()).thenReturn(recommendedMovies);

        ResponseEntity<List<RecommendedMovieDTO>> response = watchListController.getGenreBasedRecommendations();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Filme A", response.getBody().get(0).getTitle());

        verify(watchListService, times(1)).getGenreBasedRecommendations();

    }
}
