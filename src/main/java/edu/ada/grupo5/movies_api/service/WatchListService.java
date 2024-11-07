package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.WatchList;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    public void save(String tmdbId, String title, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus){
        WatchList watchList = new WatchList(tmdbId, title, movieSerieEnum, watchListStatus);
        watchListRepository.save(watchList);
    }

    public List<WatchListDTO> getAll() {
        return watchListRepository.findAll().stream().map(WatchListDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByTmdbIdAndByMovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum){
        this.watchListRepository.deleteBytmdbIdAndmovieSerieEnum(tmdbId, movieSerieEnum);
    }
}
