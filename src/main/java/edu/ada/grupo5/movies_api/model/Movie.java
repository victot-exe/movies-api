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
    public boolean adult;
    @Column(nullable = false)
    private String title;
    @JsonProperty("id")
    public int tmdbId;
    @Column(unique = true)
    private String imdb_id;
    public String original_language;
    public String original_title;
    public String release_date;
    public String status;
    public double vote_average;

//    private Integer duration;
//    private String language;
//    private String director;
//    private String genre;
//    @JsonProperty("year")
//    private Integer releaseYear;
}