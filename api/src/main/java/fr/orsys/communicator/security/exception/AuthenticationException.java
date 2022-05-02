package fr.orsys.communicator.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends ResponseStatusException {
    public AuthenticationException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
