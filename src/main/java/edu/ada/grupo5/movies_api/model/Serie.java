package edu.ada.grupo5.movies_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "series")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//TODO : implementar modelo para o bd
public class Serie extends BaseModel implements Serializable {

    private String name;
    private int tmdbId;
    public boolean adult;
    public String original_language;
    public String original_name;
    public String first_air_date;
    public double vote_average;


}
