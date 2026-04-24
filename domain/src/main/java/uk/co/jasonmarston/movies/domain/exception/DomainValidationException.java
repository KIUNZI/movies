package uk.co.jasonmarston.movies.domain.exception;

/**
 * Exception thrown when input data violates domain-layer validation rules.
 *
 * <p>Unlike {@link DomainInvariantViolationException}, which is raised when an invariant
 * constraint is violated on a constructed object, {@code DomainValidationException} is
 * intended for explicit validation failures identified by domain logic before or during
 * construction.</p>
 *
 * @see DomainException
 */
public class DomainValidationException extends DomainException {

    /**
     * Constructs a {@code DomainValidationException} with the given detail message.
     *
     * @param message the detail message describing the validation failure
     */
    public DomainValidationException(
            final String message
    ) {
        super(message);
    }

    /**
     * Constructs a {@code DomainValidationException} with the given detail message and cause.
     *
     * @param message the detail message describing the validation failure
     * @param cause   the underlying cause
     */
    public DomainValidationException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
