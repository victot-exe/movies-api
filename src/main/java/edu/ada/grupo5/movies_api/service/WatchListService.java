package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    public void save(WatchListDTO watchListDTO){

        WatchList watchList = new WatchList();
        watchList.setMovie(watchListDTO.getMovie());
        watchList.setSerie(watchListDTO.getSerie());
        watchList.setWatchListStatus(watchListDTO.getWatchListStatus());

        watchListRepository.save(watchList);
    }

    public List<WatchListDTO> getAll() {
        return watchListRepository.findAll().stream().map(WatchListDTO::new).collect(Collectors.toList());
    }
}
