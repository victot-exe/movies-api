package edu.ada.grupo5.movies_api.dto.tmdb;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SerieDTO {
    public String backdrop_path;
    public int id;
    public String name;
    public String original_name;
    public String overview;
    public String poster_path;
    public String media_type;
    public boolean adult;
    public String original_language;
    public List<Integer> genre_ids;
    public double popularity;
    public String first_air_date;
    public double vote_average;
    public int vote_count;
    public List<String> origin_country;
}
