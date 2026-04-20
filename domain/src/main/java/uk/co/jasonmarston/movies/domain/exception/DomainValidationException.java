package uk.co.jasonmarston.movies.domain.exception;

public class DomainValidationException extends DomainException {
    public DomainValidationException(
            final String message
    ) {
        super(message);
    }

    public DomainValidationException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
