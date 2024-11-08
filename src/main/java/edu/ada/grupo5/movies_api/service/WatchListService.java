package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.model.WatchList;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private SeriesService seriesService;

    public void save(String tmdbId, String title, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus) {
        WatchList watchList = new WatchList(tmdbId, title, movieSerieEnum, watchListStatus);

        seriesService.saveSerieBySearch(Integer.parseInt(tmdbId));

        String userId = getActiveUserId();
        watchList.setUserId(userId);

        watchListRepository.save(watchList);
    }

    public List<WatchListDTO> getAll() {
        String userId = getActiveUserId();
        return watchListRepository.findAllByUserId(userId).stream().map(WatchListDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateWatchListStatus(String tmdbId, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus) {
        String userId = getActiveUserId();
        this.watchListRepository.updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus, userId);
    }

    @Transactional
    public void deleteByTmdbIdAndByMovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum) {
        String userId = getActiveUserId();
        this.watchListRepository.deleteByTmdbIdAndMovieSerieEnum(tmdbId, movieSerieEnum, userId);
    }

    public String getActiveUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

}
