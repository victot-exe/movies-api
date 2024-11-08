package edu.ada.grupo5.movies_api.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MovieDTO {

    private String title;
    private String director;
    private String genre;
    private Integer releaseYear;
    private String country;
    private String language;
    private Integer duration;
    private Integer Id;
    private String imdbId;


}
