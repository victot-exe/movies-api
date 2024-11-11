package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;
import edu.ada.grupo5.movies_api.model.Serie;
import edu.ada.grupo5.movies_api.service.SeriesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

//TODO: revisar
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/tmdb/tv")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/airing_today")
    public ResponseEntity<ResponseDTO<ResultResponseDTO<SerieDTO>>> getAiringToday(@RequestParam(defaultValue = "en-US") String language,
                                                                                   @RequestParam(defaultValue = "1") String page) {
        ResponseDTO<ResultResponseDTO<SerieDTO>> response = seriesService.getAiringToday(language, page);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search/serie")
    public ResponseEntity<ResponseDTO<List<SerieDTO>>> getSerie(@RequestParam String serieName) {

        List<SerieDTO> data = seriesService.getSerie(serieName);

        ResponseDTO<List<SerieDTO>> response = ResponseDTO.<List<SerieDTO>>builder()
                .message("Series fetched successfully")
                .timestamp(Instant.now())
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchById/{tmdbId}")
    public ResponseEntity<ResponseDTO<SerieDTO>> getSerieById(@PathVariable("tmdbId") Integer tmdbId,
                                                              @RequestParam(defaultValue = "") String appendToResponse,
                                                              @RequestParam(defaultValue = "en-US") String language) {
        ResponseDTO<SerieDTO> response = seriesService.searchSerie(tmdbId, appendToResponse, language);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<Serie>>> getAllSeries() {
        ResponseDTO<List<Serie>> series = seriesService.getAllSerie();
        return ResponseEntity.ok(series);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SerieDTO>> updateSerie(@PathVariable("id") Long id, @RequestBody SerieDTO serie) {
        ResponseDTO<SerieDTO> response = seriesService.updateSerie(id, serie);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteSerie(@PathVariable("id") Long id) {
        ResponseDTO<String> response = seriesService.deleteSerieById(id);
        return ResponseEntity.ok(response);
    }
}
