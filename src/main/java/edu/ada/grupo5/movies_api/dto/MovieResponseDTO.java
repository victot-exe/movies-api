package edu.ada.grupo5.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieResponseDTO {
    private int page;
    private List<MovieDTO> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
}
