package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.Repositories.WatchListRepository;
import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.RecommendedMovieDTO;
import edu.ada.grupo5.movies_api.dto.WatchListDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.MovieDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ResultResponseDTO;
import edu.ada.grupo5.movies_api.model.MovieSerieEnum;
import edu.ada.grupo5.movies_api.model.User;
import edu.ada.grupo5.movies_api.model.WatchList;
import edu.ada.grupo5.movies_api.model.WatchListStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private TMDBClientFeign tmdbClientFeign;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private MovieService movieService;

    public void save(String tmdbId, String title, MovieSerieEnum movieSerieEnum, WatchListStatus watchListStatus, boolean favorite){
        WatchList watchList = new WatchList(tmdbId, title, movieSerieEnum, watchListStatus, favorite);

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

    @Transactional
    public void deleteByTmdbIdAndByMovieSerieEnum(String tmdbId, MovieSerieEnum movieSerieEnum) {
        Integer userId = getActiveUserId();
        this.watchListRepository.deleteByTmdbIdAndMovieSerieEnum(tmdbId, movieSerieEnum, userId);
    }

    public Integer getActiveUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }


    //recomendação por favoritos baseado apenas no repositório.
    public List<RecommendedMovieDTO> getRecommendedMovies() {
        List<WatchList> allFavorites = watchListRepository.findAllFavorites();

        Map<String, Long> favoriteCounts = allFavorites.stream()
                .collect(Collectors.groupingBy(WatchList::getTmdbId, Collectors.counting()));

        List<RecommendedMovieDTO> recommendedMovies = favoriteCounts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .map(entry -> {
                    String title = allFavorites.stream()
                            .filter(watchList -> watchList.getTmdbId().equals(entry.getKey()))
                            .map(WatchList::getTitle)
                            .findFirst()
                            .orElse("Título desconhecido");
                    return new RecommendedMovieDTO(title, entry.getKey(), entry.getValue());
                })
                .collect(Collectors.toList());

        return recommendedMovies;
    }


    //recomendaçao de filme (watched) por genero buscando no TMDB.
    public List<RecommendedMovieDTO> getGenreBasedRecommendations() {
        Integer userId = getActiveUserId();

        List<WatchList> favoriteItems = watchListRepository.findAllByUserId(userId).stream()
                .filter(item -> item.getWatchListStatus() == WatchListStatus.WATCHED|| item.getWatchListStatus() == WatchListStatus.WATCHING)
                .collect(Collectors.toList());

        Map<Integer, Long> genreCounts = favoriteItems.stream()
                .map(WatchList::getTitle)
                .flatMap(title -> tmdbClientFeign.getMovie(title).getResults().stream())
                .flatMap(movie -> movie.getGenre_ids().stream())
                .collect(Collectors.groupingBy(genre -> genre, Collectors.counting()));

        List<Integer> popularGenres = genreCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<MovieDTO> recommendedMovies = new ArrayList<>();
        for (Integer genreId : popularGenres) {
            ResultResponseDTO<MovieDTO> genreMovies = tmdbClientFeign.getMoviesByGenre(String.valueOf(genreId));
            recommendedMovies.addAll(genreMovies.getResults());
        }
        List<RecommendedMovieDTO> recommendedMovieDTOs = recommendedMovies.stream()
                .map(movie -> new RecommendedMovieDTO(movie.getTitle(), String.valueOf(movie.getId())))
                .limit(15)
                .collect(Collectors.toList());

        return recommendedMovieDTOs;
    }

    //Deletar favorito

    public void delete(String tmdbId) {
        Integer userId = getActiveUserId();
        WatchList watchList = watchListRepository.findByTmdbIdAndUserId(tmdbId, userId);
        if (watchList != null) {
            watchListRepository.delete(watchList);
        } else {
            throw new RuntimeException("Item não encontrado na lista de favoritos.");
        }
    }

}
