package uk.co.jasonmarston.movies.domain.exception;

public class VersionMismatchException extends DomainException {
    public VersionMismatchException(
            final String message
    ) {
        super(message);
    }

    public VersionMismatchException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
