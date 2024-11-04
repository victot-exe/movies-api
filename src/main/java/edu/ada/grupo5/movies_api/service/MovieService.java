package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.GenresResponseDTO;
import edu.ada.grupo5.movies_api.dto.TrendingMovieResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final TMDBClientFeign tmdbClientFeign;

    @Autowired
    public MovieService(TMDBClientFeign tmdbClientFeign) {
        this.tmdbClientFeign = tmdbClientFeign;
    }

    public TrendingMovieResponseDTO getTrendingMovies(String timeWindow, String language) {
        TrendingMovieResponseDTO trendingMovieResponseDTO = tmdbClientFeign.getTrendingMovies(timeWindow, language);
        return trendingMovieResponseDTO;
    }

    public GenresResponseDTO getGenres(String language) {
        GenresResponseDTO genresResponseDTO = tmdbClientFeign.getGenres(language);
        return genresResponseDTO;
    }
}