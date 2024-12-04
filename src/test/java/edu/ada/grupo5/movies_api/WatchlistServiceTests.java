package edu.ada.grupo5.movies_api;

import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.RecommendedMovieDTO;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.MovieDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.model.*;
import edu.ada.grupo5.movies_api.service.MovieService;
import edu.ada.grupo5.movies_api.service.SeriesService;
import edu.ada.grupo5.movies_api.service.WatchListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WatchlistServiceTests {

    @InjectMocks
    private WatchListService watchListService;

    @Mock
    private MovieService movieService;

    @Mock
    private SeriesService seriesService;

    @Mock
    private WatchListRepository watchListRepository;

    @Mock
    private TMDBClientFeign tmdbClientFeign;

    @BeforeEach
    void setUp() {
        User user = new User("testUser", "password", UserRole.ADMIN);
        user.setId(1);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void save_deveSalvarNaWatchlistComSucesso(){

        String tmdbId = "123";
        String title = "Clube da luta";
        MovieSerieEnum movieSerieEnum = MovieSerieEnum.MOVIE;
        WatchListStatus watchListStatus = WatchListStatus.TO_WATCH;
        boolean favorite = true;

        WatchList watchListToSave = new WatchList(tmdbId, title, movieSerieEnum, watchListStatus, favorite);
        watchListToSave.setUserId(1);

        watchListService.save(tmdbId, title, movieSerieEnum, watchListStatus, favorite);

        verify(watchListRepository, times(1)).save(watchListToSave);
        verify(movieService, times(1)).saveMovieBySearch(Integer.parseInt(tmdbId));
    }

    @Test
    public void save_deveChamarSaveMovieBySearchSeMovieSerieEnumIgualMovie(){

        String tmdbId = "123";
        MovieSerieEnum movieSerieEnum = MovieSerieEnum.MOVIE;

        watchListService.save(tmdbId, "Super-Herói: O Filme", movieSerieEnum, WatchListStatus.WATCHED, true);

        verify(movieService, times(1)).saveMovieBySearch(Integer.parseInt(tmdbId));
        verifyNoInteractions(seriesService);
    }

    @Test
    public void save_deveChamarSaveSerieBySearchSeMovieSerieEnumIgualSerie(){

        String tmdbId = "123";
        MovieSerieEnum movieSerieEnum = MovieSerieEnum.SERIE;

        watchListService.save(tmdbId, "Super-Herói: O Filme", movieSerieEnum, WatchListStatus.WATCHED, true);

        verify(seriesService, times(1)).saveSerieBySearch(Integer.parseInt(tmdbId));
        verifyNoInteractions(movieService);
    }

    @Test
    public void getAll_deveBuscarWatchlistComSucesso(){
        WatchList watchListItem = new WatchList("1", "A volta dos que não foram", MovieSerieEnum.MOVIE, WatchListStatus.WATCHED);
        when(watchListRepository.findAllByUserId(1)).thenReturn(Collections.singletonList(watchListItem));

        List<WatchListDTO> result = watchListService.getAll();

        Assertions.assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A volta dos que não foram", result.getFirst().getTitle());

        verify(watchListRepository, times(1)).findAllByUserId(1);
    }

    @Test
    public void getAll_deveRetonarListaVazia(){

        List<WatchListDTO> result = watchListService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());

        verify(watchListRepository, times(1)).findAllByUserId(1);
    }

    @Test
    public void updateWatchListStatus_deveChamarUpdateWatchListStatusDeWatchListRepository(){

        String tmdbId = "123";
        MovieSerieEnum movieSerieEnum = MovieSerieEnum.MOVIE;
        WatchListStatus watchListStatus = WatchListStatus.WATCHING;

        watchListService.updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus);

        verify(watchListRepository, times(1)).updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus, 1);
    }

    @Test
    public void deleteByTmdbIdAndByMovieSerieEnum_deveChamarDeleteByTmdbIdAndMovieSerieEnumDeWatchListRepository(){

        String tmdbId = "123";
        MovieSerieEnum movieSerieEnum = MovieSerieEnum.MOVIE;

        watchListService.deleteByTmdbIdAndByMovieSerieEnum(tmdbId, movieSerieEnum);

        verify(watchListRepository, times(1)).deleteByTmdbIdAndMovieSerieEnum(tmdbId, movieSerieEnum, 1);
    }

    @Test
    public void getRecommendedMovies_deveRetornarListaDeFilmesRecomendados(){
        ArrayList<WatchList> allFavorites = new ArrayList<>();
        allFavorites.add(new WatchList("1", "Filme A", MovieSerieEnum.MOVIE, WatchListStatus.TO_WATCH));
        allFavorites.add(new WatchList("3", "Filme C", MovieSerieEnum.MOVIE, WatchListStatus.TO_WATCH));
        allFavorites.add(new WatchList("3", "Filme C", MovieSerieEnum.MOVIE, WatchListStatus.TO_WATCH));
        allFavorites.add(new WatchList("3", "Filme C", MovieSerieEnum.MOVIE, WatchListStatus.TO_WATCH));
        allFavorites.add(new WatchList("2", "Filme B", MovieSerieEnum.MOVIE, WatchListStatus.TO_WATCH));
        allFavorites.add(new WatchList("1", "Filme A", MovieSerieEnum.MOVIE, WatchListStatus.TO_WATCH));
        allFavorites.add(new WatchList("9", "Serie A", MovieSerieEnum.SERIE, WatchListStatus.WATCHING));
        allFavorites.add(new WatchList("9", "Serie A", MovieSerieEnum.SERIE, WatchListStatus.WATCHING));
        when(watchListRepository.findAllFavorites()).thenReturn(allFavorites);

        List<RecommendedMovieDTO> result = watchListService.getRecommendedMovies();

        assertEquals(4, result.size());
        assertEquals("Filme C", result.get(0).getTitle());
        assertEquals(3L, result.get(0).getFavoriteCount());
    }

    @Test
    public void getRecommendedMovies_deveAceitarListaVaziaDeFavoritos(){
        when(watchListRepository.findAllFavorites()).thenReturn(Collections.emptyList());

        List<RecommendedMovieDTO> result = watchListService.getRecommendedMovies();

        assertEquals(0, result.size());
    }

    @Test
    public void getGenreBasedRecommendations_DeveRetornarListaDeRecomendadosComBaseNoGênero(){

        List<WatchList> watchList = Arrays.asList(
                new WatchList("1", "Filme A", MovieSerieEnum.MOVIE, WatchListStatus.WATCHED),
                new WatchList("2", "Filme B", MovieSerieEnum.MOVIE, WatchListStatus.WATCHED));

        when(watchListRepository.findAllByUserId(1)).thenReturn(watchList);

        MovieDTO filmeA = new MovieDTO();
        filmeA.setId(1);
        filmeA.setTitle("Filme A");
        filmeA.setGenre_ids(List.of(28, 1, 47, 55));

        MovieDTO filmeB = new MovieDTO();
        filmeB.setId(2);
        filmeB.setTitle("Filme B");
        filmeB.setGenre_ids(List.of(28, 12, 55, 3));

        ResultResponseDTO<MovieDTO> responseDTOFilmeA = new ResultResponseDTO<>();
        responseDTOFilmeA.setResults(List.of(filmeA));

        ResultResponseDTO<MovieDTO> responseDTOFilmeB = new ResultResponseDTO<>();
        responseDTOFilmeB.setResults(List.of(filmeB));

        when(tmdbClientFeign.getMovie("Filme A")).thenReturn(responseDTOFilmeA);
        when(tmdbClientFeign.getMovie("Filme B")).thenReturn(responseDTOFilmeB);

        MovieDTO recommendedMovie1 = new MovieDTO();
        recommendedMovie1.id = 15;
        recommendedMovie1.title = "Filme recomendado 1";

        MovieDTO recommendedMovie2 = new MovieDTO();
        recommendedMovie2.id = 16;
        recommendedMovie2.title = "Filme recomendado 2";

        ResultResponseDTO<MovieDTO> genreMovies = new ResultResponseDTO<>();
        genreMovies.setResults(List.of(recommendedMovie1, recommendedMovie2));

        when(tmdbClientFeign.getMoviesByGenre(anyString())).thenReturn(genreMovies);

        List<RecommendedMovieDTO> recommendedMovieDTOS = watchListService.getGenreBasedRecommendations();

        assertNotNull(recommendedMovieDTOS);
        assertEquals(2, recommendedMovieDTOS.size());
        assertEquals("Filme recomendado 1", recommendedMovieDTOS.get(0).getTitle());

        verify(watchListRepository, times(1)).findAllByUserId(anyInt());
        verify(tmdbClientFeign, times(2)).getMovie(any());
        verify(tmdbClientFeign, times(2)).getMoviesByGenre(anyString());
    }
}
