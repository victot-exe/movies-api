package edu.ada.grupo5.movies_api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenresResponseDTO {
    private List<GenreDTO> genres;
}
