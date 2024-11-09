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

    private String title;
    private String imdbId;
    private String tmdbId;


}
