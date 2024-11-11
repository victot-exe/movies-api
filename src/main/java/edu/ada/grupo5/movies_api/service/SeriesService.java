package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.SerieRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;
import edu.ada.grupo5.movies_api.model.Serie;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
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

    public ResponseDTO<ResultResponseDTO<SerieDTO>> getAiringToday(String language, String page) {
        ResultResponseDTO<SerieDTO> data = tmdbClientFeign.getAiringToday(language, page);
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

    public ResponseDTO<SerieDTO> saveSerie(SerieDTO dto) {
        Serie serie = convertFromDTO(dto);
        serieRepository.save(serie);
        return ResponseDTO.<SerieDTO>builder().message("Serie saved successfully")
                .timestamp(Instant.now())
                .data(dto)
                .build();
    }

    @Transactional
    public ResponseDTO<SerieDTO> updateSerie(Long id, SerieDTO dto) {
        if (!serieRepository.existsByTmdbId(dto.getId())) {
            return saveSerie(dto);
        } else {
            Serie serie = serieRepository.getReferenceById(id);
            updateData(serie, dto);
            serieRepository.save(serie);
            return ResponseDTO.<SerieDTO>builder()
                    .message("Serie updated successfully")
                    .timestamp(Instant.now())
                    .data(dto)
                    .build();
        }
    }


    public ResponseDTO<List<Serie>> getAllSerie() {
        List<Serie> series = serieRepository.findAll();
        return ResponseDTO.<List<Serie>>builder()
                .timestamp(Instant.now())
                .message("Series found")
                .data(series)
                .build();
    }


    @Transactional
    public ResponseDTO<String> deleteSerieById(Long id) {
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
            return ResponseDTO.<String>builder()
                    .timestamp(Instant.now())
                    .message("Serie deleted successfully")
                    .data("")
                    .build();
        } else {
            throw new ResourceNotFoundException("Serie not found");
        }
    }


    private Serie convertFromDTO(SerieDTO dto) {
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

    private void updateData(Serie serieToUpdate, SerieDTO dto) {
        serieToUpdate.setName(dto.getName());
        serieToUpdate.setTmdbId(dto.getId());
        serieToUpdate.setAdult(dto.isAdult());
        serieToUpdate.setOriginal_language(dto.getOriginal_language());
        serieToUpdate.setOriginal_name(dto.getOriginal_name());
        serieToUpdate.setFirst_air_date(dto.getFirst_air_date());
        serieToUpdate.setVote_average(dto.getVote_average());
    }
}
