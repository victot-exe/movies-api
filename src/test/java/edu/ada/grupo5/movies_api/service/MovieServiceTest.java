package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.MovieRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.tmdb.*;
import edu.ada.grupo5.movies_api.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private TMDBClientFeign tmdbClientFeign;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void testGetTrendingMovies() {
        String timeWindow = "day";
        String language = "en-US";
        ResultResponseDTO<TrendingMovieDTO> mockResponse = new ResultResponseDTO<>();
        TrendingMovieDTO trendingMovie = new TrendingMovieDTO(/* TODO: inicializar */);
        mockResponse.setResults(List.of(trendingMovie));

        when(tmdbClientFeign.getTrendingMovies(timeWindow, language)).thenReturn(mockResponse);

        ResultResponseDTO<TrendingMovieDTO> result = movieService.getTrendingMovies(timeWindow, language);

        assertThat(result).isNotNull();
        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().getFirst()).isEqualTo(trendingMovie);

        verify(tmdbClientFeign, times(1)).getTrendingMovies(timeWindow, language);
    }

    @Test
    void testGetGenres() {
        String language = "en-US";
        GenresResponseDTO mockResponse = new GenresResponseDTO();

        when(tmdbClientFeign.getGenres(language)).thenReturn(mockResponse);

        GenresResponseDTO result = movieService.getGenres(language);

        assertThat(result).isNotNull();

        verify(tmdbClientFeign, times(1)).getGenres(language);
    }

    @Test
    void testGetMovie() {
        String movieName = "Inception";
        ResultResponseDTO<MovieDTO> mockResponse = new ResultResponseDTO<>();
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Inception");
        movieDTO.setId(123);
        mockResponse.setResults(List.of(movieDTO));

        when(tmdbClientFeign.getMovie(movieName)).thenReturn(mockResponse);

        List<MovieTitleIdDTO> result = movieService.getMovie(movieName);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        MovieTitleIdDTO movieTitleIdDTO = result.getFirst();
        assertThat(movieTitleIdDTO.getTitle()).isEqualTo("Inception");
        assertThat(movieTitleIdDTO.getTmdbId()).isEqualTo(123);

        verify(tmdbClientFeign, times(1)).getMovie(movieName);
    }


    @Test
    void testSaveMovieBySearch_WhenMovieDoesNotExist() {
        Integer tmdbId = 123;
        when(movieRepository.existsByTmdbId(tmdbId)).thenReturn(false);

        Movie mockMovie = new Movie();
        mockMovie.setTmdbId(tmdbId);
        // TODO: outros campos

        when(tmdbClientFeign.searchMovieById(eq(tmdbId), anyString(), anyString())).thenReturn(mockMovie);

        movieService.saveMovieBySearch(tmdbId);

        verify(movieRepository, times(1)).save(mockMovie);
        verify(tmdbClientFeign, times(1)).searchMovieById(eq(tmdbId), anyString(), anyString());
    }

    @Test
    void testSaveMovieBySearch_WhenMovieExists() {
        int tmdbId = 123;
        when(movieRepository.existsByTmdbId(tmdbId)).thenReturn(true);

        movieService.saveMovieBySearch(tmdbId);

        verify(movieRepository, never()).save(any(Movie.class));
        verify(tmdbClientFeign, never()).searchMovieById(anyInt(), anyString(), anyString());
    }
}

