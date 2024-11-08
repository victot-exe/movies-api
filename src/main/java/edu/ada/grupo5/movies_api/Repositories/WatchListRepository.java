package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchList;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    @Modifying
    @Query("DELETE FROM WatchList w WHERE w.tmdbId = ?1 AND w.movieSerieEnum = ?2")
    void deleteByTmdbIdAndMovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum);

    @Modifying
    @Query("UPDATE WatchList w SET w.watchListStatus = ?3 WHERE w.tmdbId = ?1 AND w.movieSerieEnum = ?2")
    void updateWatchListStatus(String tmdbId, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus);
}
