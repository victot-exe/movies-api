package edu.ada.grupo5.movies_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
    private String userId;

    public WatchList(String tmdbId, String title, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus) {
        this.tmdbId = tmdbId;
        this.title = title;
        this.movieSerieEnum = movieSerieEnum;
        this.watchListStatus = watchListStatus;
    }

}
