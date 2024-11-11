package edu.ada.grupo5.movies_api.dto.tmdb;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SerieDTO {

    public boolean adult;
    public String backdrop_path;
    public List<Integer> genre_ids;
    public int id;
    public List<String> origin_country;
    public String original_language;
    public String original_name;
    public String overview;
    public double popularity;
    public String poster_path;
    public String first_air_date;
    public String name;
    public double vote_average;
    public int vote_count;
}
