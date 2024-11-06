package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.tmdb.GenresResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ModelResponseGET;
import edu.ada.grupo5.movies_api.dto.tmdb.TrendingMovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final TMDBClientFeign tmdbClientFeign;

    @Autowired
    public MovieService(TMDBClientFeign tmdbClientFeign) {
        this.tmdbClientFeign = tmdbClientFeign;
    }

    public ModelResponseGET<TrendingMovieDTO> getTrendingMovies(String timeWindow, String language) {
        return tmdbClientFeign.getTrendingMovies(timeWindow, language);
    }

    public GenresResponseDTO getGenres(String language) {
        return tmdbClientFeign.getGenres(language);
    }
}