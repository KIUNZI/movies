package uk.co.jasonmarston.movies.domain.exception;

public class PersistenceException extends DomainException {
    public PersistenceException(
            final String message
    ) {
        super(message);
    }

    public PersistenceException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
