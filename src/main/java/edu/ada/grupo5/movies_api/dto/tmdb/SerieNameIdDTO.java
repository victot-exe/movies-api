package edu.ada.grupo5.movies_api.dto.tmdb;

import lombok.Data;

@Data
public class SerieNameIdDTO {

    private String name;
    private String id;

    public SerieNameIdDTO(String title, String tmdbId) {
        this.name = title;
        this.id = tmdbId;
    }
}
