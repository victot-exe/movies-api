package edu.ada.grupo5.movies_api.service;

import edu.ada.grupo5.movies_api.client.api.TMDBClientFeign;
import edu.ada.grupo5.movies_api.dto.TVResult;
import edu.ada.grupo5.movies_api.dto.tmdb.AiringTodayDTO;
import edu.ada.grupo5.movies_api.dto.tmdb.ModelResponseGET;
import edu.ada.grupo5.movies_api.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeriesService {

    @Autowired
    private TMDBClientFeign tMDBClientFeign;

    public ModelResponseGET<AiringTodayDTO> getAiringToday(String language, String page) {
        return tMDBClientFeign.getAiringToday(language, page);
    }

    public TVResult findSerieById(String externalSource, String language) {
        return tMDBClientFeign.findSerieById("imdb_id", externalSource, language);
    }


}
