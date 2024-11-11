package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByTmdbId(int tmdbId);

    void deleteByTmdbId(int tmdbId);

    Movie getReferenceByTmdbId(int id);
}
