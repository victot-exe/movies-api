package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

//TODO: implementar restante que serao utilizados nas rotas
@Service
public class SeriesService {

    @Autowired
    private TMDBClientFeign tMDBClientFeign;

    public ResultResponseDTO<AiringTodayDTO> getAiringToday(String language, String page) {
        return tMDBClientFeign.getAiringToday(language, page);
    }

    public ResponseDTO<ResultResponseDTO<AiringTodayDTO>> getAiringTodayResponse(ResultResponseDTO<AiringTodayDTO> data) {
        return ResponseDTO.<ResultResponseDTO<AiringTodayDTO>>builder()
                .message("Airing today fetched sucessfully")
                .timestamp(Instant.now())
                .data(data)
                .build();
    }


}
