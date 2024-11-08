package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieNameIdDTO;
import edu.ada.grupo5.movies_api.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

//TODO: revisar

@RestController
@RequestMapping("/tmdb/tv")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/airing_today")
    public ResponseEntity<ResponseDTO<ResultResponseDTO<AiringTodayDTO>>> getAiringToday(@RequestParam(defaultValue = "en-US") String language,
                                                                                         @RequestParam(defaultValue = "1") String page) {
        ResultResponseDTO<AiringTodayDTO> data = seriesService.getAiringToday(language, page);
        ResponseDTO<ResultResponseDTO<AiringTodayDTO>> response = seriesService.getAiringTodayResponse(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/serie")
    public ResponseEntity<ResponseDTO<List<SerieNameIdDTO>>> getSerie(@RequestParam String serieName,
                                                                      @RequestParam(defaultValue = "false") String includeAdult,
                                                                      @RequestParam(defaultValue = "en-US") String language,
                                                                      @RequestParam(defaultValue = "1") String page) {

        List<SerieNameIdDTO> data = seriesService.getSerie(serieName, includeAdult, language, page);

        ResponseDTO<List<SerieNameIdDTO>> response = ResponseDTO.<List<SerieNameIdDTO>>builder()
                .message("Series fetched successfully")
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }
}
