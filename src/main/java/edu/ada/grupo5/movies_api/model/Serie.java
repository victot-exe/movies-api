package edu.ada.grupo5.movies_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
