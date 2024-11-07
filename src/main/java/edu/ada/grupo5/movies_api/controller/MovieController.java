package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.*;
import edu.ada.grupo5.movies_api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movies")
// TODO: estas rotas foram criadas para testar a integração com TMDB, provavelmente os métodos vão ser alterados
public class MovieController {

    @Autowired
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/tmdb/trending/movies/{timeWindow}")
    public ResponseEntity<ResponseDTO<ModelResponseGET<TrendingMovieDTO>>> getTrendingMovies(
            @PathVariable String timeWindow,
            @RequestParam(defaultValue = "en-US") String language) {

        ModelResponseGET<TrendingMovieDTO> data = movieService.getTrendingMovies(timeWindow, language);

        ResponseDTO<ModelResponseGET<TrendingMovieDTO>> response = ResponseDTO.<ModelResponseGET<TrendingMovieDTO>>builder()
                .message("Trending movies fetched successfully")
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tmdb/genres/movie")
    public ResponseEntity<ResponseDTO<GenresResponseDTO>> getGenres(@RequestParam(defaultValue = "en-US") String language) {
        GenresResponseDTO data = movieService.getGenres(language);

        ResponseDTO<GenresResponseDTO> response = ResponseDTO.<GenresResponseDTO>builder()
                .message("Movie genres fetched successfully")
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/movie")
    public ResponseEntity<ResponseDTO<List<MovieTitleIdDTO>>> getMovie(@RequestParam String movieName,
                                                          @RequestParam(defaultValue = "false") String includeAdult,
                                                          @RequestParam(defaultValue = "en-US") String language,
                                                          @RequestParam(defaultValue = "1") String page) {

        List<MovieTitleIdDTO> data = movieService.getMovie(movieName, includeAdult, language, page);

        ResponseDTO<List<MovieTitleIdDTO>> response = ResponseDTO.<List<MovieTitleIdDTO>>builder()
                .message("Movies fetched successfully")
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }
}
