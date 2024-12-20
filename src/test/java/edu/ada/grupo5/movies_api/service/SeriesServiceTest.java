package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.SerieRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;
import edu.ada.grupo5.movies_api.model.Serie;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SeriesServiceTest {

    @InjectMocks
    private SeriesService service;

    @Mock
    private TMDBClientFeign feign;

    @Mock
    private SerieRepository repository;

    @Mock
    private ResponseDTO<SerieDTO> response;

    @Mock
    private SerieDTO serie;

    @Mock
    private Serie entity;


    @BeforeEach
    void setUp() {
        serie = new SerieDTO();
        serie.setAdult(false);
        serie.setBackdrop_path("/9YteO4VWteiPmEbWYJRAeBTQZPD.jpg");
        serie.setFirst_air_date("2005-09-19");
        serie.setId(1100);
        serie.setName("How I Met Your Mother");
        serie.setOriginal_language("en");
        serie.setOriginal_name("How I Met Your Mother");
        serie.setOverview("A father recounts to his children - through a series of flashbacks - the journey he and his four best friends took leading up to him meeting their mother.");
        serie.setPopularity(619.575);
        serie.setPoster_path("/b34jPzmB0wZy7EjUZoleXOl2RRI.jpg");
        serie.setVote_average(8.159);
        serie.setVote_count(4955);
        serie.setOrigin_country(List.of("US"));
        serie.setGenre_ids(List.of(35));

        entity = new Serie();
        entity.setAdult(false);
        entity.setFirst_air_date("2005-09-19");
        entity.setTmdbId(1100);
        entity.setName("How I Met Your Mother");
        entity.setOriginal_language("en");
        entity.setOriginal_name("How I Met Your Mother");
        entity.setVote_average(8.159);
    }

    @Test
    @DisplayName("Deve retornar airing today com sucesso")
    void getAiringToday() {
        ResultResponseDTO<SerieDTO> responseDTO = new ResultResponseDTO<>();
        responseDTO.setPage(1);
        responseDTO.setTotalPages(1);
        responseDTO.setTotalResults(1);
        responseDTO.setResults(Collections.singletonList(serie));
        when(feign.getAiringToday("en-US", "")).thenReturn(responseDTO);

        ResultResponseDTO<SerieDTO> result = service.getAiringToday("en-US", "").getData();

        assertThat(result).isEqualTo(responseDTO);
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getResults().size()).isEqualTo(1);
        assertThat(result.getTotalResults()).isEqualTo(1);
        assertThat(service.getAiringToday("en-US", "").getMessage()).isEqualTo("Airing today fetched successfully");

        verify(feign, times(2)).getAiringToday("en-US", "");
    }

    @Test
    @DisplayName("Deve buscar a serie com sucesso")
    void searchSerie() {
        response = ResponseDTO.<SerieDTO>builder().message("Serie found")
                .timestamp(Instant.now())
                .data(serie)
                .build();
        int serieId = 1100;
        when(feign.searchSerieById(serieId, "", "en-US")).thenReturn(serie);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo(serie);
        assertThat(response.getMessage()).isEqualTo("Serie found");
        assertEquals(response.getData(), service.searchSerie(serieId, "", "en-US").getData());

        verify(feign, times(1)).searchSerieById(serieId, "", "en-US");
    }

    @Test
    @DisplayName("Deve salvar serie com sucesso")
    void saveSerie() {
        when(repository.existsByTmdbId(any(Integer.class))).thenReturn(false);
        when(repository.save(any(Serie.class))).thenReturn(entity);

        response = service.saveSerie(serie);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo(serie);
        assertThat(response.getMessage()).isEqualTo("Serie saved successfully");

        verify(repository, times(1)).existsByTmdbId(serie.getId());
        verify(repository, times(1)).save(any(Serie.class));
    }


    @Test
    @DisplayName("Deve buscar series com sucesso")
    void getAllSerie() {
        when(repository.findAll()).thenReturn(Collections.singletonList(entity));

        ResponseDTO<List<Serie>> responseDTO = service.getAllSerie();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMessage()).isEqualTo("Series found");
        assertThat(responseDTO.getData()).isEqualTo(Collections.singletonList(entity));

        verify(repository, times(1)).findAll();
    }


    @Test
    @DisplayName("Deve retornar uma serie com sucesso")
    void getSerie() {
        ResultResponseDTO<SerieDTO> serieDTOResultResponseDTO = new ResultResponseDTO<>();
        serieDTOResultResponseDTO.setPage(1);
        serieDTOResultResponseDTO.setTotalPages(1);
        serieDTOResultResponseDTO.setTotalResults(1);
        serieDTOResultResponseDTO.setResults(Collections.singletonList(serie));

        when(feign.getSerie(anyString())).thenReturn(serieDTOResultResponseDTO);

        List<SerieDTO> result = service.getSerie("");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(Collections.singletonList(serie));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getId()).isEqualTo(serie.getId());

        verify(feign, times(1)).getSerie(anyString());

    }

    @Test
    @DisplayName("Deve salvar assim que buscada")
    void saveSerieBySearch() {

        when(feign.searchSerieById(anyInt(), anyString(), anyString())).thenReturn(serie);
        when(repository.save(any(Serie.class))).thenReturn(entity);

        service.saveSerieBySearch(anyInt());

        verify(repository, times(1)).save(any(Serie.class));
        verify(repository, times(2)).existsByTmdbId(any(Integer.class));
        verify(feign, times(1)).searchSerieById(anyInt(), anyString(), anyString());


    }

    @Test
    @DisplayName("Atualiza serieDTO e serie com sucesso")
    void updateSerie() {
        SerieDTO updatedSerie = new SerieDTO();
        Serie updatedEntity = new Serie();
        BeanUtils.copyProperties(serie, updatedSerie);
        BeanUtils.copyProperties(entity, updatedEntity);

        Serie serieToUpdate = entity;

        when(repository.existsByTmdbId(anyInt())).thenReturn(true);
        when(repository.getReferenceById(anyLong())).thenReturn(serieToUpdate);
        when(repository.save(any(Serie.class))).thenReturn(updatedEntity);

        updatedSerie.setId(10);
        updatedSerie.setName("Updated Test");

        response = service.updateSerie(1L, updatedSerie);

        verify(repository, times(1)).existsByTmdbId(updatedSerie.getId());
        verify(repository, times(1)).getReferenceById(anyLong());
        verify(repository, times(1)).save(serieToUpdate);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo(updatedSerie);
        assertThat(response.getMessage()).isEqualTo("Serie updated successfully");

        verify(repository, times(1)).existsByTmdbId(updatedSerie.getId());
    }

    @Test
    @DisplayName("Deve deletar com sucesso")
    void deleteSerieById() {
        when(repository.existsById(any(Long.class))).thenReturn(true);
        ResponseDTO<String> responseDTO = service.deleteSerieById(anyLong());

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMessage()).isEqualTo("Serie deleted successfully");
        assertThat(responseDTO.getData()).isEqualTo("");

        verify(repository, times(1)).existsById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve lancar uma excessao ao deletar invalido")
    void DeveLancarExcessaoAoDeletar() {
        when(repository.existsById(any(Long.class))).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteSerieById(anyLong());
        });
        assertThat(exception.getMessage()).isEqualTo("Serie not found");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar série já existente")
    void saveSerieQueJaExiste() {
        when(repository.existsByTmdbId(serie.getId())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.saveSerie(serie);
        });

        assertThat(exception).isInstanceOf(RuntimeException.class);
        verify(repository, never()).save(any(Serie.class));
    }

}