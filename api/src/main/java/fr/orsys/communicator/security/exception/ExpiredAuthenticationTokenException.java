package fr.orsys.communicator.security.exception;

public class ExpiredAuthenticationTokenException extends TokenException {
    public ExpiredAuthenticationTokenException() {
        super("The provided authentication token has expired. Please re-log in and try again");
    }
}
