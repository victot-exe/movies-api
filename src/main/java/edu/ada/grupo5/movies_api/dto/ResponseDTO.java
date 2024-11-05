package edu.ada.grupo5.movies_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseDTO<T> {
    private String message;
    private LocalDateTime timestamp;
    private T data;
}
