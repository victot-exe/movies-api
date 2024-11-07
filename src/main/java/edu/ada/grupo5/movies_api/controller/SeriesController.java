package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.TVResult;
import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ModelResponseGET;
import edu.ada.grupo5.movies_api.model.Serie;
import edu.ada.grupo5.movies_api.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/tmdb/tv")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/airing_today")
    public ResponseEntity<ResponseDTO<ModelResponseGET<AiringTodayDTO>>> getAiringToday(@RequestParam(defaultValue = "en-US") String language,
                                                                                        @RequestParam(defaultValue = "1") String page) {
        ModelResponseGET<AiringTodayDTO> data = seriesService.getAiringToday(language, page);
        ResponseDTO<ModelResponseGET<AiringTodayDTO>> response = ResponseDTO.<ModelResponseGET<AiringTodayDTO>>builder()
                .message("Airing today fetched sucessfully")
                .timestamp(Instant.now())
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{imdbId}")
    public ResponseEntity<TVResult> getSerieById(@PathVariable("imdbId") String imdbId,
                                               @RequestParam(defaultValue = "en-US") String language) {
        TVResult data = seriesService.findSerieById(imdbId,language);
        return ResponseEntity.ok(data);
    }
}
