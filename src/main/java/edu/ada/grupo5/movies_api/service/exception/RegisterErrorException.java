package edu.ada.grupo5.movies_api.service.exception;

public class RegisterErrorException extends RuntimeException {

    public RegisterErrorException() {
        super();
    }

    public RegisterErrorException(String message) {
        super(message);
    }

    public RegisterErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
