package edu.ada.grupo5.movies_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
// TODO: implementar/revisar
public class Movie extends BaseModel {
    @Column(nullable = false)
    private String title;
    private String director;
    private String genre;
    @Column(name = "release_year")
    @JsonProperty("year")
    private Integer releaseYear;
    //private Integer year;
    private String country;
    private String language;
    private Integer duration;
    @Column(unique = true)
    private Integer tmdbId;
    @Column(unique = true)
    private String imdbId;
}
