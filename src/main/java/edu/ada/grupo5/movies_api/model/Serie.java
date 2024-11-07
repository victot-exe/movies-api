package edu.ada.grupo5.movies_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
//TODO : implementar modelo para o bd
public class Serie extends BaseModel{
    public String backdrop_path;
    @JsonProperty("id")
    public int showId;
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
