package edu.ada.grupo5.movies_api.handler;

import edu.ada.grupo5.movies_api.model.exception.StandardError;
import edu.ada.grupo5.movies_api.service.exception.RegisterErrorException;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import edu.ada.grupo5.movies_api.service.exception.ValidationErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// TODO: revisar implementacoes
@ControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String stringError = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(Instant.now(), status.value(), stringError, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<StandardError> validationError(ValidationErrorException e, HttpServletRequest request) {
        String stringError = "Error validating";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError error = new StandardError(Instant.now(), status.value(), stringError, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(RegisterErrorException.class)
    public ResponseEntity<StandardError> registerError(RegisterErrorException e, HttpServletRequest request) {
        String stringError = "Error on register";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(Instant.now(), status.value(), stringError, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
