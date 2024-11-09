package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.SerieRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;
import edu.ada.grupo5.movies_api.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

//TODO: implementar restante que serao utilizados nas rotas e excecoes
@Service
public class SeriesService {

    @Autowired
    private final TMDBClientFeign tmdbClientFeign;
    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    public SeriesService(TMDBClientFeign tmdbClientFeign) {
        this.tmdbClientFeign = tmdbClientFeign;
    }

    public ResultResponseDTO<SerieDTO> getAiringToday(String language, String page) {
        return tmdbClientFeign.getAiringToday(language, page);
    }

    public ResponseDTO<ResultResponseDTO<SerieDTO>> getAiringTodayResponse(ResultResponseDTO<SerieDTO> data) {
        return ResponseDTO.<ResultResponseDTO<SerieDTO>>builder()
                .message("Airing today fetched sucessfully")
                .timestamp(Instant.now())
                .data(data)
                .build();
    }


    public ResponseDTO<SerieDTO> searchSerie(Integer imdbId, String appendToResponse, String language) {
        SerieDTO dto = tmdbClientFeign.searchSerieById(imdbId, appendToResponse, language);
        return ResponseDTO.<SerieDTO>builder().message("Serie found")
                .timestamp(Instant.now())
                .data(dto)
                .build();
    }

    public ResponseDTO<SerieDTO> save(SerieDTO dto) {
        Serie serie = convertFromDTO(dto);
        serieRepository.save(serie);
        return ResponseDTO.<SerieDTO>builder().message("Serie saved successfully")
                .timestamp(Instant.now())
                .data(dto)
                .build();
    }


    public Serie convertFromDTO(SerieDTO dto) {
        if (serieRepository.existsByTmdbId(dto.getId())) throw new RuntimeException();
        return new Serie(dto.getName(), dto.getId(),
                dto.isAdult(), dto.getOriginal_language(),
                dto.getOriginal_name(), dto.getFirst_air_date(), dto.getVote_average());
    }

    public List<SerieDTO> getSerie(String serieName) {

        ResultResponseDTO<SerieDTO> serieDTOResultResponseDTO = tmdbClientFeign.getSerie(serieName);
        return serieDTOResultResponseDTO.getResults();
    }

    public void saveSerieBySearch(Integer id) {
        if (!serieRepository.existsByTmdbId(id)) {
            SerieDTO dto = tmdbClientFeign.searchSerieById(id, "", "en-US");
            serieRepository.save(convertFromDTO(dto));
        }
    }
}