package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.GenresResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.MovieTitleIdDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.TrendingMovieDTO;
import edu.ada.grupo5.movies_api.service.MovieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/tmdb/trending/movies/{timeWindow}")
    public ResponseEntity<ResponseDTO<ResultResponseDTO<TrendingMovieDTO>>> getTrendingMovies(
            @PathVariable String timeWindow,
            @RequestParam(defaultValue = "en-US") String language) {

        ResultResponseDTO<TrendingMovieDTO> data = movieService.getTrendingMovies(timeWindow, language);

        ResponseDTO<ResultResponseDTO<TrendingMovieDTO>> response = ResponseDTO.<ResultResponseDTO<TrendingMovieDTO>>builder()
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
    public ResponseEntity<ResponseDTO<List<MovieTitleIdDTO>>> getMovie(@RequestParam String movieName) {

        List<MovieTitleIdDTO> data = movieService.getMovie(movieName);

        ResponseDTO<List<MovieTitleIdDTO>> response = ResponseDTO.<List<MovieTitleIdDTO>>builder()
                .message("Movies fetched successfully")
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }


}
