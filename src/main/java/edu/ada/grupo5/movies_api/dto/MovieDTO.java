package edu.ada.grupo5.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private int id;
    private String title;
    private String overview;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("media_type")
    private String mediaType;
    private boolean adult;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    private double popularity;
    @JsonProperty("release_date")
    private String releaseDate;
    private boolean video;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
}
