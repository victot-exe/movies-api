package edu.ada.grupo5.movies_api.client.api;

import edu.ada.grupo5.movies_api.dto.tmdb.*;
import edu.ada.grupo5.movies_api.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "tmdbClient",
        url = "${tmdb.api.url}",
        configuration = TMDBClientFeignConfig.class
)
public interface TMDBClientFeign {

    @GetMapping("/trending/movie/{timeWindow}")
    ResultResponseDTO<TrendingMovieDTO> getTrendingMovies(@PathVariable("timeWindow") String timeWindow,
        @RequestParam("language") String language);

    @GetMapping("/genre/movie/list")
    GenresResponseDTO getGenres(@RequestParam("language") String language);

    @GetMapping("/tv/airing_today")
    ResultResponseDTO<SerieDTO> getAiringToday(@RequestParam("language") String language,
                                               @RequestParam("page") String page);

    @GetMapping("/search/movie")
    ResultResponseDTO<MovieDTO> getMovie(@RequestParam("query") String movieName);

    @GetMapping("/movie/{movie_id}")
    Movie searchMovieById(@PathVariable("movie_id") Integer tmdbId,
                          @RequestParam("append_to_response") String appendToResponse,
                          @RequestParam("language") String language);

    @GetMapping("/search/tv")
    ResultResponseDTO<SerieDTO> getSerie(@RequestParam("query") String serieName);


    @GetMapping("/discover/movie")
    ResultResponseDTO<MovieDTO> getMoviesByGenre(@RequestParam("with_genres") String genre);

    @GetMapping("/tv/{series_id}")
    SerieDTO searchSerieById(@PathVariable("series_id") Integer tmdbId,
                             @RequestParam("append_to_response") String appendToResponse,
                             @RequestParam("language") String language);


}
