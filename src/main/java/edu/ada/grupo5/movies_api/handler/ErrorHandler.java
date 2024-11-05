package edu.ada.grupo5.movies_api.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// TODO: implementar
@ControllerAdvice
public class ErrorHandler {

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<StandardError> handleException(Exception e, HttpServletRequest request) {
//        String stringError = "Resource not found";
//        HttpStatus status = HttpStatus.NOT_FOUND;
//        StandardError error = new StandardError(Instant.now(), status.value(),stringError,e.getMessage(),request.getRequestURI());
//        return ResponseEntity.status(status).body(error);
//    }
}
