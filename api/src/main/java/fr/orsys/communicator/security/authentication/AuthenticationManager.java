package fr.orsys.communicator.security.authentication;

import fr.orsys.communicator.security.exception.AuthenticationException;
import fr.orsys.communicator.security.exception.ExpiredAuthenticationTokenException;
import fr.orsys.communicator.security.exception.InvalidAuthenticationTokenException;
import fr.orsys.communicator.security.exception.TokenException;
import lombok.experimental.PackagePrivate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public abstract class AuthenticationManager<T> {
    public final String AUTHENTICATION_TOKEN_HEADER = "Authentication-Token";

    private final ConcurrentMap<String, Authentication<T>> authentications = new ConcurrentHashMap<>();

    private final ConcurrentMap<Long, T> threadBindingMap = new ConcurrentHashMap<>();

    @Value("${application.authentication.token.length}")
    private Integer authenticationTokenLength;

    @Value("${application.authentication.timeout}")
    private Long authenticationTimeout;

    @Value("${application.authentication.limit}")
    private Integer authenticationLimit;

    private String generateAuthenticationToken() {
        String authenticationToken;
        do {
            authenticationToken = RandomStringUtils.randomAlphanumeric(authenticationTokenLength);
        } while (authentications.containsKey(authenticationToken));
        return authenticationToken;
    }

    private Authentication<T> validateAuthenticationToken(String authenticationToken) {
        Authentication<T> authentication = authentications.get(authenticationToken);

        if (authentication == null) {
            throw new InvalidAuthenticationTokenException();
        }

        if (authentication.hasExpired()) {
            removeAuthentication(authenticationToken);
            throw new ExpiredAuthenticationTokenException();
        }

        return authentication;
    }

    public T getAuthenticatedUser(String authenticationToken) {
        return validateAuthenticationToken(authenticationToken).getUser();
    }

    public String createAuthenticationToken(T user) {
        if (authentications.size() >= authenticationLimit) {
            throw new AuthenticationException(
                    "Too many users are currently authenticated. Please try again in a few minutes");
        }
        String authenticationToken = generateAuthenticationToken();
        authentications.put(authenticationToken, new Authentication<>(user, authenticationTimeout));
        return authenticationToken;
    }

    public String renewAuthenticationToken(String currentAuthenticationToken) {
        Authentication<T> authentication = validateAuthenticationToken(currentAuthenticationToken);
        authentication.renew(authenticationTimeout);
        String newAuthenticationToken = generateAuthenticationToken();
        authentications.put(newAuthenticationToken, authentication);
        return newAuthenticationToken;
    }

    public void removeAuthentication(String authenticationToken) {
        authentications.remove(authenticationToken);
    }

    @PackagePrivate
    void bindAuthenticatedUserToCurrentThread(String authenticationToken) {
        threadBindingMap.put(Thread.currentThread().getId(), getAuthenticatedUser(authenticationToken));
    }

    @PackagePrivate
    void removeCurrentThreadBinding() {
        threadBindingMap.remove(Thread.currentThread().getId());
    }

    public T getCurrentAuthenticatedUser() {
        return threadBindingMap.get(Thread.currentThread().getId());
    }

    @Scheduled(fixedDelay = 15L, timeUnit = TimeUnit.MINUTES)
    public void cleanExpiredAuthentications() {
        List<String> expiredAuthenticationTokens = new ArrayList<>();
        authentications.forEach((authenticationToken, authentication) -> {
            if (authentication.hasExpired()) {
                expiredAuthenticationTokens.add(authenticationToken);
            }
        });
        expiredAuthenticationTokens.forEach(this::removeAuthentication);
    }
}
