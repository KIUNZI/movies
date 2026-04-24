package uk.co.jasonmarston.movies.domain.exception;

/**
 * Exception thrown when a persistence operation cannot be completed.
 *
 * <p>Wraps infrastructure-level persistence failures and translates them into a
 * domain exception so that the domain layer remains independent of any specific
 * persistence technology.</p>
 *
 * @see DomainException
 */
public class PersistenceException extends DomainException {

    /**
     * Constructs a {@code PersistenceException} with the given detail message.
     *
     * @param message the detail message describing the persistence failure
     */
    public PersistenceException(
            final String message
    ) {
        super(message);
    }

    /**
     * Constructs a {@code PersistenceException} with the given detail message and cause.
     *
     * @param message the detail message describing the persistence failure
     * @param cause   the underlying cause
     */
    public PersistenceException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
