package edu.ada.grupo5.movies_api.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError implements Serializable {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    @Override
    public String toString() {
        return "{\n" +
                "  \"timestamp\": " + "\"" + timestamp + "\",\n" +
                "  \"status\": " + "\"" + status + "\",\n" +
                "  \"error\": " + "\"" + error + "\",\n" +
                "  \"message\": " + "\"" + message + "\",\n" +
                "  \"path\": " + "\"" + path + "\"\n" +
                "}";
    }
}
