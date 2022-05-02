package fr.orsys.communicator.security.authentication;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Authentication<T> {
    @Getter
    private final T user;

    private LocalDateTime expirationTimestamp;

    public Authentication(T user, long timeout) {
        this.user = user;
        expirationTimestamp = makeExpirationTimestamp(timeout);
    }

    private LocalDateTime makeExpirationTimestamp(long timeout) {
        return LocalDateTime.now(ZoneOffset.UTC).plus(timeout, ChronoUnit.MINUTES);
    }

    public boolean hasExpired() {
        return LocalDateTime.now(ZoneOffset.UTC).isAfter(expirationTimestamp);
    }

    public void renew(long timeout) {
        expirationTimestamp = makeExpirationTimestamp(timeout);
    }
}
