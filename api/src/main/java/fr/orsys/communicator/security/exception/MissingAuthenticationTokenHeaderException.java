package fr.orsys.communicator.security.exception;

public class MissingAuthenticationTokenHeaderException extends TokenException {
    public MissingAuthenticationTokenHeaderException() {
        super("The authentication token header is not present in the request");
    }
}
