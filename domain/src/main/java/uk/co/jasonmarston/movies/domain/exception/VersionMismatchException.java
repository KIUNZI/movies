package uk.co.jasonmarston.movies.domain.exception;

/**
 * Exception thrown when an optimistic locking conflict is detected.
 *
 * <p>Raised when the version of a persisted aggregate does not match the version held
 * by the caller, indicating that another party has modified the resource since it was
 * last read.</p>
 *
 * @see DomainException
 */
public class VersionMismatchException extends DomainException {

    /**
     * Constructs a {@code VersionMismatchException} with the given detail message.
     *
     * @param message the detail message describing the version conflict
     */
    public VersionMismatchException(
            final String message
    ) {
        super(message);
    }

    /**
     * Constructs a {@code VersionMismatchException} with the given detail message and cause.
     *
     * @param message the detail message describing the version conflict
     * @param cause   the underlying cause
     */
    public VersionMismatchException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
