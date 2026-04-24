package uk.co.jasonmarston.movies.domain.exception;

/**
 * Exception thrown when a requested resource cannot be found in the system.
 *
 * <p>Raised by output ports and adapters when a lookup operation returns no result,
 * for example when a movie with a given {@link uk.co.jasonmarston.movies.domain.valueobject.PublicId}
 * does not exist in the data store.</p>
 *
 * @see DomainException
 */
public class NotFoundException extends DomainException {

    /**
     * Constructs a {@code NotFoundException} with the given detail message.
     *
     * @param message the detail message describing which resource was not found
     */
    public NotFoundException(
            final String message
    ) {
        super(message);
    }

    /**
     * Constructs a {@code NotFoundException} with the given detail message and cause.
     *
     * @param message the detail message describing which resource was not found
     * @param cause   the underlying cause
     */
    public NotFoundException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
