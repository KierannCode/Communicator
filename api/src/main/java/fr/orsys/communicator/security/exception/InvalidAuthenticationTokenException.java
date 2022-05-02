package fr.orsys.communicator.security.exception;

public class InvalidAuthenticationTokenException extends TokenException {
    public InvalidAuthenticationTokenException() {
        super("The provided authentication token is invalid. Please re-log in and try again");
    }
}
