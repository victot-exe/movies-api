package edu.ada.grupo5.movies_api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RecommendedMovieDTO {
    private String title;
    private String tmdbId;
    private long favoriteCount;


    public RecommendedMovieDTO(String title, String tmdbId, long favoriteCount) {
        this.title = title;
        this.tmdbId = tmdbId;
        this.favoriteCount = favoriteCount;
    }

    public RecommendedMovieDTO(String title, String tmdbId) {
        this.title = title;
        this.tmdbId = tmdbId;
    }

}

