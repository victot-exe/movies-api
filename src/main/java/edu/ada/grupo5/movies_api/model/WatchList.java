package edu.ada.grupo5.movies_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tb_watch_list")
@NoArgsConstructor
@AllArgsConstructor
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tmdbId;
    private String title;
    @Enumerated(EnumType.STRING)
    private MovieSerieEnum movieSerieEnum;
    @Enumerated(EnumType.STRING)
    private WatchListStatus watchListStatus;

    public WatchList(String tmdbId, String title, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus) {
        this.tmdbId = tmdbId;
        this.title = title;
        this.movieSerieEnum = movieSerieEnum;
        this.watchListStatus = watchListStatus;
    }
}
