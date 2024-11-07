package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    @Query("DELETE FROM WatchList w WHERE w.tmdbId = ?1 AND w.movieSerieEnum = ?2")
    void deleteBytmdbIdAndmovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum);
}
