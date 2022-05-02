package fr.orsys.communicator.security.exception;

public class NotAuthenticatedException extends AuthenticationException {
    public NotAuthenticatedException() {
        super("You are not authenticated, please log in and try again");
    }
}
