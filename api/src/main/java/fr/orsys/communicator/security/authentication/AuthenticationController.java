package fr.orsys.communicator.security.authentication;

import fr.orsys.communicator.security.exception.MissingAuthenticationTokenHeaderException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RequiredArgsConstructor
public abstract class AuthenticationController<T, C> {
    private final AuthenticationManager<T> authenticationManager;

    protected abstract T getUser(C validatedCredentials);

    private String getAuthenticationToken(RequestEntity<?> request) {
        String authenticationToken = request.getHeaders().getFirst(authenticationManager.AUTHENTICATION_TOKEN_HEADER);
        if (authenticationToken == null) {
            throw new MissingAuthenticationTokenHeaderException();
        }
        return authenticationToken;
    }

    @GetMapping
    public final ResponseEntity<T> getAuthenticatedUser() {
        T authenticatedUser = authenticationManager.getCurrentAuthenticatedUser();
        if (authenticatedUser == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("authenticate")
    public final ResponseEntity<T> authenticate(@RequestBody @Valid C credentials) {
        T user = getUser(credentials);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, authenticationManager.AUTHENTICATION_TOKEN_HEADER)
                .header(authenticationManager.AUTHENTICATION_TOKEN_HEADER,
                        authenticationManager.createAuthenticationToken(user))
                .body(user);
    }

    @PostMapping("logOut")
    public final void logOut(RequestEntity<Void> request) {
        try {
            authenticationManager.removeAuthentication(getAuthenticationToken(request));
        } catch (MissingAuthenticationTokenHeaderException ignored) {
        }
    }
}
