package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SerieRepository extends JpaRepository<Serie, Long> {

    boolean existsByTmdbId(int tmdbId);

    void deleteByTmdbId(int tmdbId);

    Serie getReferenceByTmdbId(int id);
}
