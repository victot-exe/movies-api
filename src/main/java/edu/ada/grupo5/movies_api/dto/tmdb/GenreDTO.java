package edu.ada.grupo5.movies_api.dto.tmdb;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {
    private int id;
    private String name;
}
