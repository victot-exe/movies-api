package edu.ada.grupo5.movies_api.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelResponseGET<T> {
    private int page;
    private List<T> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
}
