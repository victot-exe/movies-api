package edu.ada.grupo5.movies_api.service.exception;

public class ValidationErrorException extends RuntimeException {

    public ValidationErrorException() {
        super();
    }

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
