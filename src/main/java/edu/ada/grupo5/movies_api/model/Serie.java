package edu.ada.grupo5.movies_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "series")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//TODO : implementar modelo para o bd
public class Serie extends BaseModel{



}
