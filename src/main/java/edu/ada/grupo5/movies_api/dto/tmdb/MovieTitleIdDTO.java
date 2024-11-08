package edu.ada.grupo5.movies_api.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@Schema
public class MovieTitleIdDTO {

    private String title;
    private Integer tmdbId;

    public MovieTitleIdDTO(String title, Integer tmdbId) {
        this.title = title;
        this.tmdbId = tmdbId;
    }
}
