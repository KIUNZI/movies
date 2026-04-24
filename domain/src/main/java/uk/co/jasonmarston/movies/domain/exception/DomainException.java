package uk.co.jasonmarston.movies.domain.exception;

/**
 * Base class for all domain-layer exceptions.
 *
 * <p>Domain exceptions are unchecked ({@link RuntimeException}) and represent error
 * conditions that originate within the domain model. Concrete subclasses such as
 * {@link DomainInvariantViolationException}, {@link DomainValidationException}, and
 * {@link NotFoundException} refine the failure semantics.</p>
 *
 * @see DomainInvariantViolationException
 * @see DomainValidationException
 * @see NotFoundException
 * @see PersistenceException
 * @see DataIntegrityViolationException
 * @see VersionMismatchException
 */
public abstract class DomainException extends RuntimeException {

    /**
     * Constructs a {@code DomainException} with the given detail message.
     *
     * @param message the detail message
     */
    protected DomainException(
            final String message
    ) {
        super(message);
    }

    /**
     * Constructs a {@code DomainException} with the given detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    protected DomainException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
