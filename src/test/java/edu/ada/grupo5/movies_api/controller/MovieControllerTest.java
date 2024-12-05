package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.MoviesApiApplication;
import edu.ada.grupo5.movies_api.dto.tmdb.*;
import edu.ada.grupo5.movies_api.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MoviesApiApplication.class)
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    @DisplayName("Should fetch trending movies and return successfully")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void should_return_trending_movies() throws Exception {
        String timeWindow = "day";
        String language = "en-US";

        TrendingMovieDTO movie = TrendingMovieDTO.builder()
                .id(170)
                .title("28 Days Later")
                .build();

        ResultResponseDTO<TrendingMovieDTO> trendingMovies = ResultResponseDTO.<TrendingMovieDTO>builder()
                .results(List.of(movie))
                .build();

        when(movieService.getTrendingMovies(timeWindow, language)).thenReturn(trendingMovies);

        mockMvc.perform(get("/movies/tmdb/trending/movies/{timeWindow}", timeWindow)
                        .param("language", language))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Trending movies fetched successfully"))
                .andExpect(jsonPath("$.data.results[0].id").value(170))
                .andExpect(jsonPath("$.data.results[0].title").value("28 Days Later"));

        verify(movieService, times(1)).getTrendingMovies(timeWindow, language);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void should_return_movie_genres() throws Exception {
        GenresResponseDTO genresResponse = GenresResponseDTO.builder()
                .genres(List.of(new GenreDTO(1, "Action"), new GenreDTO(2, "Drama")))
                .build();

        when(movieService.getGenres("en-US")).thenReturn(genresResponse);

        mockMvc.perform(get("/movies/tmdb/genres/movie")
                        .param("language", "en-US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie genres fetched successfully"))
                .andExpect(jsonPath("$.data.genres[0].id").value(1))
                .andExpect(jsonPath("$.data.genres[0].name").value("Action"));

        verify(movieService, times(1)).getGenres("en-US");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void should_return_movies_by_name() throws Exception {
        String movieName = "28 Days Later";

        MovieTitleIdDTO movie = new MovieTitleIdDTO(movieName, 170);
        List<MovieTitleIdDTO> movies = List.of(movie);

        when(movieService.getMovie(movieName)).thenReturn(movies);

        mockMvc.perform(get("/movies/search/movie")
                        .param("movieName", movieName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movies fetched successfully"))
                .andExpect(jsonPath("$.data[0].tmdbId").value(170))
                .andExpect(jsonPath("$.data[0].title").value(movieName));

        verify(movieService, times(1)).getMovie(movieName);
    }
}

