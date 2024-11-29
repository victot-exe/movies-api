package edu.ada.grupo5.movies_api;

import edu.ada.grupo5.movies_api.controller.WatchListController;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WatchlistControllerTests {

    @InjectMocks
    private WatchListController watchListController;

    @Mock
    private WatchListService watchListService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save_DeveSalvarNaWatchlistComSucesso(){
        String tmdbId = "123";
        String title = "Filme xpto";
        MovieSerieEnum movieSerieEnum = MovieSerieEnum.MOVIE;
        WatchListStatus watchListStatus = WatchListStatus.TO_WATCH;
        boolean favorite = true;
        ResponseEntity<Object> response = watchListController.save(tmdbId, title, movieSerieEnum, watchListStatus, favorite);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        verify(watchListService, times(1)).save(tmdbId,title,movieSerieEnum,watchListStatus,favorite);
    }
}
