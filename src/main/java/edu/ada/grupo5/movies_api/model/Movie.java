package edu.ada.grupo5.movies_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends BaseModel {
    @Column(nullable = false)
    private String title;
    private String director;
    private String genre;
    @JsonProperty("year")
    private Integer releaseYear;
    private String country;
    private String language;
    private Integer duration;
    @Column(unique = true)
    private Integer tmdbId;
    @Column(unique = true)
    private String imdbId;
}