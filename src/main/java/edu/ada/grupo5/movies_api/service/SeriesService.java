package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ModelResponseGET;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieNameIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

//TODO: implementar restante que serao utilizados nas rotas
@Service
public class SeriesService {

    @Autowired
    private final TMDBClientFeign tmdbClientFeign;

    @Autowired
    public SeriesService(TMDBClientFeign tmdbClientFeign) {
        this.tmdbClientFeign = tmdbClientFeign;
    }

    public ModelResponseGET<AiringTodayDTO> getAiringToday(String language, String page) {
        return tmdbClientFeign.getAiringToday(language, page);
    }

    public ResponseDTO<ModelResponseGET<AiringTodayDTO>> getAiringTodayResponse(ModelResponseGET<AiringTodayDTO> data) {
        return ResponseDTO.<ModelResponseGET<AiringTodayDTO>>builder()
                .message("Airing today fetched sucessfully")
                .timestamp(Instant.now())
                .data(data)
                .build();
    }


    public List<SerieNameIdDTO> getSerie(String serieName, String includeAdult, String language, String page) {

        ModelResponseGET<SerieDTO> serieDTOModelResponseGET = tmdbClientFeign.getSerie(serieName, includeAdult, language, page);
        return serieDTOModelResponseGET.getResults().stream().map(serie -> new SerieNameIdDTO(serie.getName(), serie.getId())).collect(Collectors.toList());

    }
}
