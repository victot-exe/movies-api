package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.GenresResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.TrendingMovieResponseDTO;
import edu.ada.grupo5.movies_api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/tmdb/trending/movies/{timeWindow}")
    public ResponseEntity<ResponseDTO<TrendingMovieResponseDTO>> getTrendingMovies(
            @PathVariable String timeWindow,
            @RequestParam(defaultValue = "en-US") String language) {

        TrendingMovieResponseDTO data = movieService.getTrendingMovies(timeWindow, language);

        ResponseDTO<TrendingMovieResponseDTO> response = ResponseDTO.<TrendingMovieResponseDTO>builder()
                .message("Trending movies fetched successfully")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tmdb/genres/movie")
    public ResponseEntity<ResponseDTO<GenresResponseDTO>> getGenres(@RequestParam(defaultValue = "en-US") String language) {
        GenresResponseDTO data = movieService.getGenres(language);

        ResponseDTO<GenresResponseDTO> response = ResponseDTO.<GenresResponseDTO>builder()
                .message("Movie genres fetched successfully")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }
}
