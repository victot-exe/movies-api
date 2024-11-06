package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
}
