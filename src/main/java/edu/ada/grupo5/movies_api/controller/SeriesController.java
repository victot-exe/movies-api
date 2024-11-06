package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ModelResponseGET;
import edu.ada.grupo5.movies_api.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO: revisar

@RestController
@RequestMapping("/tmdb/tv")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/airing_today")
    public ResponseEntity<ResponseDTO<ModelResponseGET<AiringTodayDTO>>> getAiringToday(@RequestParam(defaultValue = "en-US") String language,
                                                                                        @RequestParam(defaultValue = "1") String page) {
        ModelResponseGET<AiringTodayDTO> data = seriesService.getAiringToday(language, page);
        ResponseDTO<ModelResponseGET<AiringTodayDTO>> response = seriesService.getAiringTodayResponse(data);
        return ResponseEntity.ok(response);
    }
}
