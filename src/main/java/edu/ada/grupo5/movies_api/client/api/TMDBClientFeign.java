package edu.ada.grupo5.movies_api.client.api;

import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.GenresResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.TrendingMovieDTO;
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
    ResultResponseDTO<AiringTodayDTO> getAiringToday(@RequestParam("language") String language,
                                                     @RequestParam("page") String page);
}
