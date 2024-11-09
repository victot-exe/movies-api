package edu.ada.grupo5.movies_api.dto.tmdb;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrendingMovieDTO {
    private int id;
    private String title;
    private String overview;
    private String backdropPath;
    private String originalTitle;
    private String posterPath;
    private String mediaType;
    private boolean adult;
    private String originalLanguage;
    private List<Integer> genreIds;
    private double popularity;
    private String releaseDate;
    private boolean video;
    private double voteAverage;
    private int voteCount;
}
