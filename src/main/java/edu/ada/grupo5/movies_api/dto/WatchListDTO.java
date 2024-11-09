package edu.ada.grupo5.movies_api.dto;

import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchList;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class WatchListDTO {

    private String title;
    private String tmdbId;
    private MovieSerieEnum movieSerieEnum;
    private WatchListStatus watchListStatus;

    public WatchListDTO(WatchList watchList) {
        this.title = watchList.getTitle();
        this.tmdbId = watchList.getTmdbId();
        this.movieSerieEnum = watchList.getMovieSerieEnum();
        this.watchListStatus = watchList.getWatchListStatus();
    }
}
