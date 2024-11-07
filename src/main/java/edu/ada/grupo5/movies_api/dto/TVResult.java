package edu.ada.grupo5.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ada.grupo5.movies_api.dto.tmdb.SerieDTO;

import java.util.List;

public class TVResult {

    @JsonProperty("tv_results")
    List<SerieDTO> tv_results;
}
