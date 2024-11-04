package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.MovieResponseDTO;
import edu.ada.grupo5.movies_api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/tmdb/trending/movies/{timeWindow}")
    public MovieResponseDTO getTrendingMovies(@PathVariable String timeWindow,
                                              @RequestParam(defaultValue = "en-US") String language) {
        return movieService.getTrendingMovies(timeWindow, language);
    }
}
