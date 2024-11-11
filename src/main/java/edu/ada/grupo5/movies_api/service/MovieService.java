package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.MovieRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.tmdb.*;
import edu.ada.grupo5.movies_api.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private final TMDBClientFeign tmdbClientFeign;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(TMDBClientFeign tmdbClientFeign) {
        this.tmdbClientFeign = tmdbClientFeign;
    }

    public ResultResponseDTO<TrendingMovieDTO> getTrendingMovies(String timeWindow, String language) {
        return tmdbClientFeign.getTrendingMovies(timeWindow, language);
    }

    public GenresResponseDTO getGenres(String language) {
        return tmdbClientFeign.getGenres(language);
    }

    public List<MovieTitleIdDTO> getMovie(String movieName) {
        ResultResponseDTO<MovieDTO> movieDTOResultResponseDTO = tmdbClientFeign.getMovie(movieName);
        return movieDTOResultResponseDTO.getResults().stream().map(movie -> new MovieTitleIdDTO(movie.getTitle(), movie.getId())).collect(Collectors.toList());
    }

    public void saveMovieBySearch(Integer id) {
        if (!movieRepository.existsByTmdbId(id)) {
            Movie movie = tmdbClientFeign.searchMovieById(id, "", "en-US");
            movieRepository.save(movie);
        }
    }

}