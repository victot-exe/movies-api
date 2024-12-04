package edu.ada.grupo5.movies_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ada.grupo5.movies_api.MoviesApiApplication;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;
import edu.ada.grupo5.movies_api.model.Serie;
import edu.ada.grupo5.movies_api.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = MoviesApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SeriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeriesService service;

    @Autowired
    private ObjectMapper objectMapper;

    private SerieDTO serieDTO;
    private Serie serie;


    @BeforeEach
    void setUp() {
        serieDTO = new SerieDTO();
        serieDTO.setId(1);
        serieDTO.setName("Breaking Bad");
        serieDTO.setAdult(false);
        serieDTO.setOriginal_language("en");
        serieDTO.setOriginal_name("Breaking Bad");
        serieDTO.setFirst_air_date("2008-01-20");
        serieDTO.setVote_average(9.5);

        serie = new Serie();
        serie.setId(1L);
        serie.setName("Breaking Bad");
        serie.setTmdbId(1);
        serie.setAdult(false);
        serie.setOriginal_language("en");
        serie.setOriginal_name("Breaking Bad");
        serie.setFirst_air_date("2008-01-20");
        serie.setVote_average(9.5);
    }

    @Test
    @DisplayName("Deve buscar as series no ar hoje e retornar BreakingBad")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAiringToday() throws Exception {
        ResultResponseDTO<SerieDTO> result = new ResultResponseDTO<>();
        result.setResults(Collections.singletonList(serieDTO));
        ResponseDTO<ResultResponseDTO<SerieDTO>> response = ResponseDTO.<ResultResponseDTO<SerieDTO>>builder()
                .data(result)
                .message("Airing today fetched successfully")
                .timestamp(Instant.now())
                .build();

        when(service.getAiringToday("en-US", "1")).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/tmdb/tv/airing_today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(("Airing today fetched successfully")))
                .andExpect(jsonPath("$.data.results[0].name").value("Breaking Bad"));

        verify(service, times(1)).getAiringToday("en-US", "1");
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Deve buscar serie por nome com sucesso")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getSerie() throws Exception {
        List<SerieDTO> list = Collections.singletonList(serieDTO);

        when(service.getSerie(anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/tmdb/tv/search/serie")
                        .param("serieName", anyString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(("Series fetched successfully")))
                .andExpect(jsonPath("$.data[0].name").value("Breaking Bad"));

        verify(service, times(1)).getSerie(anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Deve buscar serie por ID com sucesso")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getSerieById() throws Exception {
        ResponseDTO<SerieDTO> response = ResponseDTO.<SerieDTO>builder()
                .message("Serie Found")
                .timestamp(Instant.now())
                .data(serieDTO)
                .build();

        when(service.searchSerie(anyInt(), anyString(), anyString())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/tmdb/tv/searchById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Serie Found"))
                .andExpect(jsonPath("$.data.name").value("Breaking Bad"));

        verify(service, times(1)).searchSerie(anyInt(), anyString(), anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Deve buscar e retornar todas series do BD")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllSeries() throws Exception {
        List<Serie> list = Collections.singletonList(serie);
        ResponseDTO<List<Serie>> response = ResponseDTO.<List<Serie>>builder()
                .message("Series found")
                .timestamp(Instant.now())
                .data(list).build();

        when(service.getAllSerie()).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/tmdb/tv/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Series found"))
                .andExpect(jsonPath("$.data[0].name").value("Breaking Bad"));

        verify(service, times(1)).getAllSerie();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Atualiza serie com sucesso")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateSerie() throws Exception {
        SerieDTO updatedDTO = new SerieDTO();
        BeanUtils.copyProperties(serieDTO, updatedDTO);
        updatedDTO.setName("Not Breaking Bad");
        ResponseDTO<SerieDTO> response = ResponseDTO.<SerieDTO>builder()
                .message("Serie Updated")
                .timestamp(Instant.now())
                .data(updatedDTO)
                .build();

        when(service.updateSerie(anyLong(), any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/tmdb/tv/1")
                        .contentType("application/json") // Especifica que o conteúdo é JSON
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Serie Updated"))
                .andExpect(jsonPath("$.data.name").value("Not Breaking Bad"));

        verify(service, times(1)).updateSerie(anyLong(), any());
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Deleta serie com sucesso")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteSerie() throws Exception {
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .message("Serie deleted successfully")
                .timestamp(Instant.now())
                .data("")
                .build();

        when(service.deleteSerieById(anyLong())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tmdb/tv/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Serie deleted successfully"))
                .andExpect(jsonPath("$.data").value(""));

        verify(service, times(1)).deleteSerieById(anyLong());
        verifyNoMoreInteractions(service);
    }
}