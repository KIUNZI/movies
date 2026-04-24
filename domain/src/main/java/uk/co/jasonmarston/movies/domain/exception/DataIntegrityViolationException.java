package uk.co.jasonmarston.movies.domain.exception;

/**
 * Exception thrown when a persistence operation fails due to a data integrity constraint.
 *
 * <p>Typically wraps a lower-level constraint violation reported by the persistence
 * framework, such as a unique key violation or a foreign key constraint failure.</p>
 *
 * @see DomainException
 */
public class DataIntegrityViolationException extends DomainException {

    /**
     * Constructs a {@code DataIntegrityViolationException} with the given detail message.
     *
     * @param message the detail message describing the integrity violation
     */
    public DataIntegrityViolationException(
            final String message
    ) {
        super(message);
    }

    /**
     * Constructs a {@code DataIntegrityViolationException} with the given detail message
     * and cause.
     *
     * @param message the detail message describing the integrity violation
     * @param cause   the underlying cause
     */
    public DataIntegrityViolationException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
