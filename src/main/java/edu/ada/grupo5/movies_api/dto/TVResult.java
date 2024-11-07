package edu.ada.grupo5.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ada.grupo5.movies_api.model.Serie;

import java.util.List;

public class TVResult {

    @JsonProperty("tv_results")
    List<Serie> tv_results;
}
