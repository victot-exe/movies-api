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
    @Autowired
    private MovieService movieService;

    public void save(String tmdbId, String title, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus) {
        WatchList watchList = new WatchList(tmdbId, title, movieSerieEnum, watchListStatus);

        if (movieSerieEnum == MovieSerieEnum.SERIE) seriesService.saveSerieBySearch(Integer.parseInt(tmdbId));
        if (movieSerieEnum == MovieSerieEnum.MOVIE) movieService.saveMovieBySearch(Integer.parseInt(tmdbId));
        Integer userId = getActiveUserId();
        watchList.setUserId(userId);

        watchListRepository.save(watchList);
    }

    public List<WatchListDTO> getAll() {
        Integer userId = getActiveUserId();
        return watchListRepository.findAllByUserId(userId).stream().map(WatchListDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateWatchListStatus(String tmdbId, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus) {
        Integer userId = getActiveUserId();
        this.watchListRepository.updateWatchListStatus(tmdbId, movieSerieEnum, watchListStatus, userId);
    }

    public void deleteByTmdbIdAndByMovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum) {
        Integer userId = getActiveUserId();
        this.watchListRepository.deleteByTmdbIdAndMovieSerieEnum(tmdbId, movieSerieEnum, userId);
    }

    public Integer getActiveUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

}
