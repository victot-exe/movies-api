package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.MovieResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final TMDBClientFeign tmdbClientFeign;

    @Autowired
    public MovieService(TMDBClientFeign tmdbClientFeign) {
        this.tmdbClientFeign = tmdbClientFeign;
    }

    public MovieResponseDTO getTrendingMovies(String timeWindow, String language) {
        return tmdbClientFeign.getTrendingMovies(timeWindow, language);
    }
}