package edu.ada.grupo5.movies_api.dto.tmdb;

import lombok.Data;

@Data
public class SerieNameIdDTO {

    private String name;
    private Integer id;

    public SerieNameIdDTO(String title, Integer tmdbId) {
        this.name = title;
        this.id = tmdbId;
    }
}
