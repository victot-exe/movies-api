package edu.ada.grupo5.movies_api.controller;


import edu.ada.grupo5.movies_api.dto.RecommendedMovieDTO;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.MovieDTO;
import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import edu.ada.grupo5.movies_api.service.WatchListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/watchlist")
@Tag(name = "WatchList")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam String tmdbId,
        @RequestParam String title,
        @RequestParam MovieSerieEnum movieSerieEnum,
        @RequestParam WatchListStatus watchListStatus,
        @RequestParam(defaultValue = "false") boolean favorite)    {
        watchListService.save(tmdbId, title, movieSerieEnum, watchListStatus, favorite);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WatchListDTO> getAll() {
        return this.watchListService.getAll();
    }

    @PutMapping("/{tmdbId}")
    public ResponseEntity<Void> updateWatchListStatus(@PathVariable("tmdbId") String tmdbId,
                                                      @RequestParam("movieSerieEnum") MovieSerieEnum movieSerieEnum,
                                                      @RequestParam("watchListStatus") WatchListStatus watchListStatus) {
        this.watchListService.updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

   @DeleteMapping("/{tmdbId}")
    public ResponseEntity<Void> deleteByTmdbIdAndByMovieSerieEnum(@PathVariable("tmdbId") String tmdbId,
                                                                  @RequestParam("movieSerieEnum") MovieSerieEnum movieSerieEnum) {
        this.watchListService.deleteByTmdbIdAndByMovieSerieEnum(tmdbId, movieSerieEnum);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/favorite")
    public void markAsFavorite(@RequestParam String tmdbId, @RequestParam String title, @RequestParam MovieSerieEnum movieSerieEnum, @RequestParam WatchListStatus watchListStatus) {
        watchListService.save(tmdbId, title, movieSerieEnum, watchListStatus, true);
    }

    @DeleteMapping("/favorite/delete")
    public void deleteFromFavorites(@RequestParam String tmdbId) {

        watchListService.delete(tmdbId);
    }


    @GetMapping("/recommendations")
    public List<RecommendedMovieDTO> getRecommendations() {
        return watchListService.getRecommendedMovies();
    }


    @GetMapping("/genrerecommendations")
    public List<RecommendedMovieDTO> getGenreBasedRecommendations() {
        return watchListService.getGenreBasedRecommendations();
    }
}




