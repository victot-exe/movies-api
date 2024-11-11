package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchList;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

    List<WatchList> findAllByUserId(Integer userId);

    @Modifying
    @Query("DELETE FROM WatchList w WHERE w.tmdbId = ?1 AND w.movieSerieEnum = ?2 AND w.userId = ?3")
    void deleteByTmdbIdAndMovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum, Integer userId);

    @Modifying
    @Query("UPDATE WatchList w SET w.watchListStatus = ?3 WHERE w.tmdbId = ?1 AND w.movieSerieEnum = ?2 AND w.userId = ?4")
    void updateWatchListStatus(String tmdbId, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus, Integer userId);

    @Query("SELECT w FROM WatchList w WHERE w.favorite = true")
    List<WatchList> findAllFavorites();

    WatchList findByTmdbIdAndUserId(String tmdbId, Integer userId);
}
