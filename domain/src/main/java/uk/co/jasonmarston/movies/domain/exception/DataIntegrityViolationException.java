package uk.co.jasonmarston.movies.domain.exception;

public class DataIntegrityViolationException extends DomainException {
    public DataIntegrityViolationException(
            final String message
    ) {
        super(message);
    }

    public DataIntegrityViolationException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
