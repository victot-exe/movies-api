package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.MovieRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.tmdb.*;
import edu.ada.grupo5.movies_api.model.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
    @Tag("trending-movies")
    @DisplayName("Should return a list of trending movies for the given time window and language")
    void get_trending_movies_should_return_trending_movies() {
        String timeWindow = "day";
        String language = "en-US";

        TrendingMovieDTO trendingMovie = TrendingMovieDTO.builder()
                .id(1262983)
                .title("MadS")
                .overview("A teenager stops off to see his dealer to test a new drug before heading off for a night of partying. On the way home, he picks up an injured woman and the night takes a surreal turn.")
                .backdropPath("/3xgcQHcYxGOrJNbwgXRGPPHCXVu.jpg")
                .originalTitle("MadS")
                .posterPath("/3xgcQHcYxGOrJNbwgXRGPPHCXVu.jpg")
                .mediaType("movie")
                .adult(false)
                .originalLanguage("fr")
                .genreIds(List.of(27, 53))
                .popularity(1.915)
                .releaseDate("2024-11-15")
                .video(false)
                .voteAverage(0)
                .voteCount(0)
                .build();

        ResultResponseDTO<TrendingMovieDTO> mockResponse = ResultResponseDTO.<TrendingMovieDTO>builder()
                .page(1)
                .results(List.of(trendingMovie))
                .totalPages(1)
                .totalResults(1)
                .build();

        when(tmdbClientFeign.getTrendingMovies(timeWindow, language)).thenReturn(mockResponse);

        ResultResponseDTO<TrendingMovieDTO> result = movieService.getTrendingMovies(timeWindow, language);

        assertThat(result).isNotNull();
        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().getFirst()).isEqualTo(trendingMovie);

        verify(tmdbClientFeign, times(1)).getTrendingMovies(timeWindow, language);
    }


    @Test
    @Tag("genres")
    @DisplayName("Should return a list of genres for the given language")
    void get_genres_should_return_genres() {
        String language = "en-US";

        GenreDTO horrorGenre = GenreDTO.builder()
                .id(27)
                .name("Horror")
                .build();

        GenreDTO sciFiGenre = GenreDTO.builder()
                .id(878)
                .name("Science Fiction")
                .build();

        GenresResponseDTO genresResponseDTO = GenresResponseDTO.builder()
                .genres(List.of(horrorGenre, sciFiGenre))
                .build();

        when(tmdbClientFeign.getGenres(language)).thenReturn(genresResponseDTO);

        GenresResponseDTO result = movieService.getGenres(language);

        assertThat(result).isNotNull();
        assertThat(result.getGenres()).hasSize(2);
        assertThat(result.getGenres()).contains(horrorGenre, sciFiGenre);

        verify(tmdbClientFeign, times(1)).getGenres(language);
    }

    @Test
    @Tag("search")
    @DisplayName("Should return a list of movies matching the given movie name")
    void get_movie_should_return_matching_movies() {
        String movieName = "28 Days Later";
        ResultResponseDTO<MovieDTO> mockResponse = new ResultResponseDTO<>();
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("28 Days Later");
        movieDTO.setId(170);
        mockResponse.setResults(List.of(movieDTO));

        when(tmdbClientFeign.getMovie(movieName)).thenReturn(mockResponse);

        List<MovieTitleIdDTO> result = movieService.getMovie(movieName);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        MovieTitleIdDTO movieTitleIdDTO = result.getFirst();
        assertThat(movieTitleIdDTO.getTitle()).isEqualTo("28 Days Later");
        assertThat(movieTitleIdDTO.getTmdbId()).isEqualTo(170);

        verify(tmdbClientFeign, times(1)).getMovie(movieName);
    }

    @Test
    @Tag("save-movie")
    @DisplayName("Should not save a movie when it already exists in the repository")
    void save_movie_by_search_should_not_save_if_movie_exists() {
        int movieId = 170;
        when(movieRepository.existsByTmdbId(movieId)).thenReturn(true);

        movieService.saveMovieBySearch(movieId);

        verify(movieRepository, never()).save(any(Movie.class));
        verify(tmdbClientFeign, never()).searchMovieById(anyInt(), anyString(), anyString());
    }

    @Test
    @Tag("save-movie")
    @DisplayName("Should save a movie when it does not exist in the repository")
    void save_movie_by_search_should_save_if_movie_does_not_exist() {
        int movieId = 170;
        Movie mockMovie = new Movie();
        mockMovie.setTmdbId(movieId);
        mockMovie.setTitle("28 Days Later");
        mockMovie.setAdult(false);
        mockMovie.setImdb_id("tt0289043");
        mockMovie.setOriginal_language("en");
        mockMovie.setOriginal_title("28 Days Later");
        mockMovie.setRelease_date("2002-10-31");
        mockMovie.setVote_average(7.224);

        when(movieRepository.existsByTmdbId(movieId)).thenReturn(false);
        when(tmdbClientFeign.searchMovieById(movieId, "", "en-US")).thenReturn(mockMovie);

        movieService.saveMovieBySearch(movieId);

        verify(movieRepository, times(1)).save(mockMovie);
        verify(tmdbClientFeign, times(1)).searchMovieById(movieId, "", "en-US");
    }

}

