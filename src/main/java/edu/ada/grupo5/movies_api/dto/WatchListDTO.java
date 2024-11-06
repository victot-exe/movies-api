package edu.ada.grupo5.movies_api.dto;

import edu.ada.grupo5.movies_api.model.Movie;
import edu.ada.grupo5.movies_api.model.Serie;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class WatchListDTO {

    private Movie movie;
    private Serie serie;
    @NotBlank
    private WatchListStatus watchListStatus;

}
