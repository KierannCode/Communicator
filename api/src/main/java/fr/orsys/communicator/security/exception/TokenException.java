package fr.orsys.communicator.security.exception;

public abstract class TokenException extends AuthenticationException {
    public TokenException(String message) {
        super(message);
    }
}
