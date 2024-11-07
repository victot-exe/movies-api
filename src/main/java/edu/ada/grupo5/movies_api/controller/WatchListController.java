package edu.ada.grupo5.movies_api.controller;


import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import edu.ada.grupo5.movies_api.service.WatchListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
@Tag(name = "WatchList")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam String tmdbID,
                                       @RequestParam String title,
                                       @RequestParam MovieSerieEnum movieSerieEnum,
                                       @RequestParam WatchListStatus watchListStatus) {
        watchListService.save(tmdbID, title, movieSerieEnum, watchListStatus);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WatchListDTO> getAll(){
        return this.watchListService.getAll();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByTmdbIdAndByMovieSerieEnum(@PathVariable("tmdbId") String tmdbId,
                                           @RequestParam("movieSerieEnum") MovieSerieEnum movieSerieEnum){
        this.watchListService.deleteByTmdbIdAndByMovieSerieEnum(tmdbId, movieSerieEnum);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
